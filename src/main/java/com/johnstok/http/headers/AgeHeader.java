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

import com.johnstok.http.HeaderName;
import com.johnstok.http.Specification;
import com.johnstok.http.Specifications;
import com.johnstok.http.Time;

/**
 * Helper class for working with Age headers.
 *
 * @author Keith Webster Johnston.
 */
@Specifications({
    @Specification(name="rfc-2616", section="14.6"),
    @Specification(name="rfc-2616", section="13.2.3")
})
final class AgeHeader
    extends
        HeaderName<Time> {


    /** {@inheritDoc} */
    @Override
    public Time parse(final String content) {
        return Time.parse(content);
    }


    /** {@inheritDoc} */
    @Override
    public String write(final Time t) {
        return t.toString();
    }
}