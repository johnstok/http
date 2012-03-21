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
 * A HTTP content coding.
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="3.5")
public final class ContentEncoding {
    // TODO: x-gzip equiv to gzip
    // TODO: x-compress equiv to compress

    private static final String CONTENT_CODING = Syntax.TOKEN;
    public  static final String SYNTAX  = "(["+CONTENT_CODING+"])+";


    /**
     * GZIP : String.
     *
     * An encoding format produced by the file compression program "gzip" (GNU
     * zip) as described in RFC 1952 [25]. This format is a Lempel-Ziv coding
     * (LZ77) with a 32 bit CRC.
     */
    public static final ContentEncoding GZIP =
        new ContentEncoding("gzip");                               //$NON-NLS-1$


    /**
     * COMPRESS : String.
     *
     * The encoding format produced by the common UNIX file compression program
     * "compress". This format is an adaptive Lempel-Ziv-Welch coding (LZW).
     */
    public static final ContentEncoding COMPRESS =
        new ContentEncoding("compress");                           //$NON-NLS-1$


    /**
     * DEFLATE : String.
     *
     * The "zlib" format defined in RFC 1950 [31] in combination with the
     * "deflate" compression mechanism described in RFC 1951 [29].
     */
    public static final ContentEncoding DEFLATE =
        new ContentEncoding("deflate");                            //$NON-NLS-1$


    /**
     * IDENTITY : String.
     *
     * The default (identity) encoding; the use of no transformation whatsoever.
     * This content-coding is used only in the Accept-Encoding header, and
     * SHOULD NOT be used in the Content-Encoding header.
     */
    public static final ContentEncoding IDENTITY =
        new ContentEncoding("identity");                           //$NON-NLS-1$


    /** ANY : String. */
    public static final ContentEncoding ANY =
        new ContentEncoding("*");                                  //$NON-NLS-1$


    private final String _name;


    private ContentEncoding(final String name) { _name=name; }


    /**
     * Parse a string into a content coding.
     *
     * @param contentCodingString A string representing the content coding.
     *
     * @return A corresponding content coding object.
     */
    public static ContentEncoding parse(final String contentCodingString) {
        final Matcher m = Pattern.compile(SYNTAX).matcher(contentCodingString);
        if (m.matches()) {
            return new ContentEncoding(contentCodingString);
        }
        throw new ClientHttpException(Status.BAD_REQUEST);
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
        final ContentEncoding other = (ContentEncoding) obj;
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
