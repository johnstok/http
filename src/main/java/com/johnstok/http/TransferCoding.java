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
package com.johnstok.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A HTTP transfer coding.
 *
 * Transfer-coding values are used to indicate an encoding transformation that
 * has been, can be, or may need to be applied to an entity-body in order to
 * ensure "safe transport" through the network. This differs from a content
 * coding in that the transfer-coding is a property of the message, not of the
 * original entity.
 * <pre>
 *     transfer-coding         = "chunked" | transfer-extension
 *     transfer-extension      = token *( ";" parameter )
 * </pre>
 * Parameters are in the form of attribute/value pairs.
 * <pre>
 *     parameter               = attribute "=" value
 *     attribute               = token
 *     value                   = token | quoted-string
 * </pre>
 * All transfer-coding values are case-insensitive. HTTP/1.1 uses
 * transfer-coding values in the TE header field (section 14.39) and in the
 * Transfer-Encoding header field (section 14.41).
 *
 * Whenever a transfer-coding is applied to a message-body, the set of
 * transfer-codings MUST include "chunked", unless the message is terminated by
 * closing the connection. When the "chunked" transfer-coding is used, it MUST
 * be the last transfer-coding applied to the message-body. The "chunked"
 * transfer-coding MUST NOT be applied more than once to a message-body. These
 * rules allow the recipient to determine the transfer-length of the message
 * (section 4.4).
 *
 * Transfer-codings are analogous to the Content-Transfer-Encoding values of
 * MIME [7], which were designed to enable safe transport of binary data over a
 * 7-bit transport service. However, safe transport has a different focus for an
 * 8bit-clean transfer protocol. In HTTP, the only unsafe characteristic of
 * message-bodies is the difficulty in determining the exact body length
 * (section 7.2.2), or the desire to encrypt data over a shared transport.
 *
 * The Internet Assigned Numbers Authority (IANA) acts as a registry for
 * transfer-coding value tokens. Initially, the registry contains the following
 * tokens: "chunked" (section 3.6.1), "identity" (section 3.6.2), "gzip"
 * (section 3.5), "compress" (section 3.5), and "deflate" (section 3.5).
 *
 * New transfer-coding value tokens SHOULD be registered in the same way as new
 * content-coding value tokens (section 3.5).
 *
 * A server which receives an entity-body with a transfer-coding it does not
 * understand SHOULD return 501 (Unimplemented), and close the connection. A
 * server MUST NOT send transfer-codings to an HTTP/1.0 client.
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="3.6")
public final class TransferCoding {

    private static final String TRANSFER_EXTENSION =
        "(["+Syntax.TOKEN+"]+)((?:;"+Parameter.PARAMETER+")*)";
    public static final String SYNTAX  = "chunked|"+TRANSFER_EXTENSION;


    /**
     * CHUNKED : TransferCoding.
     */
    public static final TransferCoding CHUNKED =
        new TransferCoding("chunked");                             //$NON-NLS-1$


    private final String _name;
    private final Map<String, String> _parameters;


    /**
     * Constructor.
     *
     * @param name The name of the transfer coding.
     */
    private TransferCoding(final String name) {
        this(name, new HashMap<String, String>());
    }


    /**
     * Constructor.
     *
     * @param name       The name of the transfer coding.
     * @param parameters The transfer coding parameters.
     */
    private TransferCoding(final String name,
                           final Map<? extends String, ? extends String> parameters) {
        _name=name;
        _parameters =
            Collections.unmodifiableMap(new HashMap<String, String>(parameters));
    }


    /**
     * Parse a string into a transfer coding.
     *
     * @param transferCodingString A string representing the transfer coding.
     *
     * @return A corresponding transfer coding object.
     */
    public static TransferCoding parse(final String transferCodingString) {
        final Matcher m = Pattern.compile(SYNTAX).matcher(transferCodingString);
        if (m.matches()) {
            if ("chunked".equals(transferCodingString)) { return CHUNKED; }
            return new TransferCoding(m.group(1), Parameter.parse(m.group(2)));
        }
        throw new ClientHttpException(Status.BAD_REQUEST);
    }


    /**
     * Accessor.
     *
     * @return Returns the transfer coding's name.
     */
    public String getName() {
        return _name;
    }


    /**
     * Accessor.
     *
     * @param attribute Get the specified parameter.
     *
     * @return Returns the parameter value.
     */
    public String getParameter(final String attribute) {
        if (null==attribute) { return null; }
        return _parameters.get(attribute.toLowerCase(Locale.US));
    }


    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_name == null) ? 0 : _name.hashCode());
        return result;
    }


    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TransferCoding other = (TransferCoding) obj;
        if (_name == null) {
            if (other._name != null) {
                return false;
            }
        } else if (!_name.equalsIgnoreCase(other._name)) {
            return false;
        }
        return true;
    }


    /** {@inheritDoc} */
    @Override
    public String toString() { return _name; }
}
