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
 * Tests for the {@link RangeUnit} class.
 *
 * @author Keith Webster Johnston.
 */
public class RangeUnitTest {

    @Test
    public void toStringReturnsStringRepresentation() {

        // ARRANGE
        final RangeUnit ru = RangeUnit.parse("foo");

        // ACT
        final String unitString = ru.toString();

        // ASSERT
        assertEquals("foo", unitString);
    }

    @Test
    public void unitsWithDifferentStringAreNotEqual() {

        // ARRANGE

        // ACT

        // ASSERT
        assertFalse(new RangeUnit("bytes").equals(RangeUnit.parse("foo")));
    }

    @Test
    public void unitsWithSameStringAreEqual() {

        // ARRANGE

        // ACT

        // ASSERT
        assertEquals(
            RangeUnit.parse("bytes"),
            new RangeUnit("bytes"));
    }

    @Test
    public void parseBytesUnit() {

        // ARRANGE

        // ACT
        final RangeUnit ru = RangeUnit.parse("bytes");

        // ASSERT
        assertEquals("bytes", ru.getUnit());
    }

    @Test
    public void parseOtherUnit() {

        // ARRANGE

        // ACT
        final RangeUnit ru = RangeUnit.parse("foo");

        // ASSERT
        assertEquals("foo", ru.getUnit());
    }

    @Test
    public void parseZlsUnit() {

        // ARRANGE

        // ACT
        final RangeUnit ru = RangeUnit.parse("");

        // ASSERT
        assertEquals("", ru.getUnit());
    }
}
