/*-----------------------------------------------------------------------------
 * Copyright © 2012 Keith Webster Johnston.
 * All rights reserved.
 *
 * This file is part of http.
 *
 * http is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * http is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with http. If not, see <http://www.gnu.org/licenses/>.
 *---------------------------------------------------------------------------*/
package com.johnstok.http.sync;

import java.io.IOException;
import java.io.InputStream;
import com.johnstok.http.Specification;
import com.johnstok.http.StatusLine;


/**
 * A HTTP response.
 *
 * <pre>
   After receiving and interpreting a request message, a server responds
   with an HTTP response message.

       Response      = Status-Line               ; Section 6.1
 *                       *(( general-header        ; Section 4.5
                        | response-header        ; Section 6.2
                        | entity-header ) CRLF)  ; Section 7.1
                       CRLF
                       [ message-body ]          ; Section 7.2
 * </pre>
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="6")
public class Resp
    extends
        Message {

    /**
     * Constructor.
     *
     * @param is The input stream representing the message.
     *
     * @throws IOException If parsing of the message header fails.
     */
    public Resp(final InputStream is) throws IOException {
        super(is);
    }


    /**
     * Accessor.
     *
     * @return The status line.
     */
    public StatusLine getStatusLine() {
        return StatusLine.parse(getStartLine());
    }
}
