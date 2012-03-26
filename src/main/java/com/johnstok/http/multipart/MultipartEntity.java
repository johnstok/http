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
package com.johnstok.http.multipart;

import com.johnstok.http.Specification;


/**
 * General behaviour for all multipart formats.
 *
 * MIME provides for a number of "multipart" types -- encapsulations of one or
 * more entities within a single message-body. All multipart types share a
 * common syntax, as defined in section 5.1.1 of RFC 2046 [40], and MUST include
 * a boundary parameter as part of the media type value. The message body is
 * itself a protocol element and MUST therefore use only CRLF to represent line
 * breaks between body-parts. Unlike in RFC 2046, the epilogue of any multipart
 * message MUST be empty; HTTP applications MUST NOT transmit the epilogue (even
 * if the original multipart contains an epilogue). These restrictions exist in
 * order to preserve the self-delimiting nature of a multipart message-body,
 * wherein the "end" of the message-body is indicated by the ending multipart
 * boundary.
 *
 * In general, HTTP treats a multipart message-body no differently than any
 * other media type: strictly as payload. The one exception is the
 * "multipart/byteranges" type (appendix 19.2) when it appears in a 206 (Partial
 * Content) response, which will be interpreted by some HTTP caching mechanisms
 * as described in sections 13.5.4 and 14.16. In all other cases, an HTTP user
 * agent SHOULD follow the same or similar behavior as a MIME user agent would
 * upon receipt of a multipart type. The MIME header fields within each
 * body-part of a multipart message-body do not have any significance to HTTP
 * beyond that defined by their MIME semantics.
 *
 * In general, an HTTP user agent SHOULD follow the same or similar behavior as
 * a MIME user agent would upon receipt of a multipart type. If an application
 * receives an unrecognized multipart subtype, the application MUST treat it as
 * being equivalent to "multipart/mixed".
 *
 * <pre>
 *    Note: The "multipart/form-data" type has been specifically defined
 *    for carrying form data suitable for processing via the POST
 *    request method, as described in RFC 1867 [15].
 * </pre>
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="3.7.2")
abstract class MultipartEntity {

    protected static final String CRLF = "\r\n";                   //$NON-NLS-1$
}
