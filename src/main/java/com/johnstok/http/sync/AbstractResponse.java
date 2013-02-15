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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import com.johnstok.http.ContentCoding;
import com.johnstok.http.Header;
import com.johnstok.http.MediaType;
import com.johnstok.http.Status;
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
        if (_committed) {
            throw new IllegalStateException("Already committed.");
        }
        _committed = true;
        if (_variances.size()>0) {
            setHeader(
                Header.VARY,
                Utils.join(Arrays.asList(getVariances()), ',').toString());
        }
    }


    /** {@inheritDoc} */
    @Override
    public boolean isCommitted() {
        return _committed;
    }


    protected Status map(final int code) {
        switch (code) {
            case 202:
                return Status.ACCEPTED;
            case 502:
                return Status.BAD_GATEWAY;
            case 400:
                return Status.BAD_REQUEST;
            case 409:
                return Status.CONFLICT;
            case 201:
                return Status.CREATED;
            case 417:
                return Status.EXPECTATION_FAILED;
            case 403:
                return Status.FORBIDDEN;
            case 302:
                return Status.FOUND;
            case 504:
                return Status.GATEWAY_TIMEOUT;
            case 410:
                return Status.GONE;
            case 500:
                return Status.INTERNAL_SERVER_ERROR;
            case 411:
                return Status.LENGTH_REQUIRED;
            case 405:
                return Status.METHOD_NOT_ALLOWED;
            case 301:
                return Status.MOVED_PERMANENTLY;
            case 300:
                return Status.MULTIPLE_CHOICES;
            case 406:
                return Status.NOT_ACCEPTABLE;
            case 404:
                return Status.NOT_FOUND;
            case 501:
                return Status.NOT_IMPLEMENTED;
            case 304:
                return Status.NOT_MODIFIED;
            case 204:
                return Status.NO_CONTENT;
            case 200:
                return Status.OK;
            case 206:
                return Status.PARTIAL_CONTENT;
            case 402:
                return Status.PAYMENT_REQUIRED;
            case 412:
                return Status.PRECONDITION_FAILED;
            case 407:
                return Status.PROXY_AUTHENTICATION_REQUIRED;
            case 416:
                return Status.REQUESTED_RANGE_NOT_SATISFIABLE;
            case 413:
                return Status.REQUEST_ENTITY_TOO_LARGE;
            case 408:
                return Status.REQUEST_TIMEOUT;
            case 414:
                return Status.REQUEST_URI_TOO_LONG;
            case 205:
                return Status.RESET_CONTENT;
            case 303:
                return Status.SEE_OTHER;
            case 503:
                return Status.SERVICE_UNAVAILABLE;
            case 307:
                return Status.TEMPORARY_REDIRECT;
            case 401:
                return Status.UNAUTHORIZED;
            case 415:
                return Status.UNSUPPORTED_MEDIA_TYPE;
            case 305:
                return Status.USE_PROXY;
            case 505:
                return Status.VERSION_NOT_SUPPORTED;
            default:
                // FIXME: Use a subclass of RuntimeException.
                throw new RuntimeException("Unsupported Status.");
        }
    }
}
