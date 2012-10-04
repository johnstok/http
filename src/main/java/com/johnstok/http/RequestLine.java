/*-----------------------------------------------------------------------------
 * Copyright © 2012 Keith Webster Johnston.
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
 * along with http.  If not, see <http://www.gnu.org/licenses/>.
 *---------------------------------------------------------------------------*/
package com.johnstok.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A HTTP request's request line.
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="5.1")
public class RequestLine {

    public static final String SYNTAX =
        "("+Method.SYNTAX+") (["+RequestURI.SYNTAX+"]+) ("+Version.SYNTAX+")";


    private final String _method;
    private final String _uri;
    private final String _version;


    /**
     * Constructor.
     *
     * @param method  The HTTP method for the request.
     * @param uri     The requested URI.
     * @param version The HTTP version for the request.
     */
    public RequestLine(final String method,
                       final String uri,
                       final String version) {
        _method = method;
        _uri = uri;
        _version = version;
    }


    /**
     * Accessor.
     *
     * @return Returns the method.
     */
    public final String getMethod() {
        return _method;
    }


    /**
     * Accessor.
     *
     * @return Returns the URI.
     */
    public final String getUri() {
        return _uri;
    }


    /**
     * Accessor.
     *
     * @return Returns the version.
     */
    public final String getVersion() {
        return _version;
    }


    /**
     * Parse a string into a request line.
     *
     * @param requestLineString A string representing the request line.
     *
     * @return A corresponding request line object.
     */
    public static RequestLine parse(final String requestLineString) {
        final Matcher m = Pattern.compile(SYNTAX).matcher(requestLineString);
        if (m.matches()) {
            return new RequestLine(m.group(1), m.group(2), m.group(3));
        }
        throw new ClientHttpException(Status.BAD_REQUEST);
    }
}
