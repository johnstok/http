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

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import com.johnstok.http.ContentCoding;
import com.johnstok.http.Header;
import com.johnstok.http.WeightedValue;
import com.johnstok.http.headers.VaryHeader;
import com.johnstok.http.negotiation.ContentNegotiator;
import com.johnstok.http.sync.Handler;
import com.johnstok.http.sync.Request;
import com.johnstok.http.sync.Response;


/**
 * A handler implementation that provides server-driven content negotiation.
 *
 * @author Keith Webster Johnston.
 */
@SuppressWarnings("static-method")
public abstract class NegotiatingHandler
    implements
        Handler {


    /**
     * Negotiate the content encoding.
     *
     * @param request
     * @param response
     * @param supportedEncodings
     *
     * @return
     */
    protected ContentCoding negotiateContent(
                                       final Request request,
                                       final Response response,
                                       final Set<String> supportedEncodings) {
        final List<WeightedValue> clientEncodings =
            Header.parseAcceptEncoding(
                request.getHeader(Header.ACCEPT_ENCODING));
        ContentNegotiator cn = new ContentNegotiator(supportedEncodings);
        final ContentCoding cc = cn.select(clientEncodings);
        response.setHeader(Header.CONTENT_ENCODING, cc.toString());
        VaryHeader vh = new VaryHeader();
        SortedSet<String> variances = vh.parse(response.getHeader(Header.VARY));
        variances.add(Header.CONTENT_ENCODING);
        response.setHeader(Header.VARY, vh.write(variances));
        return cc;
    }
}
