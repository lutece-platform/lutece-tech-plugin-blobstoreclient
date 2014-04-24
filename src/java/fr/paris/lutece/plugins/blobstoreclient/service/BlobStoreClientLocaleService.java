/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.blobstoreclient.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import fr.paris.lutece.plugins.blobstore.service.BlobStoreClientException;
import fr.paris.lutece.plugins.blobstore.service.BlobStoreFileItem;
import fr.paris.lutece.plugins.blobstore.service.IBlobStoreService;
import fr.paris.lutece.plugins.blobstore.service.IBlobStoreClientService;
import fr.paris.lutece.plugins.blobstore.service.NoSuchBlobException;
import fr.paris.lutece.plugins.blobstoreclient.util.UrlUtils;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.web.constants.Messages;


/**
 * 
 * BlobStoreClientLocaleService
 * 
 */
public class BlobStoreClientLocaleService implements IBlobStoreClientService
{
    private static final String BLOBSTORE_PLUGIN_NAME = "blobstore";

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFileName( String strUrl ) throws BlobStoreClientException
    {
        String strFileName = StringUtils.EMPTY;
        String strBlobStore = UrlUtils.getBlobStoreFromUrl( strUrl );
        String strBlobKey = UrlUtils.getBlobKeyFromUrl( strUrl );

        IBlobStoreService blobStoreService = getBlobStoreService( strBlobStore );

        try
        {
            BlobStoreFileItem fileItem = new BlobStoreFileItem( strBlobKey, blobStoreService );
            strFileName = fileItem.getName( );
        }
        catch ( NoSuchBlobException e )
        {
            String strError = buildNoSuchBlobErrorMessage( strBlobStore, strBlobKey );
            AppLogService.error( strError + e.getMessage( ), e );
            throw new BlobStoreClientException( e.getMessage( ) );
        }

        return strFileName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String doDeleteFile( String strBaseUrl, String strBlobStore, String strBlobKey )
            throws BlobStoreClientException
    {
        IBlobStoreService blobStoreService = getBlobStoreService( strBlobStore );

        try
        {
            BlobStoreFileItem fileItem = new BlobStoreFileItem( strBlobKey, blobStoreService );
            fileItem.delete( );
        }
        catch ( NoSuchBlobException e )
        {
            String strError = buildNoSuchBlobErrorMessage( strBlobStore, strBlobKey );
            AppLogService.error( strError + e.getMessage( ), e );
            throw new BlobStoreClientException( e.getMessage( ) );
        }

        return strBlobKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String doUploadFile( String strBaseUrl, FileItem fileItem, String strBlobStore )
            throws BlobStoreClientException
    {
        String strBlobKey = StringUtils.EMPTY;

        if ( StringUtils.isNotBlank( strBlobStore ) && ( fileItem != null ) )
        {
            IBlobStoreService blobStoreService = getBlobStoreService( strBlobStore );

            try
            {
                strBlobKey = blobStoreService.storeInputStream( fileItem.getInputStream( ) );

                String strJSON = BlobStoreFileItem.buildFileMetadata( fileItem.getName( ), fileItem.getSize( ),
                        strBlobKey, fileItem.getContentType( ) );

                if ( AppLogService.isDebugEnabled( ) )
                {
                    AppLogService.debug( "Storing " + fileItem.getName( ) + " with : " + strJSON );
                }

                strBlobKey = blobStoreService.store( strJSON.getBytes( ) );
            }
            catch ( IOException e )
            {
                throw new BlobStoreClientException( e.getMessage( ) );
            }
        }
        else
        {
            throw new BlobStoreClientException( Messages.MANDATORY_FIELDS );
        }

        return strBlobKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFileUrl( String strBaseUrl, String strBlobStore, String strBlobKey )
            throws BlobStoreClientException
    {
        IBlobStoreService blobStoreService = getBlobStoreService( strBlobStore );

        return blobStoreService.getFileUrl( strBlobKey );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doDownloadFile( String strUrl, String strFilePath ) throws BlobStoreClientException
    {
        String strBlobStore = UrlUtils.getBlobStoreFromUrl( strUrl );
        String strBlobKey = UrlUtils.getBlobKeyFromUrl( strUrl );

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try
        {
            IBlobStoreService blobStoreService = getBlobStoreService( strBlobStore );

            BlobStoreFileItem fileItem = new BlobStoreFileItem( strBlobKey, blobStoreService );
            bis = new BufferedInputStream( fileItem.getInputStream( ) );

            FileOutputStream fos = new FileOutputStream( strFilePath );
            bos = new BufferedOutputStream( fos );

            int bytes;

            while ( ( bytes = bis.read( ) ) > -1 )
            {
                bos.write( bytes );
            }
        }
        catch ( NoSuchBlobException e )
        {
            String strError = buildNoSuchBlobErrorMessage( strBlobStore, strBlobKey );
            AppLogService.error( strError + e.getMessage( ), e );
            throw new BlobStoreClientException( e.getMessage( ) );
        }
        catch ( IOException e )
        {
            String strError = "BlobStoreClientLocaleService - Unable to download file '" + strUrl + "' : ";
            AppLogService.error( strError + e.getMessage( ), e );
            throw new BlobStoreClientException( strError + e.getMessage( ) );
        }
        finally
        {
            try
            {
                if ( bis != null )
                {
                    bis.close( );
                }

                if ( bos != null )
                {
                    bos.close( );
                }
            }
            catch ( IOException e )
            {
                AppLogService.error( "BlobStoreClientLocaleService - Error closing stream : " + e.getMessage( ), e );
                throw new BlobStoreClientException( e.getMessage( ) );
            }
        }
    }

    /**
     * Get the blob store service
     * @param strBlobStore the blob store service name
     * @return the BlobStore service
     * @throws BlobStoreClientException exception if there is an error
     */
    private IBlobStoreService getBlobStoreService( String strBlobStore ) throws BlobStoreClientException
    {
        if ( StringUtils.isNotBlank( strBlobStore ) )
        {
            try
            {
                return (IBlobStoreService) SpringContextService.getPluginBean( BLOBSTORE_PLUGIN_NAME, strBlobStore );
            }
            catch ( BeanDefinitionStoreException e )
            {
                String strError = "BlobStoreClientLocaleService - Bean definition store exception for blobstore '"
                        + strBlobStore + "' : ";
                AppLogService.error( strError + e.getMessage( ), e );
                throw new BlobStoreClientException( e.getMessage( ) );
            }
            catch ( NoSuchBeanDefinitionException e )
            {
                String strError = "BlobStoreClientLocaleService - No such Bean definition for blobstore '"
                        + strBlobStore + "' : ";
                AppLogService.error( strError + e.getMessage( ), e );
                throw new BlobStoreClientException( e.getMessage( ) );
            }
            catch ( CannotLoadBeanClassException e )
            {
                String strError = "BlobStoreClientLocaleService - Cannot load Bean Class for blobstore '"
                        + strBlobStore + "' : ";
                AppLogService.error( strError + e.getMessage( ), e );
                throw new BlobStoreClientException( e.getMessage( ) );
            }
        }
        else
        {
            throw new BlobStoreClientException( Messages.MANDATORY_FIELDS );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileItem doDownloadFile( String strUrl ) throws BlobStoreClientException
    {
        String strBlobStore = UrlUtils.getBlobStoreFromUrl( strUrl );
        String strBlobKey = UrlUtils.getBlobKeyFromUrl( strUrl );

        BlobStoreFileItem fileItem = null;

        try
        {
            IBlobStoreService blobStoreService = getBlobStoreService( strBlobStore );

            fileItem = new BlobStoreFileItem( strBlobKey, blobStoreService );
        }
        catch ( NoSuchBlobException e )
        {
            String strError = buildNoSuchBlobErrorMessage( strBlobStore, strBlobKey );
            AppLogService.error( strError + e.getMessage( ), e );
            throw new BlobStoreClientException( e.getMessage( ) );
        }

        return fileItem;
    }

    /**
     * Build the error message
     * @param strBlobStore the blobstore
     * @param strBlobKey the blob key
     * @return the error message
     */
    private String buildNoSuchBlobErrorMessage( String strBlobStore, String strBlobKey )
    {
        return "BlobStoreClientLocaleService - No such blob for blobstore '" + strBlobStore + "' " + "and blob key '"
                + strBlobKey + "' : ";
    }
}
