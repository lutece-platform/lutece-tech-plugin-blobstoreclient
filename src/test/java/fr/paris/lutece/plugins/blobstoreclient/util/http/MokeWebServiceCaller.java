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
package fr.paris.lutece.plugins.blobstoreclient.util.http;

import fr.paris.lutece.util.httpaccess.HttpAccessException;
import fr.paris.lutece.util.signrequest.RequestAuthenticator;

import org.apache.commons.fileupload.FileItem;

import java.util.List;
import java.util.Map;


/**
 *
 * MokeWebServiceCaller
 *
 */
public class MokeWebServiceCaller extends WebServiceCaller
{
    private static final String FILE_NAME = "FileName";
    private static final String FILE_URL = "FileUrl";
    private static final String BLOB_KEY1 = "BlobKey1";
    private static final String BLOB_KEY2 = "BlobKey2";

    /**
     * {@inheritDoc}
     */
    public String callWSGetFileName( String strUrl ) throws HttpAccessException
    {
        String strTrace = trace( strUrl );
        System.out.println( strTrace );

        return FILE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    public String callWSGet( String strUrl, RequestAuthenticator authenticator, List<String> listElements )
        throws HttpAccessException
    {
        String strTrace = trace( strUrl, authenticator, listElements );
        System.out.println( strTrace );

        return FILE_URL;
    }

    /**
     * {@inheritDoc}
     */
    public String callWSPost( String strUrl, Map<String, List<String>> mapParameters,
        RequestAuthenticator authenticator, List<String> listElements )
        throws HttpAccessException
    {
        String strTrace = trace( strUrl, mapParameters, authenticator, listElements );
        System.out.println( strTrace );

        return BLOB_KEY1;
    }

    /**
     * {@inheritDoc}
     */
    public String callWSPostMultiPart( String strUrl, Map<String, List<String>> mapParameters,
        Map<String, FileItem> listFileItems, RequestAuthenticator authenticator, List<String> listElements )
        throws HttpAccessException
    {
        String strTrace = trace( strUrl, mapParameters, listFileItems, authenticator, listElements );
        System.out.println( strTrace );

        return BLOB_KEY2;
    }
}
