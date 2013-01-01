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
package com.johnstok.http.sync;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import com.johnstok.http.Method;
import com.johnstok.http.Path;
import com.johnstok.http.Scheme;
import com.johnstok.http.ServerHttpException;
import com.johnstok.http.Specification;
import com.johnstok.http.Version;


/**
 * A HTTP request.
 *
 * @author Keith Webster Johnston.
 */
public interface Request {


    /**
     * The URI requested by the client.
     *
     * No decoding or normalisation/canonicalisation is performed on this value.
     *
     * @return The request URI, as a Java URI.
     */
    URI getRequestUri();


    /**
     * The port via which this request was received.
     *
     * @return The port number, as an integer.
     */
    int getPort();


    /**
     * The hostname via which this request was received.
     *
     * Virtual hosting is currently unsupported. As such the 'Host' header
     * field and request URI 'domain' are ignored. Each of these values can be
     * accessed directly, if necessary.
     *
     * @return The host name, as a string.
     */
    @Specification(name="RFC-2616", section="5.2")
    String getDomain(); // TODO: Rename to getHost()? Return an InetSocketAddress?


    /**
     * Get the protocol used for this request.
     *
     * @return The protocol scheme.
     */
    Scheme getScheme();


    /**
     * Does this request use a confidential protocol.
     *
     * @return True if a confidential protocol is in use; false otherwise.
     */
    boolean isConfidential();


    /**
     * The HTTP method used by the client.
     *
     * @return
     */
    Method getMethod();


    /**
     * The HTTP version used by the client.
     *
     * @return A {@link Version} object.
     */
    Version getVersion();


    /**
     * The network address of the client.
     *
     * @return A network address.
     */
    InetSocketAddress getClientAddress();


    /**
     * Get the decoded, normalised path from the request URI.
     *
     * @param charset The character set used to decode the path.
     *
     * @return The request path.
     *
     * @throws ServerHttpException If the specified URI encoding is unsupported.
     */
    Path getPath(Charset charset);


    /**
     * Look up the value of an incoming request header.
     *
     * @param headerName The name of the required header value.
     *
     * @return Returns the header value as a string
     */
    String getHeader(String headerName);

    /**
     * Look up the value of an incoming request header.
     *
     * @param headerName The name of the required header value.
     * @param defaultValue The default value to return if no such header
     *  exists.
     *
     * @return Returns the header value as a string.
     */
    String getHeader(String headerName, String defaultValue);


    /**
     * Get all available header values.
     *
     * @return A map containing the header values for this request.
     */
    Map<String, List<String>> getHeaders();


    /**
     * The incoming request body.
     *
     * @return Returns an input stream to read the body data.
     *
     * @throws IOException If creation of the input stream fails.
     */
    InputStream getBody() throws IOException;


    /**
     * Given the name of a key, look up the corresponding value in the query
     * string.
     *
     * @param paramName The name of the required query parameter.
     *
     * @return Returns the decoded query value.
     */
    String getQueryValue(String paramName);


    /**
     * Given the name of a key and a default value if not present, look up the
     * corresponding value in the query string.
     *
     * @param paramName The name of the required query parameter.
     * @param defaultValue The default value to return if no such parameter
     *  exists.
     *
     * @return Returns the decoded query value.
     */
    String getQueryValue(String paramName, String defaultValue);


    /**
     * Get all available query values.
     *
     * @return A map containing all decoded query values.
     */
    Map<String, List<String>> getQueryValues();


    /**
     * Does this request have at least one header with the specified name.
     *
     * @param headerName The name of the required header.
     *
     * @return True if the request has such a header; false otherwise.
     */
    boolean hasHeader(String headerName);


    /**
     * Does this request have at least one query param with the specified name.
     *
     * @param paramName The name of the required query param.
     *
     * @return True if the request has such a param; false otherwise.
     */
    boolean hasQueryValue(String paramName);
}
