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
 * A HTTP request's URI.
 * 
 * <pre>
   The Request-URI is a Uniform Resource Identifier (section 3.2) and
   identifies the resource upon which to apply the request.

       Request-URI    = "*" | absoluteURI | abs_path | authority

   The four options for Request-URI are dependent on the nature of the
   request. The asterisk "*" means that the request does not apply to a
   particular resource, but to the server itself, and is only allowed
   when the method used does not necessarily apply to a resource. One
   example would be

       OPTIONS * HTTP/1.1

   The absoluteURI form is REQUIRED when the request is being made to a
   proxy. The proxy is requested to forward the request or service it
   from a valid cache, and return the response. Note that the proxy MAY
   forward the request on to another proxy or directly to the server
   specified by the absoluteURI. In order to avoid request loops, a
   proxy MUST be able to recognize all of its server names, including
   any aliases, local variations, and the numeric IP address. An example
   Request-Line would be:

       GET http://www.w3.org/pub/WWW/TheProject.html HTTP/1.1

   To allow for transition to absoluteURIs in all requests in future
   versions of HTTP, all HTTP/1.1 servers MUST accept the absoluteURI
   form in requests, even though HTTP/1.1 clients will only generate
   them in requests to proxies.

   The authority form is only used by the CONNECT method (section 9.9).

   The most common form of Request-URI is that used to identify a
   resource on an origin server or gateway. In this case the absolute
   path of the URI MUST be transmitted (see section 3.2.1, abs_path) as
   the Request-URI, and the network location of the URI (authority) MUST
   be transmitted in a Host header field. For example, a client wishing
   to retrieve the resource above directly from the origin server would
   create a TCP connection to port 80 of the host "www.w3.org" and send
   the lines:

       GET /pub/WWW/TheProject.html HTTP/1.1
       Host: www.w3.org

   followed by the remainder of the Request. Note that the absolute path
   cannot be empty; if none is present in the original URI, it MUST be
   given as "/" (the server root).

   The Request-URI is transmitted in the format specified in section
   3.2.1. If the Request-URI is encoded using the "% HEX HEX" encoding
   [42], the origin server MUST decode the Request-URI in order to
   properly interpret the request. Servers SHOULD respond to invalid
   Request-URIs with an appropriate status code.

   A transparent proxy MUST NOT rewrite the "abs_path" part of the
   received Request-URI when forwarding it to the next inbound server,
   except as noted above to replace a null abs_path with "/".

      Note: The "no rewrite" rule prevents the proxy from changing the
      meaning of the request when the origin server is improperly using
      a non-reserved URI character for a reserved purpose.  Implementors
      should be aware that some pre-HTTP/1.1 proxies have been known to
      rewrite the Request-URI.
 * </pre>
 * 
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="5.1.2")
public class RequestURI {

    public static final String SYNTAX = Syntax.CHAR+"&&[^"+Syntax.CTL+"]"; // FIXME.

    private final String _uri;


    public RequestURI(String uri) {
        _uri = uri; // FIXME: Cannot be NULL or empty.
    }


    public static enum Type { NO_RESOURCE, ABSOLUTE_URI, ABS_PATH, AUTHORITY }


    /**
     * Parse a string into a request URI.
     *
     * @param requestUriString A string representing the request URI.
     *
     * @return A corresponding request URI object.
     */
    public static RequestURI parse(final String requestUriString) {
        final Matcher m = Pattern.compile("(["+SYNTAX+"]+)").matcher(requestUriString);
        if (m.matches()) {
            // FIXME: we need to determine which of the 4 types the URI is.
            // Only ABSOLUTE_URI & ABS_PATH are valid java.net.URIs.
            return new RequestURI(m.group(1));
        }
        throw new ClientHttpException(Status.BAD_REQUEST);
    }
}
