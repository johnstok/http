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
import com.johnstok.http.TransferCoding;


/**
 * A HTTP message.
 *
 * <pre>
   Message Types

   HTTP messages consist of requests from client to server and responses
   from server to client.

       HTTP-message   = Request | Response     ; HTTP/1.1 messages

   Request (section 5) and Response (section 6) messages use the generic
   message format of RFC 822 [9] for transferring entities (the payload
   of the message). Both types of message consist of a start-line, zero
   or more header fields (also known as "headers"), an empty line (i.e.,
   a line with nothing preceding the CRLF) indicating the end of the
   header fields, and possibly a message-body.

        generic-message = start-line
 *                          *(message-header CRLF)
                          CRLF
                          [ message-body ]
        start-line      = Request-Line | Status-Line

   In the interest of robustness, servers SHOULD ignore any empty
   line(s) received where a Request-Line is expected. In other words, if
   the server is reading the protocol stream at the beginning of a
   message and receives a CRLF first, it should ignore the CRLF.

   Certain buggy HTTP/1.0 client implementations generate extra CRLF's
   after a POST request. To restate what is explicitly forbidden by the
   BNF, an HTTP/1.1 client MUST NOT preface or follow a request with an
   extra CRLF.
   </pre>
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="4")
class Message {

    private final String       _startLine;
    private final List<String> _headers;
    private final InputStream  _is;


    /**
     * Constructor.
     *
     * @param is The input stream representing the message.
     *
     * @throws IOException If parsing of the message header fails.
     */
    public Message(final InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int prev = is.read();
        int curr = is.read();

        // FIXME: Ignore leading CRLF pairs - see RFC-2616§4.1.
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
            // FIXME: Fails to handle headers with LWS.
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
     * <pre>
   The message-body (if any) of an HTTP message is used to carry the
   entity-body associated with the request or response. The message-body
   differs from the entity-body only when a transfer-coding has been
   applied, as indicated by the Transfer-Encoding header field (section
   14.41).

       message-body = entity-body
                    | <entity-body encoded as per Transfer-Encoding>

   Transfer-Encoding MUST be used to indicate any transfer-codings
   applied by an application to ensure safe and proper transfer of the
   message. Transfer-Encoding is a property of the message, not of the
   entity, and thus MAY be added or removed by any application along the
   request/response chain. (However, section 3.6 places restrictions on
   when certain transfer-codings may be used.)

   The rules for when a message-body is allowed in a message differ for
   requests and responses.

   The presence of a message-body in a request is signaled by the
   inclusion of a Content-Length or Transfer-Encoding header field in
   the request's message-headers. A message-body MUST NOT be included in
   a request if the specification of the request method (section 5.1.1)
   does not allow sending an entity-body in requests. A server SHOULD
   read and forward a message-body on any request; if the request method
   does not include defined semantics for an entity-body, then the
   message-body SHOULD be ignored when handling the request.

   For response messages, whether or not a message-body is included with
   a message is dependent on both the request method and the response
   status code (section 6.1.1). All responses to the HEAD request method
   MUST NOT include a message-body, even though the presence of entity-
   header fields might lead one to believe they do. All 1xx
   (informational), 204 (no content), and 304 (not modified) responses
   MUST NOT include a message-body. All other responses do include a
   message-body, although it MAY be of zero length.
     * </pre>
     *
     * @return An input stream to consume the message body.
     */
    @Specification(name="rfc-2616", section="4.3")
    public InputStream getMessageBody() {
        return _is;
    }


    /**
     * Get the entity body.
     *
     * @param codings Array of transfer codings used to decode the message body.
     *                If multiple encodings have been applied to an entity, the
     *                transfer-codings MUST be listed in the order in which they
     *                were applied.
     *
     * @return An input stream to consume the message body.
     */
    public InputStream getEntityBody(final TransferCoding... codings) {
        throw new UnsupportedOperationException("Not Implemented.");
    }
}
