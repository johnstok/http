/*-----------------------------------------------------------------------------
 * Copyright © 2013 Keith Webster Johnston.
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
package com.johnstok.http.sync.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.johnstok.http.sync.Filter;
import com.johnstok.http.sync.Handler;
import com.johnstok.http.sync.Request;
import com.johnstok.http.sync.Response;


/**
 * A filter that logs a request.
 *
 * @author Keith Webster Johnston.
 */
public class LoggingFilter extends Filter {

    /**
     * Constructor.
     *
     * @param delegate The handler to call next.
     */
    public LoggingFilter(final Handler delegate) {
        super(delegate);
    }


    /** {@inheritDoc} */
    @Override
    public void handle(final Request request,
                       final Response response) throws IOException {
        System.out.println(
            request.getMethod()+" "
            + request.getRequestUri()+" "
            + request.getVersion());
        for (Map.Entry<String, List<String>> header :
            request.getHeaders().entrySet() ) {
            System.out.println("  "+header.getKey()+": "+header.getValue());
        }

        getDelegate().handle(request, response);
    }
}
