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

import java.nio.charset.Charset;
import java.util.ArrayList;


/**
 * Represents a URI path.
 *
 * @author Keith Webster Johnston.
 */
public class Path {

    private final ArrayList<String> _segments = new ArrayList<String>();


    /**
     * Constructor.
     *
     * @param rawPath The encoded, non-normalised path, as a string.
     * @param encodingCharset The character set to use for decoding.
     */
    public Path(final String rawPath, final Charset encodingCharset) {
        if (null==rawPath) { return; }

        final String[] segments = rawPath.split("/");
        for (final String s : segments) {
            final String segment = UrlCodec.decode(s, encodingCharset);
            if ((segment.length()>0) && !".".equals(segment)) {
                if ("..".equals(segment) && (_segments.size()>0)) {
                    final int lastIndex = _segments.size()-1;
                    if (!"..".equals(_segments.get(lastIndex))) {
                        _segments.remove(_segments.size()-1);
                    } else {
                        _segments.add(segment);
                    }
                } else {
                    _segments.add(segment);
                }
            }
        }
    }


    /**
     * Get the number of segments in the path.
     *
     * @return The segment count, as an integer.
     */
    public int getSize() { return _segments.size(); }


    /**
     * Get the segment at the specified index.
     *
     * Segments are indexed from 0; 0 being the left-most segment in the URI
     * string.
     *
     * @param index The index of the required segment.
     *
     * @return The segment value, as a string.
     */
    public String getSegment(final int index) {
        return _segments.get(index);
    }


    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "/"+String.join("/", _segments);
    }
}
