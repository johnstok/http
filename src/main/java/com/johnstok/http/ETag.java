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

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * An entity tag - used for comparing multiple entities from the same resource.
 *
 * Entity tags are used for comparing two or more entities from the same
 * requested resource. HTTP/1.1 uses entity tags in the ETag (section 14.19),
 * If-Match (section 14.24), If-None-Match (section 14.26), and If-Range
 * (section 14.27) header fields. The definition of how they are used and
 * compared as cache validators is in section 13.3.3. An entity tag consists of
 * an opaque quoted string, possibly prefixed by a weakness indicator.
 * <pre>
 *    entity-tag = [ weak ] opaque-tag
 *    weak       = "W/"
 *    opaque-tag = quoted-string
 * </pre>
 *
 * A "strong entity tag" MAY be shared by two entities of a resource only if
 * they are equivalent by octet equality.
 *
 * A "weak entity tag," indicated by the "W/" prefix, MAY be shared by two
 * entities of a resource only if the entities are equivalent and could be
 * substituted for each other with no significant change in semantics. A weak
 * entity tag can only be used for weak comparison.
 *
 * An entity tag MUST be unique across all versions of all entities associated
 * with a particular resource. A given entity tag value MAY be used for entities
 * obtained by requests on different URIs. The use of the same entity tag value
 * in conjunction with entities obtained by requests on different URIs does not
 * imply the equivalence of those entities.
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="3.11")
public class ETag {

    private static final String WEAK       = "W/";
    private static final String OPAQUE_TAG = Syntax.QUOTED_STRING;
    public static final String  SYNTAX     = "("+WEAK+")?"+OPAQUE_TAG;


    private final String  _value;
    private final boolean _weak;


    /**
     * Constructor.
     *
     * @param value The string representation of the tag.
     * @param weak  Is the tag weak.
     */
    public ETag(final String value, final boolean weak) {
        // TODO: Validate value.
        _value = value;
        _weak = weak;
    }


    /**
     * Accessor.
     *
     * @return Returns the tag's string representation.
     */
    public String getValue() { return _value; }


    /**
     * Accessor.
     *
     * @return Returns true if the tag is weak; false otherwise.
     */
    public boolean isWeak() {
        return _weak;
    }


    /** {@inheritDoc} */
    @Override
    public String toString() {
        return (_weak?"W/":"")+"\""+_value+"\"";
    }

    /**
     * Parse a string into an entity tag.
     *
     * @param entityTagString A string representing the entity tag.
     *
     * @return A corresponding entity tag object.
     */
    public static ETag parse(final String entityTagString) {
        final Matcher m = Pattern.compile(SYNTAX).matcher(entityTagString);
        if (m.matches()) {
            return new ETag(m.group(3), null!=m.group(1));
        }
        throw new ClientHttpException(Status.BAD_REQUEST);
    }


    // TODO: Entity Tag equality - defined outside of 3.11.


    /**
     * Calculate the ETag for a file.
     *
     * @param file The file to analyse.
     *
     * @return The ETag, as a string.
     */
    public static String eTag(final File file) { // TODO: Return an ETag rather than a string.
        try {
            return
                    eTag(
                            file.length(),
                            file.lastModified(),
                            file.getCanonicalPath());
        } catch (final IOException e) {
            return null;
        }
    }

    /**
     * Calculate the ETag for a resource.
     *
     * @return The ETag, as a string.
     */
    public static String eTag(final long length,
                              final long lastModified,
                              final String path) { // TODO: Return an ETag rather than a string.
        try {
            final String uid =
                    length
                            +":"+lastModified                                  //$NON-NLS-1$
                            +":"+path;                                         //$NON-NLS-1$

            final MessageDigest m =
                    MessageDigest.getInstance("MD5");                  //$NON-NLS-1$
            final byte[] data = uid.getBytes();
            m.update(data, 0, data.length);
            final BigInteger i = new BigInteger(1, m.digest());
            return String.format("%1$032X", i);                    //$NON-NLS-1$

        } catch (final Exception e) {
            // FIXME: Log error?
            return null;
        }
    }
}
