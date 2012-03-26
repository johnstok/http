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
 * Tests for the {@link TransferCoding} class.
 *
 * @author Keith Webster Johnston.
 */
public class TransferCodingTest {

    @Test
    public void parseChunked() {

        // ARRANGE

        // ACT
        final TransferCoding tc = TransferCoding.parse("chunked");

        // ASSERT
        assertEquals("chunked", tc.getName());
        assertEquals(TransferCoding.CHUNKED, tc);
    }

    @Test
    public void toStringReturnsName() {

        // ARRANGE

        // ACT

        // ASSERT
        assertEquals("gzip", TransferCoding.parse("gzip").toString());
    }


    @Test
    public void equalityIsCaseInsensitive() {

        // ARRANGE

        // ACT

        // ASSERT
        assertEquals(
            TransferCoding.parse("GZIP"),
            TransferCoding.parse("gzip"));
    }


    @Test
    public void parseZlsIsInvalid() {

        // ARRANGE

        // ACT
        try {
            TransferCoding.parse("");

        // ASSERT
        } catch (final ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }


    @Test
    public void parseNonTokenIsInvalid() {

        // ARRANGE

        // ACT
        try {
            TransferCoding.parse("abc:123");

            // ASSERT
        } catch (final ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }

    @Test
    public void parseSingleParamSpecified() {

        // ARRANGE

        // ACT
        final TransferCoding mt = TransferCoding.parse("gzip;a=1");

        // ASSERT
        assertEquals("gzip", mt.getName());
        assertEquals("1", mt.getParameter("a"));
        assertEquals("1", mt.getParameter("A"));
    }

    @Test
    public void parseMultipleParamSpecified() {

        // ARRANGE

        // ACT
        final TransferCoding mt = TransferCoding.parse("gzip;a=1;b=2;c=3");

        // ASSERT
        assertEquals("gzip", mt.getName());
        assertEquals("1", mt.getParameter("a"));
        assertEquals("2", mt.getParameter("b"));
        assertEquals("3", mt.getParameter("c"));
    }

    @Test
    public void parseParamTrailingSemicolonInvalid() {

        // ARRANGE

        // ACT
        try {
            TransferCoding.parse("gzip;a=1;");

            // ASSERT
        } catch (final ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }

    @Test
    public void parseParamNoValueInvalid() {

        // ARRANGE

        // ACT
        try {
            TransferCoding.parse("gzip;a=1;b=");

        // ASSERT
        } catch (final ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }

    @Test
    public void parseNoAttributeInvalid() {

        // ARRANGE

        // ACT
        try {
            TransferCoding.parse("gzip;a=1;=2");

            // ASSERT
        } catch (final ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }

    @Test
    public void parseParamTooManyPartsInvalid() {

        // ARRANGE

        // ACT
        try {
            TransferCoding.parse("gzip;a=1;b=c=d");

            // ASSERT
        } catch (final ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }
}
