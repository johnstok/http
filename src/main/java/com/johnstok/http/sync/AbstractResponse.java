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

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import com.johnstok.http.ContentCoding;
import com.johnstok.http.Header;
import com.johnstok.http.MediaType;
import com.johnstok.http.engine.Utils;
import com.johnstok.http.headers.DateHeader;


/**
 * Provides default implementations of many {@link Response} API methods.
 *
 * @author Keith Webster Johnston.
 */
public abstract class AbstractResponse
    implements
        Response {

    private final Date         _originationTime = new Date();
    private final List<String> _variances = new ArrayList<String>();

    private Charset            _charset;
    private MediaType          _mediaType;
    private String             _contentEncoding;
    private boolean            _committed;


    /** {@inheritDoc} */
    @Override
    public Date getOriginationTime() {
        return _originationTime; // TODO: Make defensive copy?
    }


    /** {@inheritDoc} */
    @Override
    public void setCharset(final Charset charset) {
        _charset = charset;
        setContentType();
    }


    private void setContentType() {
        // FIXME: Handle setting charset when no media type is set.
        setHeader(
            Header.CONTENT_TYPE,
            _mediaType+((null==_charset) ? "" : "; charset="+_charset));
    }


    /** {@inheritDoc} */
    @Override
    public void setMediaType(final MediaType mediaType) {
        _mediaType = mediaType;
        setContentType();
    }


    /** {@inheritDoc} */
    @Override
    public MediaType getMediaType() {
        return _mediaType;
    }


    /** {@inheritDoc} */
    @Override
    public void setContentEncoding(final String encoding) {
        if (ContentCoding.IDENTITY.equals(encoding)) {
            // Don't send a header for 'identity'.
            // FIXME: This doesn't seem to be working - still need code in Engine class for some reason.
            _contentEncoding = null;
            setHeader(Header.CONTENT_ENCODING, (String) null);
        } else {
            _contentEncoding = encoding;
            setHeader(Header.CONTENT_ENCODING, encoding);
        }
    }


    /** {@inheritDoc} */
    @Override
    public String[] getVariances() { // TODO: Why do we return an array here?
        return _variances.toArray(new String[_variances.size()]);
    }


    /** {@inheritDoc} */
    @Override
    public void addVariance(final String headerName) {
        _variances.add(headerName);
    }


    /** {@inheritDoc} */
    @Override
    public void setHeader(final String name, final Date value) {
        setHeader(name, DateHeader.format(value));
    }


    /**
     * Perform final actions on the response prior to writing the body.
     */
    protected void commit() throws IOException {
        _committed = true;
        if (_variances.size()>0) {
            setHeader(
                Header.VARY,
                Utils.join(Arrays.asList(getVariances()), ',').toString());
        }
    }


    /** {@inheritDoc} */
    @Override
    public void write(final BodyWriter value) {
        try {
            commit();
            value.write(getOutputStream());
        } catch (IOException e) {
            e.printStackTrace(); // FIXME: Log the error!
        } finally {
            try {
                close();
            } catch (IOException e) {
                e.printStackTrace(); // FIXME: Log the error!
            }
        }
    }


    /**
     * Query if the status line and headers have been sent to the client.
     */
    public boolean isCommitted() {
        return _committed;
    }


    protected abstract OutputStream getOutputStream() throws IOException;


    protected abstract void close() throws IOException;
}
