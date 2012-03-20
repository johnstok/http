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

import static org.junit.Assert.*;
import org.junit.Test;



/**
 * Tests for the {@link Version} class.
 *
 * @author Keith Webster Johnston.
 */
public class VersionTest {

    @Test
    public void accessorsCorrect() {

        // ARRANGE

        // ACT
        final Version v = new Version(1, 0);

        // ASSERT
        assertEquals(1, v.getMajor());
        assertEquals(0, v.getMinor());
    }


    @Test
    public void validToString() {

        // ARRANGE

        // ACT
        final Version v = new Version(1, 0);

        // ASSERT
        assertEquals("HTTP/1.0", v.toString());
    }


    @Test
    public void validParse() {

        // ARRANGE

        // ACT
        final Version v = Version.parse("HTTP/1.0");

        // ASSERT
        assertEquals(1, v.getMajor());
        assertEquals(0, v.getMinor());
    }


    @Test
    public void validParseWithLeadingZeroes() {

        // ARRANGE

        // ACT
        final Version v = Version.parse("HTTP/01.02");

        // ASSERT
        assertEquals(1, v.getMajor());
        assertEquals(2, v.getMinor());
    }


    @Test
    public void invalidParse() {

        // ARRANGE

        // ACT
        try {
            Version.parse("HTTP/1.a");

        // ASSERT
        } catch (final ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }
}
