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
import java.io.OutputStream;


/**
 * An helper class for decorating a response.
 *
 * @author Keith Webster Johnston.
 */
public class ResponseAdapter
    implements
        Response {

    private final Response _response;


    /**
     * Constructor.
     *
     * @param response The response this adapter will delegate to.
     */
    public ResponseAdapter(final Response response) {
        _response = response; // FIXME: Check for NULL.
    }


    /** {@inheritDoc} */
    @Override
    public void setHeader(final String name, final String value) {
        _response.setHeader(name, value);
    }


    /** {@inheritDoc} */
    @Override
    public String getHeader(final String name) {
        return _response.getHeader(name);
    }


    /** {@inheritDoc} */
    @Override
    public OutputStream getBody() throws IOException {
        return _response.getBody();
    }


    /** {@inheritDoc} */
    @Override
    public boolean isCommitted() {
        return _response.isCommitted();
    }


    /** {@inheritDoc} */
    @Override
    public void setStatus(final int code, final String message) {
        _response.setStatus(code, message);
    }


    /** {@inheritDoc} */
    @Override
    public int getStatusCode() {
        return _response.getStatusCode();
    }


    /** {@inheritDoc} */
    @Override
    public String getReasonPhrase() {
        return _response.getReasonPhrase();
    }
}
