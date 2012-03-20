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

import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Test;



/**
 * Tests for the {@link DateHeader} class.
 *
 * @author Keith Webster Johnston.
 */
public class DateHeaderTest {

    private static final String EPOCH_RFC_822 =
        "Thu, 01 Jan 1970 00:00:00 GMT";                           //$NON-NLS-1$
    private static final String EPOCH_RFC_850 =
        "Thursday, 01-Jan-70 00:00:00 GMT";                        //$NON-NLS-1$
    private static final String EPOCH_RFC_ANSI =
        "Thu Jan 01 00:00:00 1970";                                //$NON-NLS-1$
    private static final String EPOCH_RFC_ANSI_2SP =
        "Thu Jan  01 00:00:00 1970";                               //$NON-NLS-1$

    @Test
    public void validFormat() {

        // ARRANGE
        final Date d = new Date(0);

        // ACT
        final String dateString = DateHeader.format(d);

        // ASSERT
        assertEquals(EPOCH_RFC_822, dateString);
    }


    @Test
    public void parseRfc822() {

        // ARRANGE

        // ACT
        final Date d = DateHeader.parse(EPOCH_RFC_822);

        // ASSERT
        assertEquals(0l, d.getTime());
    }


    @Test
    public void parseRfc850() {

        // ARRANGE

        // ACT
        final Date d = DateHeader.parse(EPOCH_RFC_850);

        // ASSERT
        assertEquals(0l, d.getTime());
    }


    @Test
    public void parseAnsi() {

        // ARRANGE

        // ACT
        final Date d = DateHeader.parse(EPOCH_RFC_ANSI);

        // ASSERT
        assertEquals(0l, d.getTime());
    }


    @Test
    public void parseAnsiDoubleSpace() {

        // ARRANGE

        // ACT
        final Date d = DateHeader.parse(EPOCH_RFC_ANSI_2SP);

        // ASSERT
        assertEquals(0l, d.getTime());
    }
}
