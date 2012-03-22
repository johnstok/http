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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Encapsulates a media type.
 *
 * The type, sub-type, and parameter attribute names are case-insensitive.
 *
 * HTTP uses Internet Media Types [17] in the Content-Type (section 14.17) and
 * Accept (section 14.1) header fields in order to provide open and extensible
 * data typing and type negotiation.
 * <pre>
 *     media-type     = type "/" subtype *( ";" parameter )
 *     type           = token
 *     subtype        = token
 * </pre>
 * Parameters MAY follow the type/subtype in the form of attribute/value pairs
 * (as defined in section 3.6).
 *
 * The type, subtype, and parameter attribute names are case-insensitive.
 * Parameter values might or might not be case-sensitive, depending on the
 * semantics of the parameter name. Linear white space (LWS) MUST NOT be used
 * between the type and subtype, nor between an attribute and its value. The
 * presence or absence of a parameter might be significant to the processing of
 * a media-type, depending on its definition within the media type registry.
 *
 * Note that some older HTTP applications do not recognize media type
 * parameters. When sending data to older HTTP applications, implementations
 * SHOULD only use media type parameters when they are required by that
 * type/subtype definition.
 *
 * Media-type values are registered with the Internet Assigned Number Authority
 * (IANA [19]). The media type registration process is outlined in RFC 1590
 * [17]. Use of non-registered media types is discouraged.
 *
 * @author Keith Webster Johnston.
 */
@Specifications({
    @Specification(name="rfc-2616", section="3.7"),
    @Specification(name="rfc-1590")
})
public class MediaType {

    public  static final String TYPE    = Syntax.TOKEN;
    public  static final String SUBTYPE = Syntax.TOKEN;
    public  static final String SYNTAX  = "(["+TYPE+"]+)/(["+SUBTYPE+"]+)";

    // TODO: Parameters.

    private final String _type;
    private final String _subtype;


    /**
     * Constructor.
     *
     * @param type    The primary type of the media type.
     * @param subtype The sub-type of the media type.
     */
    public MediaType(final String type, final String subtype) {
        _type = Contract.require().matches("["+Syntax.TOKEN+"]+", type);
        _subtype = Contract.require().matches("["+Syntax.TOKEN+"]+", subtype);
    }


    /**
     * Parse a string into a media type.
     *
     * @param mediaTypeString A string representing the media type.
     *
     * @return A corresponding media type object.
     */
    public static MediaType parse(final String mediaTypeString) {
        final Matcher m = Pattern.compile(SYNTAX).matcher(mediaTypeString);
        if (m.matches()) {
            return new MediaType(m.group(1), m.group(2));
        }
        throw new ClientHttpException(Status.BAD_REQUEST);
    }


    /**
     * Accessor.
     *
     * @return Returns the type.
     */
    public String getType() {
        return _type;
    }


    /**
     * Accessor.
     *
     * @return Returns the subtype.
     */
    public String getSubtype() {
        return _subtype;
    }


    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
            prime * result + ((_subtype == null) ? 0 : _subtype.hashCode());
        result = prime * result + ((_type == null) ? 0 : _type.hashCode());
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
        final MediaType other = (MediaType) obj;
        if (_subtype == null) {
            if (other._subtype != null) {
                return false;
            }
        } else if (!_subtype.equalsIgnoreCase(other._subtype)) {
            return false;
        }
        if (_type == null) {
            if (other._type != null) {
                return false;
            }
        } else if (!_type.equalsIgnoreCase(other._type)) {
            return false;
        }
        return true;
    }


    /**
     * Test whether the specified media type matches this one.
     *
     * @param mediaType The media type to test.
     *
     * @return True if the provided media type matches; false otherwise.
     */
    public boolean matches(final String mediaType) {
        return matches(MediaType.parse(mediaType));
    }


    /**
     * Test whether the specified media type matches this one.
     *
     * @param mediaType The media type to test.
     *
     * @return True if the provided media type matches; false otherwise.
     */
    public boolean matches(final MediaType mediaType) {
        if (_type.equalsIgnoreCase(mediaType._type) && _subtype.equalsIgnoreCase(mediaType._subtype)) {
            return true;
        } else if (_type.equalsIgnoreCase(mediaType._type) && "*".equals(mediaType._subtype)) {
            return true;
        } else if ("*".equals(mediaType._type) && "*".equals(mediaType._subtype)) {
            return true;
        }
        return false;
    }


    /** {@inheritDoc} */
    @Override
    public String toString() {
        return _type+"/"+_subtype;
    }


    public static final MediaType ANY = new MediaType("*", "*"); // TODO: Should wildcards be allowed?
    public static final MediaType HTML = new MediaType("text", "html");
    public static final MediaType XML = new MediaType("application", "xml");
    public static final MediaType JPEG = new MediaType("image", "jpeg");
    public static final MediaType JSON = new MediaType("application", "json");


    /**
     * TODO: Add a description for this method.
     *
     * @param match
     * @return
     */
    public boolean precedes(final MediaType mediaType) {
        if (null==mediaType) {
            return true;
        } else if (ANY.equals(mediaType) && !"*".equals(_type)) {
            return true;
        } else if (_type.equalsIgnoreCase(mediaType._type)
                   && "*".equals(mediaType._subtype)
                   && !"*".equals(_subtype)) {
            return true;
        }
        return false;
    }
}
