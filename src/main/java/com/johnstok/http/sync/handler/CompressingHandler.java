/*-----------------------------------------------------------------------------
 * Copyright © 2014 Keith Webster Johnston.
 * All rights reserved.
 *
 * This file is part of http.
 *
 * http is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * http is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with http. If not, see <http://www.gnu.org/licenses/>.
 *---------------------------------------------------------------------------*/
package com.johnstok.http.sync.handler;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import com.johnstok.http.ClientHttpException;
import com.johnstok.http.ContentCoding;
import com.johnstok.http.Status;
import com.johnstok.http.sync.Handler;
import com.johnstok.http.sync.Request;
import com.johnstok.http.sync.Response;
import com.johnstok.http.sync.ResponseAdapter;


/**
 * A handler that performs response compression.
 *
 * @author Keith Webster Johnston.
 */
public class CompressingHandler
    extends
        NegotiatingHandler {

    private final Handler _delegate;
    private final Map<String, OutputStreamFactory> _supportedEncodings =
            new HashMap<>();


    /**
     * Constructor.
     *
     * @param handler
     */
    public CompressingHandler(final Handler handler) {
        _delegate = handler;
        _supportedEncodings.put(
            ContentCoding.GZIP.toString(), new GZIPOutputStreamFactory());
        _supportedEncodings.put(
            ContentCoding.IDENTITY.toString(), new IdentityOutputStreamFactory());
    }


    /** {@inheritDoc} */
    @Override
    public void handle(final Request request,
                       final Response response) throws IOException {
        final ContentCoding cc =
            negotiateContent(request, response, _supportedEncodings.keySet());
        if (null==cc) { throw new ClientHttpException(Status.NOT_ACCEPTABLE); }
        OutputStreamFactory bwf = _supportedEncodings.get(cc);
        _delegate.handle(request, new CompressingResponse(response, bwf));
    }


    static final class CompressingResponse
        extends
            ResponseAdapter {


        private final OutputStreamFactory _cc;


        /**
         * Constructor.
         *
         * @param response
         * @param cc
         */
        public CompressingResponse(final Response response,
                                   final OutputStreamFactory cc) {
            super(response);
            _cc = cc;
        }


        /** {@inheritDoc} */
        @Override
        public OutputStream getBody() throws IOException {
            return _cc.create(super.getBody());
        }
    }


    static interface OutputStreamFactory {
        OutputStream create(OutputStream os) throws IOException;
    }


    static class IdentityOutputStreamFactory implements OutputStreamFactory {
        /** {@inheritDoc} */
        @Override
        public OutputStream create(final OutputStream os) { return os; }
    }


    static class GZIPOutputStreamFactory implements OutputStreamFactory {
        /** {@inheritDoc} */
        @Override
        public OutputStream create(final OutputStream os) throws IOException {
            return new GZIPOutputStream(
                /* GZip OS, writes a header that commits the response – work
                 * around by buffering.                                   */
                new BufferedOutputStream(os, 256)); }
    }
}
