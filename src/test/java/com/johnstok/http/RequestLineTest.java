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
 * Tests for the {@link RequestLine} class.
 *
 * @author Keith Webster Johnston.
 */
public class RequestLineTest {

    @Test
    public void validRequestLineIsParsed() {

        // ARRANGE

        // ACT
        final RequestLine rl = RequestLine.parse("GET foo HTTP/1.1");

        // ASSERT
        assertEquals("GET", rl.getMethod());
        assertEquals("foo", rl.getUri());
        assertEquals("HTTP/1.1", rl.getVersion());
    }

    @Test
    public void validRequestLineWithExtensionMethod() {

        // ARRANGE

        // ACT
        final RequestLine rl = RequestLine.parse("FOO foo HTTP/1.1");

        // ASSERT
        assertEquals("FOO", rl.getMethod());
        assertEquals("foo", rl.getUri());
        assertEquals("HTTP/1.1", rl.getVersion());
    }

    @Test
    public void invalidRequestLineIsRejected() {

        // ARRANGE

        // ACT
        try {
            RequestLine.parse("abc 123");

        // ASSERT
        } catch (final ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }
}
