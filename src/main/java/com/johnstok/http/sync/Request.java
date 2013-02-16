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
import java.util.List;
import java.util.Map;
import com.johnstok.http.Method;
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
     * @return The request URI, as a string.
     */
    String getRequestUri();


    /**
     * The server address at which this request was received.
     *
     * @return The IP address of the server.
     */
    InetSocketAddress getServerAddress();


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
     * Look up the value of an incoming request header.
     *
     * TODO: What if there are multiple headers with the same name?
     *
     * @param headerName The name of the required header value.
     *
     * @return Returns the header value as a string
     */
    String getHeader(String headerName);

    /**
     * Look up the value of an incoming request header.
     *
     * TODO: What if there are multiple headers with the same name?
     *
     * @param headerName The name of the required header value.
     * @param defaultValue The default value to return if no such header
     *  exists.
     *
     * @return Returns the header value as a string.
     */
    String getHeader(String headerName, String defaultValue); // TODO: Implement this in AbstractRequest, based on getHeaders().


    /**
     * Get all available header values.
     *
     * @return A map containing the header values for this request.
     */
    Map<String, List<String>> getHeaders();

    // TODO: boolean hasHeader(String headerName);
    // TODO: List<String> getHeaders(String headerName);


    /**
     * The incoming request body.
     *
     * @return Returns an input stream to read the body data.
     *
     * @throws IOException If creation of the input stream fails.
     */
    InputStream getBody() throws IOException;


    /**
     * Does this request have at least one header with the specified name.
     *
     * @param headerName The name of the required header.
     *
     * @return True if the request has such a header; false otherwise.
     */
    boolean hasHeader(String headerName);
}
