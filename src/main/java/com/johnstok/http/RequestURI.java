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

import java.net.URI;
import java.net.URISyntaxException;



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
@Specifications({
    @Specification(name="rfc-2616", section="3.2"),
    @Specification(name="rfc-2616", section="5.1.2"),
    @Specification(name="rfc-3986"),
    @Specification(name="rfc-2396")
})
public class RequestURI {

    public static final String SYNTAX = // FIXME: This is a naïve regex.
        Syntax.CHAR+"&&[^"+Syntax.CTL+"]";           //$NON-NLS-1$ //$NON-NLS-2$

    private final String _rawUri;
    private final Type   _type;
    private final URI    _uri;


    public RequestURI(final String value, final Type type, final URI uri) {
        _rawUri = value; // FIXME: Cannot be NULL or empty.
        _type = type; // FIXME: Cannot be NULL or empty.
        _uri = uri;
    }


    /**
     * Accessor.
     *
     * @return Returns the type.
     */
    public final Type getType() {
        return _type;
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
        if ("*".equals(requestUriString)) {
            return new RequestURI(requestUriString, Type.NO_RESOURCE, null);
        } else if (Authority.isValid(requestUriString)) {
            try {
                return
                    new RequestURI(
                        requestUriString,
                        Type.AUTHORITY,
                        new URI("//"+requestUriString));
            } catch (URISyntaxException e) {
                throw new ClientHttpException(Status.BAD_REQUEST);
            }
        } else {
            try {
                URI uri = new URI(requestUriString);
                return new RequestURI(
                    requestUriString,
                    (uri.isAbsolute()) ? Type.ABSOLUTE_URI : Type.ABS_PATH,
                    uri);
            } catch (URISyntaxException e) {
                throw new ClientHttpException(Status.BAD_REQUEST);
            }
        }
    }


    /**
     * Compare two request URIs to see if they match.
     *
     * <pre>
   When comparing two URIs to decide if they match or not, a client
   SHOULD use a case-sensitive octet-by-octet comparison of the entire
   URIs, with these exceptions:

        - A port that is empty or not given is equivalent to the default
          port for that URI-reference;

        - Comparisons of host names MUST be case-insensitive;

        - Comparisons of scheme names MUST be case-insensitive;

        - An empty abs_path is equivalent to an abs_path of "/".

   Characters other than those in the "reserved" and "unsafe" sets (see
   RFC 2396 [42]) are equivalent to their ""%" HEX HEX" encoding.

   For example, the following three URIs are equivalent:

      http://abc.com:80/~smith/home.html
      http://ABC.com/%7Esmith/home.html
      http://ABC.com:/%7esmith/home.html
     * </pre>
     *
     * @return True if the URIs match, false otherwise.
     */
    @Specification(name="rfc-2616", section="3.2.3")
    public boolean matches(final RequestURI requestUri) {
        // TODO: Do we need to specify the charset here?
        if (this == requestUri) {
            return true;
        }
        if (null == requestUri) {
            return false;
        }
        if (getClass() != requestUri.getClass()) {
            return false;
        }

        URI thisUri = URI.create(_rawUri);
        URI thatUri = URI.create(requestUri._rawUri);

        // Compare schemes
        if (!caseInsensitiveMatch(thisUri.getScheme(), thatUri.getScheme())) {
            return false;
        }

        // Compare authorities
        if (isRegistryAuthority(thisUri) && isRegistryAuthority(thisUri)) {
            if (!match(thisUri.getAuthority(), thatUri.getAuthority())) {
                return false;
            }
        } else if (isRegistryAuthority(thisUri)) { // one server-based and one registry-based authority, so not equal.
            return false;
        } else {
            if (!caseInsensitiveMatch(thisUri.getHost(), thatUri.getHost())) {
                return false;
            }
            if (!match(thisUri.getUserInfo(), thatUri.getUserInfo())) {
                return false;
            }
            int thisPort = thisUri.getPort();
            int thatPort = thatUri.getPort();
            if (((-1==thisPort)?80:thisPort)!=((-1==thatPort)?80:thatPort)) {
                return false;
            }
        }

        // Compare paths
        String thisPath = thisUri.getPath();
        String thatPath = thatUri.getPath();
        if (!match(
            ("".equals(thisPath))?"/":thisPath,
                ("".equals(thatPath))?"/":thatPath)) {
            return false;
        }

        // Compare query strings
        if (!caseInsensitiveMatch(thisUri.getQuery(), thatUri.getQuery())) {
            return false;
        }

        return true;
    }


    private boolean isRegistryAuthority(final URI thisUri) {
        try {
            thisUri.parseServerAuthority();
        } catch (URISyntaxException e) {
            return true;
        }
        return false;
    }


    private boolean match(final String thisString, final String thatString) {
        if ((null==thisString) && (null==thatString)) {
            return true;
        }
        if (null==thisString) { // thatString != null so not equal.
            return false;
        }
        return thisString.equals(thatString);
    }


    private boolean caseInsensitiveMatch(final String thisString,
                                         final String thatString) {
        if ((null==thisString) && (null==thatString)) {
            return true;
        }
        if (null==thisString) { // thatString != null so not equal.
            return false;
        }
        return thisString.equalsIgnoreCase(thatString);
    }


    /**
     * Convert the request URI to a {@link URI} object.
     *
     * <p>Note that the URI class always encodes / decodes using the UTF-8
     * character set.</p>
     *
     * @return The URI representation of this request URI.
     */
    public URI toUri() {
        return _uri;
    }


    /** {@inheritDoc} */
    @Override
    public String toString() {
        return _rawUri;
    }
}
