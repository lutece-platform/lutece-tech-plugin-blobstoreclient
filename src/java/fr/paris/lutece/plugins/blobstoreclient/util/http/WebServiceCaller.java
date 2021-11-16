/*
 * Copyright (c) 2002-2021, City of Paris
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

import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.httpaccess.HttpAccess;
import fr.paris.lutece.util.httpaccess.HttpAccessException;
import fr.paris.lutece.util.signrequest.HeaderHashAuthenticator;
import fr.paris.lutece.util.signrequest.NoSecurityAuthenticator;
import fr.paris.lutece.util.signrequest.RequestAuthenticator;
import fr.paris.lutece.util.signrequest.RequestHashAuthenticator;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * WebServiceCaller
 *
 */
public class WebServiceCaller implements IWebServiceCaller
{
    /**
     * {@inheritDoc}
     */
    public String callWSGetFileName( String strUrl ) throws HttpAccessException
    {
        String strResponse = StringUtils.EMPTY;

        if ( AppLogService.isDebugEnabled( ) )
        {
            AppLogService.debug( trace( strUrl ) );
        }

        try
        {
            HttpAccess httpAccess = new HttpAccess( );
            strResponse = httpAccess.getFileName( strUrl );
        }
        catch( HttpAccessException e )
        {
            AppLogService.error( buildErrorMessage( strUrl ) + e.getMessage( ), e );
            throw new HttpAccessException( buildErrorMessage( strUrl ), e );
        }

        return strResponse;
    }

    /**
     * {@inheritDoc}
     */
    public String callWSGet( String strUrl, RequestAuthenticator authenticator, List<String> listElements ) throws HttpAccessException
    {
        String strResponse = StringUtils.EMPTY;

        if ( AppLogService.isDebugEnabled( ) )
        {
            AppLogService.debug( trace( strUrl, authenticator, listElements ) );
        }

        try
        {
            HttpAccess httpAccess = new HttpAccess( );
            strResponse = httpAccess.doGet( strUrl, authenticator, listElements );
        }
        catch( HttpAccessException e )
        {
            AppLogService.error( buildErrorMessage( strUrl ) + e.getMessage( ), e );
            throw new HttpAccessException( buildErrorMessage( strUrl ), e );
        }

        return strResponse;
    }

    /**
     * {@inheritDoc}
     */
    public String callWSPost( String strUrl, Map<String, List<String>> mapParameters, RequestAuthenticator authenticator, List<String> listElements )
            throws HttpAccessException
    {
        String strResponse = StringUtils.EMPTY;

        if ( AppLogService.isDebugEnabled( ) )
        {
            AppLogService.debug( trace( strUrl, authenticator, listElements ) );
        }

        try
        {
            HttpAccess httpAccess = new HttpAccess( );
            strResponse = httpAccess.doPostMultiValues( strUrl, mapParameters, authenticator, listElements );
        }
        catch( HttpAccessException e )
        {
            AppLogService.error( buildErrorMessage( strUrl ) + e.getMessage( ), e );
            throw new HttpAccessException( buildErrorMessage( strUrl ), e );
        }

        return strResponse;
    }

    /**
     * {@inheritDoc}
     */
    public String callWSPostMultiPart( String strUrl, Map<String, List<String>> mapParameters, Map<String, FileItem> listFileItems,
            RequestAuthenticator authenticator, List<String> listElements ) throws HttpAccessException
    {
        String strResponse = StringUtils.EMPTY;

        if ( AppLogService.isDebugEnabled( ) )
        {
            AppLogService.debug( trace( strUrl, authenticator, listElements ) );
        }

        try
        {
            HttpAccess httpAccess = new HttpAccess( );
            strResponse = httpAccess.doPostMultiPart( strUrl, mapParameters, listFileItems, authenticator, listElements );
        }
        catch( HttpAccessException e )
        {
            AppLogService.error( buildErrorMessage( strUrl ) + e.getMessage( ), e );
            throw new HttpAccessException( buildErrorMessage( strUrl ), e );
        }

        return strResponse;
    }

    /**
     * {@inheritDoc}
     */
    public void callWSDownloadFile( String strUrl, String strFilePath, RequestAuthenticator authenticator, List<String> listElements )
            throws HttpAccessException
    {
        try
        {
            HttpAccess httpAccess = new HttpAccess( );
            httpAccess.downloadFile( strUrl, strFilePath );
        }
        catch( HttpAccessException e )
        {
            AppLogService.error( buildErrorMessage( strUrl ) + e.getMessage( ), e );
            throw new HttpAccessException( buildErrorMessage( strUrl ), e );
        }
    }

    /**
     * {@inheritDoc}
     */
    public FileItem callWSDownloadFile( String strUrl, RequestAuthenticator authenticator, List<String> listElements ) throws HttpAccessException
    {
        try
        {
            HttpAccess httpAccess = new HttpAccess( );

            return httpAccess.downloadFile( strUrl );
        }
        catch( HttpAccessException e )
        {
            AppLogService.error( buildErrorMessage( strUrl ) + e.getMessage( ), e );
            throw new HttpAccessException( buildErrorMessage( strUrl ), e );
        }
    }

    /**
     * Trace the web service call
     * 
     * @param strUrl
     *            The WS URI
     * @return The trace
     */
    protected String trace( String strUrl )
    {
        return trace( strUrl, null, null, null, null );
    }

    /**
     * Trace the web service call
     * 
     * @param strUrl
     *            The WS URI
     * @param authenticator
     *            The Request Authenticator
     * @param listElements
     *            The list of elements to use to build the signature
     * @return The trace
     */
    protected String trace( String strUrl, RequestAuthenticator authenticator, List<String> listElements )
    {
        return trace( strUrl, null, null, authenticator, listElements );
    }

    /**
     * Trace the web service call
     * 
     * @param strUrl
     *            The WS URI
     * @param mapParameters
     *            The parameters
     * @param authenticator
     *            The Request Authenticator
     * @param listElements
     *            The list of elements to use to build the signature
     * @return The trace
     */
    protected String trace( String strUrl, Map<String, List<String>> mapParameters, RequestAuthenticator authenticator, List<String> listElements )
    {
        return trace( strUrl, mapParameters, null, authenticator, listElements );
    }

    /**
     * Trace the web service call
     * 
     * @param strUrl
     *            The WS URI
     * @param mapParameters
     *            The parameters
     * @param fileItems
     *            the file to upload - map of (parameter_name, file_to_upload)
     * @param authenticator
     *            The Request Authenticator
     * @param listElements
     *            The list of elements to use to build the signature
     * @return The trace
     */
    protected String trace( String strUrl, Map<String, List<String>> mapParameters, Map<String, FileItem> fileItems, RequestAuthenticator authenticator,
            List<String> listElements )
    {
        StringBuilder sbTrace = new StringBuilder( );
        sbTrace.append( "\n ---------------------- BlobStore Client WebService Call -------------------" );
        sbTrace.append( "\nWebService URL : " ).append( strUrl );

        if ( mapParameters != null )
        {
            sbTrace.append( "\nParameters : " );

            for ( Entry<String, List<String>> parameter : mapParameters.entrySet( ) )
            {
                for ( String strValue : parameter.getValue( ) )
                {
                    sbTrace.append( "\n   " ).append( parameter.getKey( ) ).append( ":" ).append( strValue );
                }
            }
        }

        if ( fileItems != null )
        {
            sbTrace.append( "\nFichiers : " );

            for ( Entry<String, FileItem> paramFileItem : fileItems.entrySet( ) )
            {
                FileItem fileItem = paramFileItem.getValue( );
                sbTrace.append( "\n   Parametre : " ).append( paramFileItem.getKey( ) );
                sbTrace.append( "\n   Nom : " ).append( fileItem.getName( ) );
                sbTrace.append( " - Taille : " ).append( fileItem.getSize( ) );
            }
        }

        if ( ( authenticator != null ) && ( listElements != null ) )
        {
            sbTrace.append( "\nSecurity : " );
            sbTrace.append( "\nValues used for the request signature : " );

            for ( String strValue : listElements )
            {
                sbTrace.append( "\n   " ).append( strValue );
            }

            if ( authenticator instanceof RequestHashAuthenticator )
            {
                RequestHashAuthenticator auth = (RequestHashAuthenticator) authenticator;
                String strTimestamp = "" + new Date( ).getTime( );
                String strSignature = auth.buildSignature( listElements, strTimestamp );
                sbTrace.append( "\n Request Authenticator : RequestHashAuthenticator" );
                sbTrace.append( "\n Timestamp sample : " ).append( strTimestamp );
                sbTrace.append( "\n Signature for this timestamp : " ).append( strSignature );
            }
            else
                if ( authenticator instanceof HeaderHashAuthenticator )
                {
                    HeaderHashAuthenticator auth = (HeaderHashAuthenticator) authenticator;
                    String strTimestamp = Long.toString( new Date( ).getTime( ) );
                    String strSignature = auth.buildSignature( listElements, strTimestamp );
                    sbTrace.append( "\n Request Authenticator : HeaderHashAuthenticator" );
                    sbTrace.append( "\n Timestamp sample : " ).append( strTimestamp );
                    sbTrace.append( "\n Signature for this timestamp : " ).append( strSignature );
                }
                else
                    if ( authenticator instanceof NoSecurityAuthenticator )
                    {
                        sbTrace.append( "\n No request authentification" );
                    }
                    else
                    {
                        sbTrace.append( "\n Unknown Request authenticator" );
                    }
        }

        sbTrace.append( "\n --------------------------------------------------------------------" );

        return sbTrace.toString( );
    }

    /**
     * Build the error message
     * 
     * @param strUrl
     *            the url
     * @return the error message
     */
    private String buildErrorMessage( String strUrl )
    {
        return "Error connecting to '" + strUrl + "' : ";
    }
}
