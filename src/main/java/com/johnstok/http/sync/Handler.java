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


/**
 * API for synchronous HTTP request handling.
 *
 * @author Keith Webster Johnston.
 */
public interface Handler {

    /**
     * Handle a HTTP request.
     *
     * @param request  The incoming HTTP request.
     * @param response The outgoing HTTP response.
     *
     * @throws IOException If reading from / writing to the client fails.
     */
    void handle(Request request, Response response) throws IOException;
}
