/*-----------------------------------------------------------------------------
 * Copyright © 2012 Keith Webster Johnston.
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

import java.util.HashSet;
import java.util.Set;
import com.johnstok.http.HeaderName;
import com.johnstok.http.Method;
import com.johnstok.http.Specification;
import com.johnstok.http.Specifications;
import com.johnstok.http.engine.Utils;


/**
 * Helper class for working with Allow headers.
 *
 * TODO: Add doc's from rfc-2616§14.7.
 *
 * @author Keith Webster Johnston.
 */
@Specifications({
    @Specification(name="rfc-2616", section="5.1.1"),
    @Specification(name="rfc-2616", section="14.7")
})
public class AllowHeader extends HeaderName<Set<Method>> {

    /** {@inheritDoc} */
    @Override
    public Set<Method> parse(final String content) {
        String[] methodStrings = content.split(",");
        Set<Method> methods = new HashSet<Method>();
        for (String methodString : methodStrings) {
            methods.add(Method.parse(methodString.trim()));
        }
        return methods;
    }

    /** {@inheritDoc} */
    @Override
    public String write(final Set<Method> methods) {
        return Utils.join(methods, ',').toString();
    }

    // TODO: Implement logic in section 5.1.1.
}
