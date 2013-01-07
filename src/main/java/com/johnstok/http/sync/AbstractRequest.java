/*-----------------------------------------------------------------------------
 * Copyright © 2012 Keith Webster Johnston.
 * All rights reserved.
 *
 * This file is part of http.
 *
 * http is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * http is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with http. If not, see <http://www.gnu.org/licenses/>.
 *---------------------------------------------------------------------------*/
package com.johnstok.http.sync;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import com.johnstok.http.Path;
import com.johnstok.http.ServerHttpException;
import com.johnstok.http.Status;


/**
 * Provides default implementations of many {@link Request} API methods.
 *
 * @author Keith Webster Johnston.
 */
public abstract class AbstractRequest
    implements
        Request {

    private   final InetSocketAddress _address;
    protected final Charset           _uriCharset;


    /**
     * Constructor.
     *
     * @param address    The server address that received this request.
     * @param uriCharset The character set used to parse the request URI.
     */
    public AbstractRequest(final InetSocketAddress address,
                           final Charset uriCharset) {
        _address = address; // TODO: Not null.
        _uriCharset = uriCharset;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasQueryValue(final String paramName) {
        return null!=getQueryValue(paramName);
    }


    /** {@inheritDoc} */
    @Override
    public boolean hasHeader(final String headerName) {
        return null!=getHeader(headerName);
    }


    /** {@inheritDoc} */
    @Override
    public String getQueryValue(final String paramName) {
        return getQueryValue(paramName, null);
    }


    /** {@inheritDoc} */
    @Override
    public String getHeader(final String headerName) {
        return getHeader(headerName, null);
    }


    /** {@inheritDoc} */
    @Override
    public InetSocketAddress getServerAddress() { return _address; }


    /** {@inheritDoc} */
    @Override
    public final Path getPath() {
        try {
            URI uri = new URI(getRequestUri());
            return new Path(uri.getRawPath(), _uriCharset);
        } catch (URISyntaxException e) {
            throw new ServerHttpException(Status.INTERNAL_SERVER_ERROR, e); // FIXME: This can happen if the request was not for a resource ("*").
        }
    }
}
