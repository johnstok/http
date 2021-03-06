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
import com.johnstok.http.Configuration;


/**
 * Responsibility: model the response to a HTTP request.
 *
 * TODO: Document state machine:
 *  - when can the status be set;
 *  - when can headers be set;
 *  - what happens if a method is called at wrong time? (Ans. IllStEx);
 *  - response is committed when getOutputStream is called.
 *
 *  NEW  ==getBody()==>  COMMITTED  ==body.close()==> COMPLETE
 *
 * TODO: Explicitly document the behaviour of a response once it is committed.
 * Responses will be automatically committed when the body output stream is
 * retrieved.
 *
 * TODO: The handler MAY flush and/or close body output stream. However,
 * the server will flush & close the body output stream (if necessary) when the
 * handler has returned successfully (i.e. no exception thrown). If a handler
 * wraps the provided OS it will need to ensure that all data is flushed prior
 * to return.
 *
 * TODO: How will the server behave if an error is returned? Describe both
 * committed and uncommitted scenarios.
 *
 * TODO: Document what is escaping is done? E.g. to prevent response splitting, etc.
 *
 * TODO:Document default vales for:
 *  - status line
 *  - headers
 *  - body
 *
 * TODO: Should we allow the HTTP version to be set?
 *
 * @author Keith Webster Johnston.
 */
public interface Response {

    /*
     * Redirect - by throwing exception?
     * Remove header?
     */


    /**
     * Mutator.
     *
     * @param statusCode   The HTTP status code for the response.
     *  <br>Allowed values are 0 (inclusive) to 1000 (exclusive).
     * @param reasonPhrase The HTTP reason phrase for the response.
     *  <br>This value will be URL encoded using the ISO-8859-1 character-set
     *  before transmission to the client.
     *  <br>TODO: Document how the length of this string interacts with
     *  {@link Configuration#getMaxInitialLineSize(int)}.
     */
    void setStatus(int statusCode, String reasonPhrase);


    /**
     * Accessor.
     *
     * @return The current status code of the response.
     */
    int getStatusCode();


    /**
     * Accessor.
     *
     * @return The current reason phrase of the response.
     */
    String getReasonPhrase();


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
     * TODO: What happens if this method is called more than once? Is the same object returned? IlStEx?
     *
     * @param value
     */
    OutputStream getBody() throws IOException;


    /**
     * Query if the status line and headers have been sent to the client.
     */
    boolean isCommitted();


    // TODO: void reset(); // Throws IlStEx if committed.
}
