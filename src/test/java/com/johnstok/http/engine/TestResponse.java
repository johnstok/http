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
import com.johnstok.http.Status;
import com.johnstok.http.sync.AbstractResponse;


/**
 * Responsibility: in-memory representation of a HTTP response.
 *
 * @author Keith Webster Johnston.
 */
public class TestResponse
    extends
        AbstractResponse {

    private Status                              _status;
    private final SimpleDateFormat              _dateFormatter;
    private final HashMap<String, List<String>> _headers =
        new HashMap<String, List<String>>();
    private final ByteArrayOutputStream         _outputStream =
        new ByteArrayOutputStream();


    /**
     * Constructor.
     */
    public TestResponse() {
        _dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
        _dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }


    /** {@inheritDoc} */
    @Override
    public Status getStatus() {
        return _status;
    }


    /** {@inheritDoc} */
    @Override
    public void setStatus(final Status status) {
        _status = status;
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
    protected OutputStream getOutputStream() throws IOException {
        return _outputStream;
    }


    /** {@inheritDoc} */
    @Override
    protected void close() throws IOException {
        /* No Op */
    }
}
