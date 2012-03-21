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

import static org.junit.Assert.*;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import org.junit.Test;


/**
 * Tests for the {@link CharacterSet} class.
 *
 * @author Keith Webster Johnston.
 */
public class CharacterSetTest {

    private final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    @Test
    public void defaultIsIso_8859_1() {

        // ARRANGE

        // ACT

        // ASSERT
        assertEquals(ISO_8859_1, CharacterSet.DEFAULT);
    }

    @Test
    public void validSyntaxValidName() {

        // ARRANGE

        // ACT
        Charset iso_8859_1 = CharacterSet.parse("iso-8859-1");

        // ASSERT
        assertEquals(ISO_8859_1, iso_8859_1);
    }


    @Test
    public void invalidSyntaxValidName() {

        // ARRANGE

        // ACT
        try {
            CharacterSet.parse("ISO_8859-1:1987");

        // ASSERT
        } catch (ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }


    @Test
    public void validSyntaxInvalidName() {

        // ARRANGE

        // ACT
        try {
            CharacterSet.parse("foo-bar-baz-123");

        // ASSERT
        } catch (UnsupportedCharsetException e) {
            assertEquals("foo-bar-baz-123", e.getCharsetName());
        }
    }


    @Test
    public void invalidSyntaxInvalidName() {

        // ARRANGE

        // ACT
        try {
            CharacterSet.parse("foo:bar[baz}123");

        // ASSERT
        } catch (ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }

}
