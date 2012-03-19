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
import java.util.List;
import org.junit.Test;



/**
 * Tests for the {@link Range} class.
 *
 * @author Keith Webster Johnston.
 */
public class RangeTest {


    /**
     * Test.
     */
    @Test
    public void byteRangeBeyondContentLengthIsUnsatisfiable() {

        // ARRANGE
        final Range r = new Range(2L, 2L);

        // ASSERT
        assertEquals(2L, r.getFrom());
        assertEquals(2L, r.getTo());
        assertTrue(r.isValid());
        assertFalse(r.isSatisfiable(1));
    }


    /**
     * Test.
     */
    @Test
    public void parseSingleRangeFinishOnly() {

        // ACT
        final List<Range> ranges = Range.parse("bytes=-2");

        // ASSERT
        assertEquals(1, ranges.size());
        assertEquals(null, ranges.get(0).getFrom());
        assertEquals(2L,  ranges.get(0).getTo());
    }


    /**
     * Test.
     */
    @Test
    public void parseSingleRangeStartAndFinish() {

        // ACT
        final List<Range> ranges = Range.parse("bytes=0-2");

        // ASSERT
        assertEquals(1, ranges.size());
        assertEquals(0L,   ranges.get(0).getFrom());
        assertEquals(2L, ranges.get(0).getTo());
    }


    /**
     * Test.
     */
    @Test
    public void parseSingleRangeStartOnly() {

        // ACT
        final List<Range> ranges = Range.parse("bytes=0-");

        // ASSERT
        assertEquals(1, ranges.size());
        assertEquals(0L,   ranges.get(0).getFrom());
        assertEquals(null, ranges.get(0).getTo());
    }
}
