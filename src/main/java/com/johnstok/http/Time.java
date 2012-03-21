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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A period of time, measured in seconds.
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="3.3.2")
public class Time {

    public static final String SYNTAX = "("+Syntax.DIGIT+")+";

    private final long _seconds;


    /**
     * Constructor.
     *
     * @param seconds The length of time in seconds.
     */
    public Time(final long seconds) {
        if (seconds<0) {
            throw new ClientHttpException(Status.BAD_REQUEST);
        }
        _seconds = seconds;
    }


    /**
     * Get the length of time in seconds.
     *
     * @return Version number as an integer.
     */
    public long getSeconds() {
        return _seconds;
    }


    /** {@inheritDoc} */
    @Override
    public String toString() {
        return String.valueOf(_seconds);
    }


    /**
     * Parse a string into a valid time.
     *
     * @param timeString A string representing the time.
     *
     * @return A corresponding time object.
     */
    public static Time parse(final String timeString) {
        final Matcher m = Pattern.compile(SYNTAX).matcher(timeString);
        if (m.matches()) {
            return
                new Time(Long.valueOf(timeString).longValue());
        }
        throw new ClientHttpException(Status.BAD_REQUEST);
    }
}
