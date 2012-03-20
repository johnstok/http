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
package com.johnstok.http.headers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import com.johnstok.http.ClientHttpException;
import com.johnstok.http.Status;


/**
 * Helper class for working with date headers.
 *
 * @author Keith Webster Johnston.
 */
public final class DateHeader {
    // TODO: Consider using regex to parse dates, as described in the spec.

    private static final SimpleDateFormat RFC_822;
    private static final SimpleDateFormat RFC_850;
    private static final SimpleDateFormat ANSI_C;


    static {
        RFC_822 =
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz"); //$NON-NLS-1$
        RFC_822.setTimeZone(TimeZone.getTimeZone("GMT"));          //$NON-NLS-1$
        RFC_850 =
            new SimpleDateFormat("EEE, dd-MMM-yy HH:mm:ss zzz");   //$NON-NLS-1$
        RFC_850.setTimeZone(TimeZone.getTimeZone("GMT"));          //$NON-NLS-1$
        ANSI_C =
            new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");      //$NON-NLS-1$
        ANSI_C.setTimeZone(TimeZone.getTimeZone("GMT"));           //$NON-NLS-1$
    }


    private DateHeader() { super(); }


    /**
     * Look up the current value of an outgoing request header.
     *
     * @param value The header value, as a string.
     *
     * @return The header as a date.
     */
    public static Date parse(final String value) {
        try {
            return RFC_822.parse(value);
        } catch (final ParseException e) {
            // Continue.
        }
        try {
            return RFC_850.parse(value);
        } catch (final ParseException e) {
            // Continue.
        }
        try {
            return ANSI_C.parse(value);
        } catch (final ParseException e) {
            throw new ClientHttpException(Status.BAD_REQUEST);
        }
    }


    /**
     * Confirm the value of the specified header is a HTTP date.
     *
     * @param value The header value, as a string.
     *
     * @return True if the date is valid; false otherwise.
     */
    public static boolean isValidDate(final String value) {
        try {
            RFC_822.parse(value);
            return true;
        } catch (final ParseException e) {
            // Continue.
        }
        try {
            RFC_850.parse(value);
            return true;
        } catch (final ParseException e) {
            // Continue.
        }
        try {
            ANSI_C.parse(value);
            return true;
        } catch (final ParseException e) {
            return false;
        }
    }


    /**
     * Format the specified date as an HTTP date string.
     *
     * @param date The date to format.
     *
     * @return The date formatted as an HTTP date string.
     */
    public static String format(final Date date) {
        return RFC_822.format(date);
    }
}
