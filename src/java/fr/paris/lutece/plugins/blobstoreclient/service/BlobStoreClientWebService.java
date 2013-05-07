/*
 * Copyright (c) 2002-2013, Mairie de Paris
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

import fr.paris.lutece.plugins.blobstoreclient.service.signrequest.BlobStoreClientRequestAuthenticatorService;
import fr.paris.lutece.plugins.blobstoreclient.util.UrlUtils;
import fr.paris.lutece.plugins.blobstoreclient.util.http.IWebServiceCaller;
import fr.paris.lutece.portal.service.blobstore.BlobStoreClientException;
import fr.paris.lutece.portal.service.blobstore.IBlobStoreClientService;
import fr.paris.lutece.util.httpaccess.HttpAccessException;

import org.apache.commons.fileupload.FileItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * BlobStoreClientWebService
 *
 */
public class BlobStoreClientWebService implements IBlobStoreClientService
{
    private static final String PARAMETER_BLOB_KEY = "blob_key";
    private static final String PARAMETER_BLOBSTORE = "blobstore";
    private static final String PARAMETER_BLOB = "blob";
    private IWebServiceCaller _webServiceCaller;

    /**
     * Set the webservice caller
     * @param webServiceCaller the webservice caller
     */
    public void setWebServiceCaller( IWebServiceCaller webServiceCaller )
    {
        _webServiceCaller = webServiceCaller;
    }

    /**
     * {@inheritDoc}
     */
    public String getFileName( String strUrl ) throws BlobStoreClientException
    {
        try
        {
            return _webServiceCaller.callWSGetFileName( strUrl );
        }
        catch ( HttpAccessException e )
        {
            throw new BlobStoreClientException( e.getMessage(  ) );
        }
    }

    /**
     * {@inheritDoc}
     */
    public String doDeleteFile( String strBaseUrl, String strBlobStore, String strBlobKey )
        throws BlobStoreClientException
    {
        // Parameters
        Map<String, List<String>> mapParameters = new HashMap<String, List<String>>(  );
        List<String> parameterBlobKey = new ArrayList<String>(  );
        parameterBlobKey.add( strBlobKey );

        List<String> parameterBlobStore = new ArrayList<String>(  );
        parameterBlobStore.add( strBlobStore );
        mapParameters.put( PARAMETER_BLOB_KEY, parameterBlobKey );
        mapParameters.put( PARAMETER_BLOBSTORE, parameterBlobStore );

        // List elements to include to the signature
        List<String> listElements = new ArrayList<String>(  );
        listElements.add( strBlobKey );
        listElements.add( strBlobStore );

        try
        {
            return _webServiceCaller.callWSPost( UrlUtils.buildDeleteBlobUrl( strBaseUrl ), mapParameters,
                BlobStoreClientRequestAuthenticatorService.getRequestAuthenticator(  ), listElements );
        }
        catch ( HttpAccessException e )
        {
            throw new BlobStoreClientException( e.getMessage(  ) );
        }
    }

    /**
     * {@inheritDoc}
     */
    public String doUploadFile( String strBaseUrl, FileItem fileItem, String strBlobStore )
        throws BlobStoreClientException
    {
        // Parameters
        Map<String, List<String>> mapParameters = new HashMap<String, List<String>>(  );
        List<String> parameterBlobStore = new ArrayList<String>(  );
        parameterBlobStore.add( strBlobStore );
        mapParameters.put( PARAMETER_BLOBSTORE, parameterBlobStore );

        Map<String, FileItem> fileItems = new HashMap<String, FileItem>(  );
        fileItems.put( PARAMETER_BLOB, fileItem );

        // List elements to include to the signature
        List<String> listElements = new ArrayList<String>(  );
        listElements.add( strBlobStore );

        try
        {
            return _webServiceCaller.callWSPostMultiPart( UrlUtils.buildCreateBlobUrl( strBaseUrl, strBlobStore ),
                mapParameters, fileItems, BlobStoreClientRequestAuthenticatorService.getRequestAuthenticator(  ),
                listElements );
        }
        catch ( HttpAccessException e )
        {
            throw new BlobStoreClientException( e.getMessage(  ) );
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getFileUrl( String strBaseUrl, String strBlobStore, String strBlobKey )
        throws BlobStoreClientException
    {
        List<String> listElements = new ArrayList<String>(  );
        listElements.add( strBlobKey );
        listElements.add( strBlobStore );

        try
        {
            return _webServiceCaller.callWSGet( UrlUtils.buildFileUrl( strBaseUrl, strBlobStore, strBlobKey ),
                BlobStoreClientRequestAuthenticatorService.getRequestAuthenticator(  ), listElements );
        }
        catch ( HttpAccessException e )
        {
            throw new BlobStoreClientException( e.getMessage(  ) );
        }
    }

    /**
     * {@inheritDoc}
     */
    public void doDownloadFile( String strUrl, String strFilePath )
        throws BlobStoreClientException
    {
        String strBlobKey = UrlUtils.getBlobKeyFromUrl( strUrl );
        String strBlobStore = UrlUtils.getBlobStoreFromUrl( strUrl );

        List<String> listElements = new ArrayList<String>(  );
        listElements.add( strBlobKey );
        listElements.add( strBlobStore );

        try
        {
            _webServiceCaller.callWSDownloadFile( strUrl, strFilePath,
                BlobStoreClientRequestAuthenticatorService.getRequestAuthenticator(  ), listElements );
        }
        catch ( HttpAccessException e )
        {
            throw new BlobStoreClientException( e.getMessage(  ) );
        }
    }

    /**
     * {@inheritDoc}
     */
    public FileItem doDownloadFile( String strUrl ) throws BlobStoreClientException
    {
        String strBlobKey = UrlUtils.getBlobKeyFromUrl( strUrl );
        String strBlobStore = UrlUtils.getBlobStoreFromUrl( strUrl );

        List<String> listElements = new ArrayList<String>(  );
        listElements.add( strBlobKey );
        listElements.add( strBlobStore );

        try
        {
            return _webServiceCaller.callWSDownloadFile( strUrl,
                BlobStoreClientRequestAuthenticatorService.getRequestAuthenticator(  ), listElements );
        }
        catch ( HttpAccessException e )
        {
            throw new BlobStoreClientException( e.getMessage(  ) );
        }
    }
}
