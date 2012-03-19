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



/**
 * An entity tag - used for comparing multiple entities from the same resource.
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="3.11")
public class ETag {

    // FIXME: Added boolean `_weak` field.

    private final String _value;


    /**
     * Constructor.
     *
     * @param value
     */
    public ETag(final String value) { _value = value; }


    /**
     * Accessor.
     *
     * @return Returns the value.
     */
    public String getValue() { return _value; }
}
