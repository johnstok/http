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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.johnstok.http.Specification;


/**
 * A HTTP message.
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="4")
public class Message {

    private final String       _startLine;
    private final List<String> _headers;
    private final InputStream  _is;


    /**
     * Constructor.
     *
     * @param is The input stream representing the message.
     */
    public Message(final InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int prev = is.read();
        int curr = is.read();

        // Read start line
        while (!(13==prev && 10==curr)) {
            baos.write(prev);
            prev = curr;
            curr = is.read();
        }
        _startLine = new String(baos.toByteArray(), "iso-8859-1");

        // Read headers
        _headers = new ArrayList<String>();

        while (true) {
            baos = new ByteArrayOutputStream();
            prev = is.read();
            curr = is.read();
            while (!(13==prev && 10==curr)) {
                baos.write(prev);
                prev = curr;
                curr = is.read();
            }
            if (baos.size()>0) {
                _headers.add(new String(baos.toByteArray(), "iso-8859-1"));
            } else {
                break;
            }
        }

        _is = is;
    }


    /**
     * Get the message start line.
     *
     * @return The start line as a string.
     */
    public String getStartLine() {
        return _startLine;
    }


    /**
     * Get a message header.
     *
     * @param index The index of the header from the start of the message.
     *
     * @return The header as a string.
     */
    public String getHeader(final int index) {
        return _headers.get(index);
    }


    /**
     * Get the message body.
     *
     * @return An input stream to consume the message body.
     */
    public InputStream getBody() {
        return _is;
    }
}
