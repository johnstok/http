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
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Date;
import com.johnstok.http.MediaType;
import com.johnstok.http.Status;


/**
 * Responsibility: model the response to a HTTP request.
 *
 * TODO: Explicitly document the behaviour of a response once it is committed.
 * Responses will be automatically committed immediately prior to writing the
 * response body.
 *
 * @author Keith Webster Johnston.
 */
public interface Response {

    /*
     * Set status message;
     * Redirect - by throwing exception?
     * Remove header?
     */

    /**
     * Mutator.
     *
     * @param status The new status to set.
     */
    void setStatus(Status status);


    /**
     * Accessor.
     *
     * @return The current status of the response.
     */
    Status getStatus();


    /**
     * TODO: Add a description for this method.
     *
     * @param value
     * @param name
     */
    void setHeader(String name, String value);


    /**
     * TODO: Add a description for this method.
     *
     * @param string
     */
    // TODO: This should return a list of values.
    String getHeader(final String name);


    /**
     * TODO: Add a description for this method.
     *
     * @param value
     */
    OutputStream getBody() throws IOException;

    /**
     * Query if the status line and headers have been sent to the client.
     */
    boolean isCommitted();


    void close() throws IOException;


    // TODO: Move everything below here elsewhere - it is logic on top of the HTTP message format.


    /**
     * TODO: Add a description for this method.
     *
     * @param name
     * @param value
     */
    void setHeader(String name, Date value);


    /**
     * The time at which the server originated the response message.
     *
     * @return
     */
    Date getOriginationTime();


    /**
     * TODO: Add a description for this method.
     *
     * @param charset
     */
    void setCharset(Charset charset);


    /**
     * TODO: Add a description for this method.
     *
     * @param mediaType
     */
    void setMediaType(MediaType mediaType);


    /**
     * TODO: Add a description for this method.
     *
     * @param encoding
     */
    void setContentEncoding(String encoding);


    /**
     * TODO: Add a description for this method.
     *
     * @return
     */
    MediaType getMediaType();


    /**
     * TODO: Add a description for this method.
     *
     * @return
     */
    String[] getVariances();


    /**
     * TODO: Add a description for this method.
     *
     * @param headerName
     */
    void addVariance(String headerName);
}
