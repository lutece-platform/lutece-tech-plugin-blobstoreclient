/*
 * Copyright (c) 2002-2011, Mairie de Paris
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

import fr.paris.lutece.plugins.blobstoreclient.util.BlobStoreClientException;
import fr.paris.lutece.plugins.blobstoreclient.util.UrlUtils;
import fr.paris.lutece.portal.service.blobstore.BlobStoreFileItem;
import fr.paris.lutece.portal.service.blobstore.BlobStoreService;
import fr.paris.lutece.portal.service.blobstore.NoSuchBlobException;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.web.constants.Messages;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.io.IOException;


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
    public String getFileName( String strUrl ) throws BlobStoreClientException
    {
        String strFileName = StringUtils.EMPTY;
        String strBlobStore = UrlUtils.getBlobStoreFromUrl( strUrl );
        String strBlobKey = UrlUtils.getBlobKeyFromUrl( strUrl );

        if ( StringUtils.isNotBlank( strBlobKey ) && StringUtils.isNotBlank( strBlobStore ) )
        {
            BlobStoreService blobStoreService;

            try
            {
                blobStoreService = (BlobStoreService) SpringContextService.getPluginBean( BLOBSTORE_PLUGIN_NAME,
                        strBlobStore );

                BlobStoreFileItem fileItem = new BlobStoreFileItem( strBlobKey, blobStoreService );
                strFileName = fileItem.getName(  );
            }
            catch ( BeanDefinitionStoreException e )
            {
                throw new BlobStoreClientException( e.getMessage(  ) );
            }
            catch ( NoSuchBeanDefinitionException e )
            {
                throw new BlobStoreClientException( e.getMessage(  ) );
            }
            catch ( CannotLoadBeanClassException e )
            {
                throw new BlobStoreClientException( e.getMessage(  ) );
            }
            catch ( NoSuchBlobException e )
            {
                throw new BlobStoreClientException( e.getMessage(  ) );
            }
        }
        else
        {
            throw new BlobStoreClientException( Messages.MANDATORY_FIELDS );
        }

        return strFileName;
    }

    /**
     * {@inheritDoc}
     */
    public String doDeleteFile( String strBaseUrl, String strBlobStore, String strBlobKey )
        throws BlobStoreClientException
    {
        if ( StringUtils.isNotBlank( strBlobKey ) && StringUtils.isNotBlank( strBlobStore ) )
        {
            BlobStoreService blobStoreService;

            try
            {
                blobStoreService = (BlobStoreService) SpringContextService.getPluginBean( BLOBSTORE_PLUGIN_NAME,
                        strBlobStore );

                BlobStoreFileItem fileItem = new BlobStoreFileItem( strBlobKey, blobStoreService );
                fileItem.delete(  );
            }
            catch ( BeanDefinitionStoreException e )
            {
                throw new BlobStoreClientException( e.getMessage(  ) );
            }
            catch ( NoSuchBeanDefinitionException e )
            {
                throw new BlobStoreClientException( e.getMessage(  ) );
            }
            catch ( CannotLoadBeanClassException e )
            {
                throw new BlobStoreClientException( e.getMessage(  ) );
            }
            catch ( NoSuchBlobException e )
            {
                throw new BlobStoreClientException( e.getMessage(  ) );
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
    public String doUploadFile( String strBaseUrl, FileItem fileItem, String strBlobStore )
        throws BlobStoreClientException
    {
        String strBlobKey = StringUtils.EMPTY;

        if ( StringUtils.isNotBlank( strBlobStore ) && ( fileItem != null ) )
        {
            BlobStoreService blobStoreService;

            try
            {
                blobStoreService = (BlobStoreService) SpringContextService.getPluginBean( BLOBSTORE_PLUGIN_NAME,
                        strBlobStore );
                strBlobKey = blobStoreService.storeInputStream( fileItem.getInputStream(  ) );

                String strJSON = BlobStoreFileItem.buildFileMetadata( fileItem.getName(  ), fileItem.getSize(  ),
                        strBlobKey, fileItem.getContentType(  ) );

                if ( AppLogService.isDebugEnabled(  ) )
                {
                    AppLogService.debug( "Storing " + fileItem.getName(  ) + " with : " + strJSON );
                }

                strBlobKey = blobStoreService.store( strJSON.getBytes(  ) );
            }
            catch ( BeanDefinitionStoreException e )
            {
                throw new BlobStoreClientException( e.getMessage(  ) );
            }
            catch ( NoSuchBeanDefinitionException e )
            {
                throw new BlobStoreClientException( e.getMessage(  ) );
            }
            catch ( CannotLoadBeanClassException e )
            {
                throw new BlobStoreClientException( e.getMessage(  ) );
            }
            catch ( IOException e )
            {
                throw new BlobStoreClientException( e.getMessage(  ) );
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
    public String getFileUrl( String strBaseUrl, String strBlobStore, String strBlobKey )
        throws BlobStoreClientException
    {
        String strDownloadUrl = StringUtils.EMPTY;

        if ( StringUtils.isNotBlank( strBlobStore ) && StringUtils.isNotBlank( strBlobKey ) )
        {
            BlobStoreService blobStoreService;

            try
            {
                blobStoreService = (BlobStoreService) SpringContextService.getPluginBean( BLOBSTORE_PLUGIN_NAME,
                        strBlobStore );
                strDownloadUrl = blobStoreService.getFileUrl( strBlobKey );
            }
            catch ( BeanDefinitionStoreException e )
            {
                AppLogService.error( e.getMessage(  ) );
            }
            catch ( NoSuchBeanDefinitionException e )
            {
                AppLogService.error( e.getMessage(  ) );
            }
            catch ( CannotLoadBeanClassException e )
            {
                AppLogService.error( e.getMessage(  ) );
            }
        }
        else
        {
            AppLogService.error( Messages.MANDATORY_FIELDS );
        }

        return strDownloadUrl;
    }
}
