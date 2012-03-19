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
public final class Method {

    private static final Set<String> KNOWN_METHODS =
        new HashSet<String>(Arrays.asList(
            Method.GET,
            Method.HEAD,
            Method.OPTIONS,
            Method.DELETE,
            Method.POST,
            Method.PUT
        ));

    /** GET : String. */
    public static final String GET  = "GET";                       //$NON-NLS-1$

    /** HEAD : String. */
    public static final String HEAD = "HEAD";                      //$NON-NLS-1$

    /** OPTIONS : String. */
    public static final String OPTIONS = "OPTIONS";                //$NON-NLS-1$

    /** DELETE : String. */
    public static final String DELETE = "DELETE";                  //$NON-NLS-1$

    /** PUT : boolean. */
    public static final String PUT = "PUT";                        //$NON-NLS-1$

    /** POST : String. */
    public static final String POST = "POST";                      //$NON-NLS-1$


    private Method() { super(); }


    /**
     * All known HTTP methods.
     *
     * @return The set of known HTTP methods.
     */
    public static Set<String> all() { return KNOWN_METHODS; }
}
