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

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * HTTP/1.1 allows a client to request that only part (a range of) the response
 * entity be included within the response. HTTP/1.1 uses range units in the
 * Range (section 14.35) and Content-Range (section 14.16) header fields. An
 * entity can be broken down into subranges according to various structural
 * units.
 * <pre>
 *    range-unit       = bytes-unit | other-range-unit
 *    bytes-unit       = "bytes"
 *    other-range-unit = token
 * </pre>
 * The only range unit defined by HTTP/1.1 is "bytes". HTTP/1.1 implementations
 * MAY ignore ranges specified using other units.
 *
 * HTTP/1.1 has been designed to allow implementations of applications that do
 * not depend on knowledge of ranges.
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="3.12")
public class RangeUnit {

    private static final String BYTES_UNIT       = "bytes";
    private static final String OTHER_RANGE_UNIT = Syntax.TOKEN;
    public static final String  SYNTAX           =
        "("+BYTES_UNIT+"|["+OTHER_RANGE_UNIT+"]*)";


    private final String _unit;


    /**
     * Constructor.
     *
     * @param unit The string representation of the range unit.
     */
    public RangeUnit(final String unit) {
        _unit = Objects.requireNonNull(unit);
    }


    /**
     * Accessor.
     *
     * @return Returns the string representation of the unit.
     */
    public String getUnit() {
        return _unit;
    }


    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((_unit == null) ? 0 : _unit.hashCode());
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
        final RangeUnit other = (RangeUnit) obj;
        if (_unit == null) {
            if (other._unit != null) {
                return false;
            }
        } else if (!_unit.equals(other._unit)) {
            return false;
        }
        return true;
    }


    /** {@inheritDoc} */
    @Override
    public String toString() {
        return _unit;
    }


    /**
     * Parse a string into a range unit.
     *
     * @param rangeUnitString A string representing the range unit.
     *
     * @return A corresponding range unit object.
     */
    public static RangeUnit parse(final String rangeUnitString) {
        final Matcher m = Pattern.compile(SYNTAX).matcher(rangeUnitString);
        if (m.matches()) {
            return new RangeUnit(m.group(1));
        }
        throw new ClientHttpException(Status.BAD_REQUEST);
    }
}
