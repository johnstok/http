/*-----------------------------------------------------------------------------
 * Copyright © 2012 Keith Webster Johnston.
 * All rights reserved.
 *
 * This file is part of http.
 *
 * http is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * http is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with http.  If not, see <http://www.gnu.org/licenses/>.
 *---------------------------------------------------------------------------*/
package com.johnstok.http;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * HTTP content negotiation (section 12) uses short "floating point"
 * numbers to indicate the relative importance ("weight") of various
 * negotiable parameters.  A weight is normalized to a real number in
 * the range 0 through 1, where 0 is the minimum and 1 the maximum
 * value. If a parameter has a quality value of 0, then content with
 * this parameter is `not acceptable' for the client. HTTP/1.1
 * applications MUST NOT generate more than three digits after the
 * decimal point. User configuration of these values SHOULD also be
 * limited in this fashion.
 * <pre>
 *     qvalue         = ( "0" [ "." 0*3DIGIT ] )
 *                    | ( "1" [ "." 0*3("0") ] )
 * </pre>
 * "Quality values" is a misnomer, since these values merely represent
 * relative degradation in desired quality.
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="3.9")
public class QualityValue
    implements
        Comparable<QualityValue> {

    public  static final String SYNTAX  =
        "0(\\.["+Syntax.DIGIT+"]{1,3})?|1(\\.[0]{1,3})?";


    private final BigDecimal _value;


    /**
     * Constructor.
     *
     * @param value The decimal representation of the quality value.
     */
    public QualityValue(final BigDecimal value) {
        // TODO: Test 0 <= value <= 1
        _value = value;
    }


    /**
     * Accessor.
     *
     * @return Returns the decimal representation of the quality value.
     */
    public final BigDecimal getValue() {
        return _value;
    }


    /**
     * Parse a string into a content coding.
     *
     * @param qualityValueString A string representing the content coding.
     *
     * @return A corresponding content coding object.
     */
    public static QualityValue parse(final String qualityValueString) {
        final Matcher m = Pattern.compile(SYNTAX).matcher(qualityValueString);
        if (m.matches()) {
            return new QualityValue(new BigDecimal(qualityValueString));
        }
        throw new ClientHttpException(Status.BAD_REQUEST);
    }


    /** {@inheritDoc} */
    @Override
    public String toString() {
        return _value.toString();
    }


    /** {@inheritDoc} */
    @Override
    public int compareTo(final QualityValue o) {
        return _value.compareTo(o._value);
    }


    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_value == null) ? 0 : _value.hashCode());
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
        QualityValue other = (QualityValue) obj;
        if (_value == null) {
            if (other._value != null) {
                return false;
            }
        } else if (!_value.equals(other._value)) {
            return false;
        }
        return true;
    }
}
