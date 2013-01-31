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

import java.nio.charset.Charset;
import java.util.Date;
import com.johnstok.http.MediaType;
import com.johnstok.http.Status;


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
     * @param response
     */
    public ResponseAdapter(final Response response) {
        _response = response; // FIXME: Check for NULL.
    }


    /** {@inheritDoc} */
    @Override
    public void setStatus(final Status code) {
        _response.setStatus(code);
    }


    /** {@inheritDoc} */
    @Override
    public Status getStatus() {
        return _response.getStatus();
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
    public void write(final BodyWriter value) {
        _response.write(value);
    }


    /** {@inheritDoc} */
    @Override
    public boolean hasBody() {
        return _response.hasBody();
    }


    /** {@inheritDoc} */
    @Override
    public Date getOriginationTime() {
        return _response.getOriginationTime();
    }


    /** {@inheritDoc} */
    @Override
    public void setHeader(final String name, final Date value) {
        _response.setHeader(name, value);
    }


    /** {@inheritDoc} */
    @Override
    public void setCharset(final Charset charset) {
        _response.setCharset(charset);
    }


    /** {@inheritDoc} */
    @Override
    public void setMediaType(final MediaType mediaType) {
        _response.setMediaType(mediaType);
    }


    /** {@inheritDoc} */
    @Override
    public void setContentEncoding(final String encoding) {
        _response.setContentEncoding(encoding);
    }


    /** {@inheritDoc} */
    @Override
    public MediaType getMediaType() {
        return _response.getMediaType();
    }


    /** {@inheritDoc} */
    @Override
    public String[] getVariances() {
        return _response.getVariances();
    }


    /** {@inheritDoc} */
    @Override
    public void addVariance(final String headerName) {
        _response.addVariance(headerName);
    }
}
