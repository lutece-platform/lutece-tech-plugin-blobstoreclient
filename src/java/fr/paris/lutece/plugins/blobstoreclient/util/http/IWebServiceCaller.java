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
package fr.paris.lutece.plugins.blobstoreclient.util.http;

import fr.paris.lutece.util.httpaccess.HttpAccessException;
import fr.paris.lutece.util.signrequest.RequestAuthenticator;

import org.apache.commons.fileupload.FileItem;

import java.util.List;
import java.util.Map;


/**
 * WebServiceCaller Interface
 */
public interface IWebServiceCaller
{
    /**
     * Call Rest WS to get the file name
     * @param strUrl the url
     * @return the file name
     * @throws HttpAccessException exception if there is an HTTP issue
     */
    String callWSGetFileName( String strUrl ) throws HttpAccessException;

    /**
     * Call Rest WS to execute a GET method
     * @param strUrl the url
     * @param authenticator The request authenticator
     * @param listElements the list of elements to include in the signature
     * @return the response as a String
     * @throws HttpAccessException exception if there is an HTTP issue
     */
    String callWSGet( String strUrl, RequestAuthenticator authenticator, List<String> listElements )
        throws HttpAccessException;

    /**
     * Call Rest WS to do an action
     * @param strUrl the url
     * @param mapParameters the params to pass in the post
     * @param authenticator The request authenticator
     * @param listElements the list of elements to include in the signature
     * @return the response as a String
     * @throws HttpAccessException the exception if there is a problem
     */
    String callWSPost( String strUrl, Map<String, List<String>> mapParameters, RequestAuthenticator authenticator,
        List<String> listElements ) throws HttpAccessException;

    /**
     * Call Rest WS to execute a multipart POST method
     * @param strUrl the url
     * @param mapParameters the params to pass in the post
     * @param fileItems the file to upload - map of (parameter_name, file_to_upload)
     * @param authenticator The request authenticator
     * @param listElements the list of elements to include in the signature
     * @return the response as a String
     * @throws HttpAccessException the exception if there is a problem
     */
    String callWSPostMultiPart( String strUrl, Map<String, List<String>> mapParameters,
        Map<String, FileItem> fileItems, RequestAuthenticator authenticator, List<String> listElements )
        throws HttpAccessException;

    /**
     * Call WS to download a file
     * @param strUrl the url of the file to download
     * @param strFilePath the file path to download the file
     * @param authenticator the request authenticator
     * @param listElements the list of elements to include in the signature
     * @throws HttpAccessException exception if there is an HTTP error
     */
    void callWSDownloadFile( String strUrl, String strFilePath, RequestAuthenticator authenticator,
        List<String> listElements ) throws HttpAccessException;

    /**
     * Call WS to download a file
     * @param strUrl the url of the file to download
     * @param authenticator the request authenticator
     * @param listElements the list of elements to include in the signature
     * @return a {@link FileItem}
     * @throws HttpAccessException exception if there is an HTTP error
     */
    FileItem callWSDownloadFile( String strUrl, RequestAuthenticator authenticator, List<String> listElements )
        throws HttpAccessException;
}
