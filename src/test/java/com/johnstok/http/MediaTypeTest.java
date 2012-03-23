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
 * Tests for the {@link MediaType} class.
 *
 * @author Keith Webster Johnston.
 */
public class MediaTypeTest {

    @Test
    public void constructAny() {

        // ARRANGE

        // ACT
        final MediaType mt = new MediaType("*", "*");

        // ASSERT
        assertEquals("*", mt.getType());
        assertEquals("*", mt.getSubtype());
    }

    @Test
    public void constructWildcardSubtype() {

        // ARRANGE

        // ACT
        final MediaType mt = new MediaType("text", "*");

        // ASSERT
        assertEquals("text", mt.getType());
        assertEquals("*", mt.getSubtype());
    }

    @Test
    public void constructFullySpecified() {

        // ARRANGE

        // ACT
        final MediaType mt = new MediaType("text", "html");

        // ASSERT
        assertEquals("text", mt.getType());
        assertEquals("html", mt.getSubtype());
    }

    @Test
    public void parseAny() {

        // ARRANGE

        // ACT
        final MediaType mt = MediaType.parse("*/*");

        // ASSERT
        assertEquals("*", mt.getType());
        assertEquals("*", mt.getSubtype());
    }

    @Test
    public void parseTextWildcardSubtype() {

        // ARRANGE

        // ACT
        final MediaType mt = MediaType.parse("text/*");

        // ASSERT
        assertEquals("text", mt.getType());
        assertEquals("*", mt.getSubtype());
    }

    @Test
    public void parseTextFullySpecified() {

        // ARRANGE

        // ACT
        final MediaType mt = MediaType.parse("text/html");

        // ASSERT
        assertEquals("text", mt.getType());
        assertEquals("html", mt.getSubtype());
    }

    @Test
    public void parseSingleParamSpecified() {

        // ARRANGE

        // ACT
        final MediaType mt = MediaType.parse("text/html;a=1");

        // ASSERT
        assertEquals("text", mt.getType());
        assertEquals("html", mt.getSubtype());
        assertEquals("1", mt.getParameter("a"));
        assertEquals("1", mt.getParameter("A"));
    }

    @Test
    public void parseMultipleParamSpecified() {

        // ARRANGE

        // ACT
        final MediaType mt = MediaType.parse("text/html;a=1;b=2;c=3");

        // ASSERT
        assertEquals("text", mt.getType());
        assertEquals("html", mt.getSubtype());
        assertEquals("1", mt.getParameter("a"));
        assertEquals("2", mt.getParameter("b"));
        assertEquals("3", mt.getParameter("c"));
    }

    @Test
    public void parseParamTrailingSemicolonInvalid() {

        // ARRANGE

        // ACT
        try {
            MediaType.parse("text/html;a=1;");

            // ASSERT
        } catch (ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }

    @Test
    public void parseParamNoValueInvalid() {

        // ARRANGE

        // ACT
        try {
            MediaType.parse("text/html;a=1;b=");

        // ASSERT
        } catch (ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }

    @Test
    public void parseNoAttributeInvalid() {

        // ARRANGE

        // ACT
        try {
            MediaType.parse("text/html;a=1;=2");

            // ASSERT
        } catch (ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }

    @Test
    public void parseParamTooManyPartsInvalid() {

        // ARRANGE

        // ACT
        try {
            MediaType.parse("text/html;a=1;b=c=d");

            // ASSERT
        } catch (ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }

    @Test
    public void sameAreEqual() {

        // ARRANGE
        final MediaType mt = new MediaType("text", "html");

        // ACT
        final boolean equal = mt.equals(mt);

        // ASSERT
        assertTrue(equal);
    }

    @Test
    public void varyingCaseAreEqual() {

        // ARRANGE
        final MediaType mt1 = new MediaType("text", "HTML");
        final MediaType mt2 = new MediaType("TEXT", "html");

        // ACT
        final boolean equal = mt1.equals(mt2);

        // ASSERT
        assertTrue(equal);
    }

    @Test
    public void equivalentAreEqual() {

        // ARRANGE
        final MediaType mt1 = new MediaType("text", "html");
        final MediaType mt2 = new MediaType("text", "html");

        // ACT
        final boolean equal = mt1.equals(mt2);

        // ASSERT
        assertTrue(equal);
    }

    @Test
    public void wildcardMatchNotEqual() {

        // ARRANGE
        final MediaType mt1 = new MediaType("text", "html");
        final MediaType mt2 = new MediaType("text", "*");

        // ACT
        final boolean equal = mt1.equals(mt2);

        // ASSERT
        assertFalse(equal);
    }

    @Test
    public void equivalentMatches() {

        // ARRANGE
        final MediaType mt = new MediaType("text", "html");

        // ACT
        final boolean match = mt.matches("text/html");

        // ASSERT
        assertTrue(match);
    }

    @Test
    public void wildcardSubtypeMatches() {

        // ARRANGE
        final MediaType mt = new MediaType("text", "html");

        // ACT
        final boolean match = mt.matches("text/*");

        // ASSERT
        assertTrue(match);
    }

    @Test
    public void anyMatches() {

        // ARRANGE
        final MediaType mt = new MediaType("text", "html");

        // ACT
        final boolean match = mt.matches("*/*");

        // ASSERT
        assertTrue(match);
    }

    @Test
    public void wildcardTypeDoesNotMatch() {

        // ARRANGE
        final MediaType mt = new MediaType("text", "html");

        // ACT
        final boolean match = mt.matches("*/html");

        // ASSERT
        assertFalse(match);
    }
}
