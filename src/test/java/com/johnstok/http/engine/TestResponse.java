/*-----------------------------------------------------------------------------
 * Copyright © 2011 Keith Webster Johnston.
 * All rights reserved.
 *
 * Revision      $Rev$
 *---------------------------------------------------------------------------*/
package com.johnstok.http.engine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import com.johnstok.http.sync.AbstractResponse;


/**
 * Responsibility: in-memory representation of a HTTP response.
 *
 * @author Keith Webster Johnston.
 */
public class TestResponse
    extends
        AbstractResponse {

    private final SimpleDateFormat              _dateFormatter;
    private final HashMap<String, List<String>> _headers =
        new HashMap<String, List<String>>();
    private final ByteArrayOutputStream         _outputStream =
        new ByteArrayOutputStream();
    private String _reasonPhrase;
    private int    _code;


    /**
     * Constructor.
     */
    public TestResponse() {
        _dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
        _dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }


    /** {@inheritDoc} */
    @Override
    public void setHeader(final String name, final String value) {
        final ArrayList<String> values = new ArrayList<String>();
        values.add(value);
        _headers.put(name, values);
    }


    @Override
    public String getHeader(final String name) {
        final List<String> values = _headers.get(name);
        return ((null==values) || (0==values.size())) ? null : values.get(0);
    }


    public String getBodyAsString(final Charset charset) {
        return new String(_outputStream.toByteArray(), charset);
    }


    public void setBody(final byte[] body) throws IOException {
        _outputStream.reset();
        _outputStream.write(body);
    }


    /** {@inheritDoc} */
    @Override
    public void setHeader(final String name, final Date value) {
        setHeader(name, _dateFormatter.format(value));
    }


    /** {@inheritDoc} */
    @Override
    public OutputStream getBody() throws IOException {
        return _outputStream;
    }


    /** {@inheritDoc} */
    @Override
    public void close() throws IOException {
        /* No Op */
    }


    /** {@inheritDoc} */
    @Override
    public void setStatus(final int code, final String reasonPhrase) {
        _code = code;
        _reasonPhrase = reasonPhrase;
    }


    /** {@inheritDoc} */
    @Override
    public int getStatusCode() {
        return _code;
    }


    /** {@inheritDoc} */
    @Override
    public String getReasonPhrase() {
        return _reasonPhrase;
    }
}
