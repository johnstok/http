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
import org.junit.Test;


/**
 * Tests for the {@link ContentEncoding} class.
 *
 * @author Keith Webster Johnston.
 */
public class ContentCodingTest {

    @Test
    public void toStringReturnsName() {

        // ARRANGE

        // ACT

        // ASSERT
        assertEquals("gzip", ContentEncoding.parse("gzip").toString());
    }


    @Test
    public void equalityIsCaseInsensitive() {

        // ARRANGE

        // ACT

        // ASSERT
        assertEquals(
            ContentEncoding.parse("GZIP"),
            ContentEncoding.parse("gzip"));
    }


    @Test
    public void parseZlsIsInvalid() {

        // ARRANGE

        // ACT
        try {
            ContentEncoding.parse("");

        // ASSERT
        } catch (ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }


    @Test
    public void parseNonTokenIsInvalid() {

        // ARRANGE

        // ACT
        try {
            ContentEncoding.parse("abc:123");

            // ASSERT
        } catch (ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }
}
