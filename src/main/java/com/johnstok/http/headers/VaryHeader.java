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
 * along with http.  If not, see <http://www.gnu.org/licenses/>.
 *---------------------------------------------------------------------------*/
package com.johnstok.http.headers;

import java.util.SortedSet;
import java.util.TreeSet;
import com.johnstok.http.HeaderName;
import com.johnstok.http.Specification;
import com.johnstok.http.Specifications;


/**
 * Helper class for working with Vary headers.
 *
 * TODO: Add doc's from rfc-2616§14.44.
 *
 * @author Keith Webster Johnston.
 */
@Specifications({
    @Specification(name="rfc-2616", section="14.44")
})
public class VaryHeader extends HeaderName<SortedSet<String>> {

    /** {@inheritDoc} */
    @Override
    public SortedSet<String> parse(final String content) {
        String[] headerStrings = content.split(",");
        SortedSet<String> headers = new TreeSet<String>();
        for (String methodString : headerStrings) {
            headers.add(methodString.trim());
        }
        return headers;
    }

    /** {@inheritDoc} */
    @Override
    public String write(final SortedSet<String> methods) {
        return String.join(",", methods);
    }
}
