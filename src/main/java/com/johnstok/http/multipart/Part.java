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
package com.johnstok.http.multipart;

import java.util.HashMap;
import java.util.Map;


/**
 * A single part of a multipart message.
 *
 * @author Keith Webster Johnston.
 */
public class Part {

    private byte[]              _body    = new byte[0];
    private Map<String, String> _headers = new HashMap<String, String>();


    /**
     * Add a header for this part.
     *
     * @param key   The header's name.
     * @param value The header's value.
     */
    public void addHeader(final String key, final String value) {
        _headers.put(key, value);
    }


    /**
     * Accessor.
     *
     * @return Returns the body.
     */
    public byte[] getBody() {
        return _body;
    }


    /**
     * Accessor.
     *
     * @param key The header's key.
     *
     * @return Returns the header value.
     */
    public String getHeader(final String key) {
        return _headers.get(key);
    }


    /**
     * Accessor.
     *
     * @return Returns the headers.
     */
    public Map<String, String> getHeaders() {
        return _headers;
    }


    /**
     * Mutator.
     *
     * @param body The body to set.
     */
    public void setBody(final byte[] body) {
        _body = body;
    }


    /**
     * Mutator.
     *
     * @param headers The headers to set.
     */
    public void setHeaders(final Map<String, String> headers) {
        _headers = headers;
    }
}
