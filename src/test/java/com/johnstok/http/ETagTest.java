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
 * Tests for the {@link ETag} class.
 *
 * @author Keith Webster Johnston.
 */
public class ETagTest {

    @Test
    public void toStringForWeakTag() {

        // ARRANGE
        final ETag t = new ETag("foo", true);

        // ACT

        // ASSERT
        assertEquals("W/\"foo\"", t.toString());
    }


    @Test
    public void toStringForStrongTag() {

        // ARRANGE
        final ETag t = new ETag("bar", false);

        // ACT

        // ASSERT
        assertEquals("\"bar\"", t.toString());
    }


    @Test
    public void accessorsCorrect() {

        // ARRANGE
        final ETag t = new ETag("bar", false);

        // ACT

        // ASSERT
        assertEquals("bar", t.getValue());
        assertFalse(t.isWeak());
    }


    @Test
    public void parseStrong() {

        // ARRANGE
        final ETag t = ETag.parse("\"foo\"");

        // ACT

        // ASSERT
        assertEquals("foo", t.getValue());
        assertFalse(t.isWeak());
    }


    @Test
    public void parseWeak() {

        // ARRANGE
        final ETag t = ETag.parse("W/\"bar\"");

        // ACT

        // ASSERT
        assertEquals("bar", t.getValue());
        assertTrue(t.isWeak());
    }


    @Test
    public void parseInvalid() {

        // ARRANGE

        // ACT
        try {
            ETag.parse("baz");

        // ASSERT
        } catch (final ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }
}
