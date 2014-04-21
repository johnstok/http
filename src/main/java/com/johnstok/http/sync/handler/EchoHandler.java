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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import com.johnstok.http.sync.BodyWriter;
import com.johnstok.http.sync.Handler;
import com.johnstok.http.sync.Request;
import com.johnstok.http.sync.Response;


/**
 * Echo's the request line for debug purposes.
 *
 * @author Keith Webster Johnston.
 */
public class EchoHandler
    implements
        Handler {

    /** {@inheritDoc} */
    @Override
    public void handle(final Request request,
                       final Response response) throws IOException {
        new BodyWriter() {
            @Override
            public void write(final OutputStream outputStream) throws IOException {
                OutputStreamWriter w =
                    new OutputStreamWriter(outputStream, "UTF-8");
                w.write(request.getVersion()+" "+request.getMethod()+" "+request.getRequestUri());
                w.write('\n');
                w.write('\n');
                for (Map.Entry<String, List<String>> h : request.getHeaders().entrySet()) {
                    w.write(h.getKey());
                    w.write(": ");
                    w.write(String.join(",", h.getValue()));
                    w.write('\n');
                }
                w.flush();
            }
        }.write(response.getBody());
    }
}
