/*-----------------------------------------------------------------------------
 * Copyright © 2013 Keith Webster Johnston.
 * All rights reserved.
 *
 * This file is part of http.
 *
 * http is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * http is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with http. If not, see <http://www.gnu.org/licenses/>.
 *---------------------------------------------------------------------------*/
package com.johnstok.http.sync;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;


/**
 * An helper class for decorating a request.
 *
 * @author Keith Webster Johnston.
 */
public class RequestAdapter
    implements
        Request {

    private final Request _delegate;


    /**
     * Constructor.
     *
     * @param delegate
     */
    public RequestAdapter(final Request delegate) {
        _delegate = delegate; // FIXME: Check for NULL.
    }


    /** {@inheritDoc} */
    @Override
    public String getRequestUri() {
        return _delegate.getRequestUri();
    }


    /** {@inheritDoc} */
    @Override
    public InetSocketAddress getServerAddress() {
        return _delegate.getServerAddress();
    }


    /** {@inheritDoc} */
    @Override
    public boolean isConfidential() {
        return _delegate.isConfidential();
    }


    /** {@inheritDoc} */
    @Override
    public String getMethod() {
        return _delegate.getMethod();
    }


    /** {@inheritDoc} */
    @Override
    public String getVersion() {
        return _delegate.getVersion();
    }


    /** {@inheritDoc} */
    @Override
    public InetSocketAddress getClientAddress() {
        return _delegate.getClientAddress();
    }


    /** {@inheritDoc} */
    @Override
    public String getHeader(final String headerName) {
        return _delegate.getHeader(headerName);
    }


    /** {@inheritDoc} */
    @Override
    public String getHeader(final String headerName,
                            final String defaultValue) {
        return _delegate.getHeader(headerName, defaultValue);
    }


    /** {@inheritDoc} */
    @Override
    public Map<String, List<String>> getHeaders() {
        return _delegate.getHeaders();
    }


    /** {@inheritDoc} */
    @Override
    public InputStream getBody() throws IOException {
        return _delegate.getBody();
    }


    /** {@inheritDoc} */
    @Override
    public boolean hasHeader(final String headerName) {
        return _delegate.hasHeader(headerName);
    }


    /** {@inheritDoc} */
    @Override
    public List<String> getHeaders(final String headerName) {
        return _delegate.getHeaders(headerName);
    }
}
