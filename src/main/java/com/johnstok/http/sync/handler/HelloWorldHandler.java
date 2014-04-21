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
import java.nio.charset.StandardCharsets;
import com.johnstok.http.Header;
import com.johnstok.http.sync.Handler;
import com.johnstok.http.sync.Request;
import com.johnstok.http.sync.Response;
import com.johnstok.http.sync.writer.StringBodyWriter;


/**
 * Request handler that returns the body "Hello World!".
 *
 * @author Keith Webster Johnston.
 */
public class HelloWorldHandler
    implements
        Handler {

    /** {@inheritDoc} */
    @Override
    public void handle(final Request request,
                       final Response response) throws IOException {
        response.setHeader(Header.CONTENT_TYPE, "text/plain;charset=utf-8");
        new StringBodyWriter(
            "Hello World!", StandardCharsets.UTF_8)
            .write(response.getBody());
    }
}
