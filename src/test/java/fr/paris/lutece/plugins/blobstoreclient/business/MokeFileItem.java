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
package fr.paris.lutece.plugins.blobstoreclient.business;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;


/**
 *
 * MokeFileItem
 *
 */
public class MokeFileItem implements FileItem
{
    private static final long serialVersionUID = 511558501873337322L;
    private static final byte[] BYTE = { 0 };
    private static final String CONTENT_TYPE = "ContentType";
    private static final String FIELD_NAME = "FieldName";
    private static final String NAME = "Name";
    private static final String FILE_NAME = "testblob.txt";
    private static int SIZE = 123;
    private String _strResourcesDir;

    /**
     * Constructor
     * @param strResourcesDir the resouces dir
     */
    public MokeFileItem( String strResourcesDir )
    {
        _strResourcesDir = strResourcesDir;
    }

    /**
     * {@inheritDoc}
     */
    public void delete(  )
    {
    }

    /**
     * {@inheritDoc}
     */
    public byte[] get(  )
    {
        return BYTE;
    }

    /**
     * {@inheritDoc}
     */
    public String getContentType(  )
    {
        return CONTENT_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    public String getFieldName(  )
    {
        return FIELD_NAME;
    }

    /**
     * {@inheritDoc}
     */
    public InputStream getInputStream(  ) throws IOException
    {
        return new FileInputStream( _strResourcesDir + "../test-classes/" + FILE_NAME );
    }

    /**
     * {@inheritDoc}
     */
    public String getName(  )
    {
        return NAME;
    }

    /**
     * {@inheritDoc}
     */
    public OutputStream getOutputStream(  ) throws IOException
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public long getSize(  )
    {
        return SIZE;
    }

    /**
     * {@inheritDoc}
     */
    public String getString(  )
    {
        return NAME;
    }

    /**
     * {@inheritDoc}
     */
    public String getString( String strParam ) throws UnsupportedEncodingException
    {
        return NAME + strParam;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isFormField(  )
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isInMemory(  )
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public void setFieldName( String strFieldName )
    {
    }

    /**
     * {@inheritDoc}
     */
    public void setFormField( boolean bCondition )
    {
    }

    /**
     * {@inheritDoc}
     */
    public void write( File file ) throws Exception
    {
    }
}
