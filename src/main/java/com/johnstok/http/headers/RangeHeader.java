/*-----------------------------------------------------------------------------
 * Copyright © 2013 Keith Webster Johnston.
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
package com.johnstok.http.headers;

import java.util.List;
import com.johnstok.http.HeaderName;
import com.johnstok.http.Range;
import com.johnstok.http.Specification;


/**
 * Helper class for working with Range headers.
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="14.35")
public class RangeHeader extends HeaderName<List<Range>> {

    /** {@inheritDoc} */
    @Override
    public List<Range> parse(final String content) {
        return Range.parse(content); // TODO: In-line this method.
    }

    /** {@inheritDoc} */
    @Override
    public String write(final List<Range> ranges) {
        StringBuilder rangeString = new StringBuilder("bytes=");
        for (Range r : ranges) {
            if (!r.isValid()) { continue; } // TODO: Warn via log?
            if (null!=r.getFrom()) { rangeString.append(r.getFrom()); }
            rangeString.append('-');
            if (null!=r.getTo()) { rangeString.append(r.getTo()); }
            rangeString.append(',');
        }
        if (rangeString.length()>6) {
            rangeString.deleteCharAt(rangeString.length()-1);
        }
        return rangeString.toString();
    }
}
