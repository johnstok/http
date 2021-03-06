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

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;
import com.johnstok.http.RequestURI;
import com.johnstok.http.Status;
import com.johnstok.http.sync.Handler;
import com.johnstok.http.sync.Request;
import com.johnstok.http.sync.Response;


/**
 * Performs simple dispatching based on a regular expression.
 *
 * @author Keith Webster Johnston.
 */
public class DispatchingHandler
    implements
        Handler {

    private final Map<Pattern, Handler> _handlers;


    public DispatchingHandler(final Map<Pattern, Handler> handlers) {
        _handlers = handlers; // FIXME: Check for NULL.
    }


    /** {@inheritDoc} */
    @Override
    public void handle(final Request request,
                       final Response response) throws IOException {

        final String path =
            RequestURI.parse(request.getRequestUri()).toUri().getRawPath(); // FIXME: What if the request URI is an authority?!

        for (Pattern p : _handlers.keySet()) {
            if (p.matcher(path).matches()) {
                _handlers.get(p).handle(request, response);
                return;
            }
        }

        response.setStatus(Status.NOT_FOUND.getCode(), Status.NOT_FOUND.getReasonPhrase());
    }
}
