/*-----------------------------------------------------------------------------
 * Copyright © 2013 Keith Webster Johnston.
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
package com.johnstok.http.engine;

import com.johnstok.http.sync.Handler;
import com.johnstok.http.sync.Request;
import com.johnstok.http.sync.Response;


/**
 * A HTTP handler that delegates to a REST engine.
 *
 * @author Keith Webster Johnston.
 */
public class RESTfulHandler
    implements
        Handler {

    private final Dispatcher _dispatcher;


    /**
     * Constructor.
     *
     * @param dispatcher
     */
    public RESTfulHandler(final Dispatcher dispatcher) {
        _dispatcher = dispatcher;
    }


    /** {@inheritDoc} */
    @Override
    public void handle(final Request request, final Response response) {
        final Resource r = _dispatcher.dispatch(request, response);
        new Engine().process(r, request, response);
    }
}
