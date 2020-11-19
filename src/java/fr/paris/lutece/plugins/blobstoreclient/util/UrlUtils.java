/*
 * Copyright (c) 2002-2020, City of Paris
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
package fr.paris.lutece.plugins.blobstoreclient.util;

import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 *
 * URLUtils
 *
 */
public final class UrlUtils
{
    // CONSTANTS
    private static final String SLASH = "/";
    private static final String INTERROGATION_MARK = "?";
    private static final String EQUAL = "=";
    private static final String AMPERSAND = "&";
    private static final String REGEX_URL_PARAMETER = "([^&]+)";

    // PARAMETERS
    private static final String PARAMETER_BLOB_KEY = "blob_key";
    private static final String PARAMETER_BLOBSTORE = "blobstore";

    // PROPERTIES
    private static final String PROPERTY_URL_CREATE = "blobstoreclient.blobstore.rest.create.url";
    private static final String PROPERTY_URL_DELETE = "blobstoreclient.blobstore.rest.delete.url";
    private static final String PROPERTY_URL_FILE = "blobstoreclient.blobstore.rest.file.url";

    /**
     * Private constructor
     */
    private UrlUtils(  )
    {
    }

    /**
     * Build the url to upload a file.
     * <br />
     * This will append the url defined in <b>blobstoreclient.properties</b> to the given
     * base url
     * @param strBaseUrl the base url
     * @param strBlobStore the blobstore service name
     * @return the url to upload a file
     */
    public static String buildCreateBlobUrl( String strBaseUrl, String strBlobStore )
    {
        StringBuilder sbUrl = new StringBuilder( strBaseUrl );

        if ( sbUrl.toString(  ).endsWith( SLASH ) )
        {
            sbUrl.substring( 0, sbUrl.length(  ) - 1 );
        }

        sbUrl.append( AppPropertiesService.getProperty( PROPERTY_URL_CREATE ) );
        sbUrl.append( INTERROGATION_MARK + PARAMETER_BLOBSTORE + EQUAL + strBlobStore );

        return sbUrl.toString(  );
    }

    /**
     * Build the url to delete a file.
     * <br />
     * This will append the url defined in <b>blobstoreclient.properties</b> to the given
     * base url
     * @param strBaseUrl the base url
     * @return the url to delete a file
     */
    public static String buildDeleteBlobUrl( String strBaseUrl )
    {
        StringBuilder sbUrl = new StringBuilder( strBaseUrl );

        if ( sbUrl.toString(  ).endsWith( SLASH ) )
        {
            sbUrl.substring( 0, sbUrl.length(  ) - 1 );
        }

        sbUrl.append( AppPropertiesService.getProperty( PROPERTY_URL_DELETE ) );

        return sbUrl.toString(  );
    }

    /**
     * Build the url to get a file url.
     * <br />
     * This will append the url defined in <b>blobstoreclient.properties</b> to the given
     * base url
     * @param strBaseUrl the base url
     * @param strBlobStore the blobstore service name
     * @param strBlobKey the blob key
     * @return the url to delete a file
     */
    public static String buildFileUrl( String strBaseUrl, String strBlobStore, String strBlobKey )
    {
        StringBuilder sbUrl = new StringBuilder( strBaseUrl );

        if ( sbUrl.toString(  ).endsWith( SLASH ) )
        {
            sbUrl.substring( 0, sbUrl.length(  ) - 1 );
        }

        sbUrl.append( AppPropertiesService.getProperty( PROPERTY_URL_FILE ) );
        sbUrl.append( strBlobStore );
        sbUrl.append( SLASH );
        sbUrl.append( strBlobKey );
        sbUrl.append( INTERROGATION_MARK + PARAMETER_BLOBSTORE + EQUAL + strBlobStore );
        sbUrl.append( AMPERSAND + PARAMETER_BLOB_KEY + EQUAL + strBlobKey );

        return sbUrl.toString(  );
    }

    /**
     * Extract the parameter value from the url
     * @param strUrl the url
     * @param strParameter the parameter
     * @return the parameter value
     */
    public static String getParameterValue( String strUrl, String strParameter )
    {
        String strValue = StringUtils.EMPTY;

        try
        {
            Pattern p = Pattern.compile( strParameter + EQUAL + REGEX_URL_PARAMETER );
            Matcher m = p.matcher( strUrl );

            if ( m.find(  ) )
            {
                strValue = m.group( 1 );
            }
        }
        catch ( PatternSyntaxException e )
        {
            AppLogService.error( e );
        }

        return strValue;
    }

    /**
     * Get the blobstore service name from the url
     * @param strUrl the url
     * @return the blobstore service name
     */
    public static String getBlobStoreFromUrl( String strUrl )
    {
        return getParameterValue( strUrl, PARAMETER_BLOBSTORE );
    }

    /**
     * Get the blob key from the url
     * @param strUrl the url
     * @return the blob key
     */
    public static String getBlobKeyFromUrl( String strUrl )
    {
        return getParameterValue( strUrl, PARAMETER_BLOB_KEY );
    }
}
