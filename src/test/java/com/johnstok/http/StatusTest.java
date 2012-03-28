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
 * Tests for the {@link Status} class.
 *
 * @author Keith Webster Johnston.
 */
public class StatusTest {


    @Test
    public void isInformational() {

        // ASSERT
        assertTrue(Status.SWITCHING_PROTOCOLS.isInformational());
    }


    @Test
    public void isSuccess() {

        // ASSERT
        assertTrue(Status.CREATED.isSuccess());
    }


    @Test
    public void isRedirect() {

        // ASSERT
        assertTrue(Status.TEMPORARY_REDIRECT.isRedirect());
    }


    @Test
    public void isClientError() {

        // ASSERT
        assertTrue(Status.NOT_FOUND.isClientError());
    }


    @Test
    public void isServerError() {

        // ASSERT
        assertTrue(Status.BAD_GATEWAY.isServerError());
    }
}
