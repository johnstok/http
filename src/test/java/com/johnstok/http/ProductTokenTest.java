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
 * Tests for the {@link ProductToken} class.
 *
 * @author Keith Webster Johnston.
 */
public class ProductTokenTest {


    @Test
    public void equalObjectsAreEqual() {

        // ARRANGE

        // ACT

        // ASSERT
        assertEquals(
            ProductToken.parse("CERN-LineMode/2.15"),
            ProductToken.parse("CERN-LineMode/2.15"));
    }


    @Test
    public void parseValid() {

        // ARRANGE

        // ACT
        final ProductToken pt = ProductToken.parse("CERN-LineMode/2.15");

        // ASSERT
        assertEquals("CERN-LineMode", pt.getProduct());
        assertEquals("2.15",          pt.getVersion());
    }


    @Test
    public void parseValidNoVersion() {

        // ARRANGE

        // ACT
        final ProductToken pt = ProductToken.parse("Apache");

        // ASSERT
        assertEquals("Apache", pt.getProduct());
        assertNull(pt.getVersion());
    }


    @Test
    public void parseZlsInvalid() {

        // ARRANGE

        // ACT
        try {
            final ProductToken pt = ProductToken.parse("");

        // ASSERT
        } catch (final ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }


    @Test
    public void parseZlsVersionInvalid() {

        // ARRANGE

        // ACT
        try {
            final ProductToken pt = ProductToken.parse("libwww/");

            // ASSERT
        } catch (final ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }
}
