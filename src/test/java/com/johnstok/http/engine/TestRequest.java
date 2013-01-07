/*-----------------------------------------------------------------------------
 * Copyright © 2011 Keith Webster Johnston.
 * All rights reserved.
 *
 * Revision      $Rev$
 *---------------------------------------------------------------------------*/
package com.johnstok.http.engine;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.johnstok.http.Method;
import com.johnstok.http.Version;
import com.johnstok.http.sync.AbstractRequest;
import com.johnstok.http.sync.Request;


/**
 * Test implementation of the {@link Request} API.
 *
 * @author Keith Webster Johnston.
 */
public class TestRequest
    extends
        AbstractRequest {

    private       Method                        _method = Method.GET;
    private final HashMap<String, List<String>> _headers;
    private       byte[]                        _body;
    private       boolean                       _confidential;
    private final Version                       _version;
    private final String                        _uri;


    /**
     * Constructor.
     */
    public TestRequest() {
        super(
            new InetSocketAddress("localhost", 80),                //$NON-NLS-1$
            Charset.forName("UTF-8"));                             //$NON-NLS-1$
        _version = new Version(1, 1);
        _headers = new HashMap<String, List<String>>();
        _uri = "/";                                                //$NON-NLS-1$
    }


    /** {@inheritDoc} */
    @Override
    public InputStream getBody() {
        return new ByteArrayInputStream(_body);
    }


    /** {@inheritDoc} */
    @Override
    public String getHeader(final String headerName,
                            final String defaultValue) {
        final List<String> values = _headers.get(headerName);
        if ((null==values) || (0==values.size())) {
            return defaultValue;
        }
        return values.get(0);
    }


    /** {@inheritDoc} */
    @Override
    public Map<String, List<String>> getHeaders() { return _headers; }


    /** {@inheritDoc} */
    @Override
    public Method getMethod() { return _method; }


    /** {@inheritDoc} */
    @Override
    public InetSocketAddress getClientAddress() {
        throw new UnsupportedOperationException("Method not implemented.");
    }


    /** {@inheritDoc} */
    @Override
    public Map<String, List<String>> getQueryValues() {
        throw new UnsupportedOperationException("Method not implemented.");
    }


    /** {@inheritDoc} */
    @Override
    public String getQueryValue(final String paramName,
                                final String defaultValue) {
        throw new UnsupportedOperationException("Method not implemented.");
    }


    /** {@inheritDoc} */
    @Override
    public Version getVersion() { return _version; }


    /**
     * Replace the incoming request body with this for the rest of the
     * processing.
     *
     * @param bytes The new body to set.
     *
     * @return Returns this request.
     */
    public Request setBody(final byte[] bytes) {
        _body = bytes;
        return this;
    }


    /**
     * TODO: Add a description for this method.
     *
     * @param name
     * @param value
     */
    public void setHeader(final String name, final String value) {
        final ArrayList<String> values = new ArrayList<String>();
        values.add(value);
        _headers.put(name, values);
    }


    /**
     * Mutator.
     *
     * @param method The new HTTP method to set.
     */
    public void setMethod(final Method method) { _method = method; }


    /** {@inheritDoc} */
    @Override
    public boolean isConfidential() { return _confidential; }


    /** {@inheritDoc} */
    @Override
    public String getRequestUri() {
        return _uri;
    }
}
