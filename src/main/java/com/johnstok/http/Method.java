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
package com.johnstok.http;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * HTTP methods.
 *
 * @author Keith Webster Johnston.
 */
@Specifications({
    @Specification(name="rfc-2616", section="5.1.1"),
    @Specification(name="rfc-2616", section="9")
})
public final class Method {

    public static final String SYNTAX =
        "OPTIONS|GET|HEAD|POST|PUT|DELETE|TRACE|CONNECT";

    private static final Set<String> KNOWN_METHODS =
        new HashSet<String>(Arrays.asList(
            Method.GET,
            Method.HEAD,
            Method.OPTIONS,
            Method.DELETE,
            Method.POST,
            Method.TRACE,
            Method.CONNECT,
            Method.PUT
        ));

    /** GET : String. */
    @Specification(name="rfc-2616", section="9.3")
    public static final String GET  = "GET";                       //$NON-NLS-1$

    /** HEAD : String. */
    @Specification(name="rfc-2616", section="9.4")
    public static final String HEAD = "HEAD";                      //$NON-NLS-1$

    /** OPTIONS : String. */
    @Specification(name="rfc-2616", section="9.2")
    public static final String OPTIONS = "OPTIONS";                //$NON-NLS-1$

    /** DELETE : String. */
    @Specification(name="rfc-2616", section="9.7")
    public static final String DELETE = "DELETE";                  //$NON-NLS-1$

    /** PUT : boolean. */
    @Specification(name="rfc-2616", section="9.6")
    public static final String PUT = "PUT";                        //$NON-NLS-1$

    /** POST : String. */
    @Specification(name="rfc-2616", section="9.5")
    public static final String POST = "POST";                      //$NON-NLS-1$

    /** TRACE : String. */
    @Specification(name="rfc-2616", section="9.8")
    public static final String TRACE = "TRACE";                     //$NON-NLS-1$

    /** CONNECT : String. */
    @Specification(name="rfc-2616", section="9.9")
    public static final String CONNECT = "CONNECT";                //$NON-NLS-1$


    private Method() { super(); }


    @Specification(name="rfc-2616", section="9.1.1")
    public boolean isSafe() {
        throw new UnsupportedOperationException();
    }


    @Specification(name="rfc-2616", section="9.1.2")
    public boolean isIdempotent() {
        throw new UnsupportedOperationException();
    }


    public boolean isResponseCacheable() {
        throw new UnsupportedOperationException();
    }


    public boolean isEntityRequired() {
        throw new UnsupportedOperationException();
    }


    public boolean isEntityAllowed() {
        throw new UnsupportedOperationException();
    }


    /**
     * All known HTTP methods.
     *
     * @return The set of known HTTP methods.
     */
    public static Set<String> all() { return KNOWN_METHODS; }
}
