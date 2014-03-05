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

import fr.paris.lutece.plugins.blobstore.service.BlobStoreClientException;
import fr.paris.lutece.plugins.blobstoreclient.business.MokeFileItem;
import fr.paris.lutece.plugins.blobstoreclient.util.http.MokeWebServiceCaller;
import fr.paris.lutece.test.LuteceTestCase;


/**
 * 
 * CRMClientWebServiceTest
 * 
 */
public class BlobStoreClientWebServiceTest extends LuteceTestCase
{
    private static final String URL = "URL";
    private static final String BLOBSTORE = "BlobStore";
    private static final String BLOB_KEY = "BlobKey";

    /**
     * Test of getFileName method of
     * fr.paris.lutece.plugins.blobstoreclient.service.BlobStoreClientWebService
     */
    public void testGetFileName( )
    {
        System.out.println( "getFileName" );

        BlobStoreClientWebService webService = new BlobStoreClientWebService( );
        webService.setWebServiceCaller( new MokeWebServiceCaller( ) );

        try
        {
            webService.getFileName( URL );
        }
        catch ( BlobStoreClientException e )
        {
            fail( );
        }
    }

    /**
     * Test of doDeleteFile method of
     * fr.paris.lutece.plugins.blobstoreclient.service.BlobStoreClientWebService
     */
    public void testDoDeleteFile( )
    {
        System.out.println( "doDeleteFile" );

        BlobStoreClientWebService webService = new BlobStoreClientWebService( );
        webService.setWebServiceCaller( new MokeWebServiceCaller( ) );

        try
        {
            webService.doDeleteFile( URL, BLOBSTORE, BLOB_KEY );
        }
        catch ( BlobStoreClientException e )
        {
            fail( );
        }
    }

    /**
     * Test of doUploadFile method of
     * fr.paris.lutece.plugins.blobstoreclient.service.BlobStoreClientWebService
     */
    public void testDoUploadFile( )
    {
        System.out.println( "doDeleteFile" );

        BlobStoreClientWebService webService = new BlobStoreClientWebService( );
        webService.setWebServiceCaller( new MokeWebServiceCaller( ) );

        try
        {
            webService.doUploadFile( URL, new MokeFileItem( getResourcesDir( ) ), BLOBSTORE );
        }
        catch ( BlobStoreClientException e )
        {
            fail( );
        }
    }

    /**
     * Test of getFileUrl method of
     * fr.paris.lutece.plugins.blobstoreclient.service.BlobStoreClientWebService
     */
    public void testGetFileUrl( )
    {
        System.out.println( "getFileUrl" );

        BlobStoreClientWebService webService = new BlobStoreClientWebService( );
        webService.setWebServiceCaller( new MokeWebServiceCaller( ) );

        try
        {
            webService.getFileUrl( URL, BLOBSTORE, BLOB_KEY );
        }
        catch ( BlobStoreClientException e )
        {
            fail( );
        }
    }
}
