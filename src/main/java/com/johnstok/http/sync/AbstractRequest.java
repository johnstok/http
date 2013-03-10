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
import java.nio.charset.Charset;
import java.util.List;
import com.johnstok.http.engine.Utils;


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
        _address = Utils.checkNotNull(address);
        _uriCharset = Utils.checkNotNull(uriCharset);
    }


    /** {@inheritDoc} */
    @Override
    public final boolean hasHeader(final String headerName) {
        return null!=getHeader(headerName);
    }


    /** {@inheritDoc} */
    @Override
    public final String getHeader(final String headerName) {
        return getHeader(headerName, null);
    }


    /** {@inheritDoc} */
    @Override
    public final List<String> getHeaders(final String headerName) {
        return getHeaders().get(headerName);
    }


    /** {@inheritDoc} */
    @Override
    public final String getHeader(final String headerName,
                                  final String defaultValue) {
        List<String> values = getHeaders(headerName);
        if ((null==values) || (0==values.size())) { return defaultValue; }
        return values.get(0);
    }


    /** {@inheritDoc} */
    @Override
    public InetSocketAddress getServerAddress() { return _address; }
}
