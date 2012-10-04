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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * HTTP methods.
 *
 * From section 5.1.1:
 * <pre>
   The Method  token indicates the method to be performed on the
   resource identified by the Request-URI. The method is case-sensitive.

       Method         = "OPTIONS"                ; Section 9.2
                      | "GET"                    ; Section 9.3
                      | "HEAD"                   ; Section 9.4
                      | "POST"                   ; Section 9.5
                      | "PUT"                    ; Section 9.6
                      | "DELETE"                 ; Section 9.7
                      | "TRACE"                  ; Section 9.8
                      | "CONNECT"                ; Section 9.9
                      | extension-method
       extension-method = token

   The list of methods allowed by a resource can be specified in an
   Allow header field (section 14.7). The return code of the response
   always notifies the client whether a method is currently allowed on a
   resource, since the set of allowed methods can change dynamically. An
   origin server SHOULD return the status code 405 (Method Not Allowed)
   if the method is known by the origin server but not allowed for the
   requested resource, and 501 (Not Implemented) if the method is
   unrecognized or not implemented by the origin server. The methods GET
   and HEAD MUST be supported by all general-purpose servers. All other
   methods are OPTIONAL; however, if the above methods are implemented,
   they MUST be implemented with the same semantics as those specified
   in section 9.
 * </pre>
 *
 * @author Keith Webster Johnston.
 */
@Specifications({
    @Specification(name="rfc-2616", section="5.1.1"),
    @Specification(name="rfc-2616", section="9")
})
public final class Method {

    public static final String EXTENSION_METHOD = Syntax.TOKEN;
    public static final String SYNTAX =
        "OPTIONS|GET|HEAD|POST|PUT|DELETE|TRACE|CONNECT|["
        + EXTENSION_METHOD
        + "]+";

    private static final Set<Method> KNOWN_METHODS =
        new HashSet<Method>(Arrays.asList(
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
    public static final Method GET  = new Method("GET");           //$NON-NLS-1$

    /** HEAD : String. */
    @Specification(name="rfc-2616", section="9.4")
    public static final Method HEAD = new Method("HEAD");          //$NON-NLS-1$

    /** OPTIONS : String. */
    @Specification(name="rfc-2616", section="9.2")
    public static final Method OPTIONS = new Method("OPTIONS");    //$NON-NLS-1$

    /** DELETE : String. */
    @Specification(name="rfc-2616", section="9.7")
    public static final Method DELETE = new Method("DELETE");      //$NON-NLS-1$

    /** PUT : boolean. */
    @Specification(name="rfc-2616", section="9.6")
    public static final Method PUT = new Method("PUT");            //$NON-NLS-1$

    /** POST : String. */
    @Specification(name="rfc-2616", section="9.5")
    public static final Method POST = new Method("POST");          //$NON-NLS-1$

    /** TRACE : String. */
    @Specification(name="rfc-2616", section="9.8")
    public static final Method TRACE = new Method("TRACE");        //$NON-NLS-1$

    /** CONNECT : String. */
    @Specification(name="rfc-2616", section="9.9")
    public static final Method CONNECT = new Method("CONNECT");    //$NON-NLS-1$

    private final String _value;

    private Method(final String value) {
        _value = value; // FIXME: Verify not null or empty.
    }


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
     * Accessor.
     *
     * @return True if the method is recognized, false otherwise.
     */
    public boolean isRecognized() {
        return KNOWN_METHODS.contains(this);
    }


    /**
     * All known HTTP methods.
     *
     * @return The set of known HTTP methods.
     */
    public static Set<Method> all() { return KNOWN_METHODS; }


    /**
     * Parse a string into a method.
     *
     * @param methodString A string representing the method.
     *
     * @return A corresponding method object.
     */
    public static Method parse(final String methodString) {
        final Matcher m = Pattern.compile("("+SYNTAX+")").matcher(methodString); //FIXME: It's untidy to have to wrap the parentheses here - need to be consistent.
        if (m.matches()) {
            if ("GET".equals(m.group(1))) {
                return new Method("GET");
            } else if ("HEAD".equals(m.group(1))) {
                return new Method("HEAD");
            } else if ("OPTIONS".equals(m.group(1))) {
                return new Method("OPTIONS");
            } else if ("DELETE".equals(m.group(1))) {
                return new Method("DELETE");
            } else if ("PUT".equals(m.group(1))) {
                return new Method("PUT");
            } else if ("POST".equals(m.group(1))) {
                return new Method("POST");
            } else if ("TRACE".equals(m.group(1))) {
                return new Method("TRACE");
            } else if ("CONNECT".equals(m.group(1))) {
                return new Method("CONNECT");
            } else {
                return new Method(m.group(1));
            }
        }
        throw new ClientHttpException(Status.BAD_REQUEST);
    }


    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_value == null) ? 0 : _value.hashCode());
        return result;
    }


    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Method other = (Method) obj;
        if (_value == null) {
            if (other._value != null) {
                return false;
            }
        } else if (!_value.equals(other._value)) {
            return false;
        }
        return true;
    }
}
