/*-----------------------------------------------------------------------------
 * Copyright © 2012 Keith Webster Johnston.
 * All rights reserved.
 *
 * This file is part of http.
 *
 * http is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * http is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with http.  If not, see <http://www.gnu.org/licenses/>.
 *---------------------------------------------------------------------------*/
package com.johnstok.http;

import static org.junit.Assert.*;
import org.junit.Test;


/**
 * Tests for the {@link StatusLine} class.
 *
 * @author Keith Webster Johnston.
 */
public class StatusLineTest {

    @Test
    public void validStatusLineIsParsed() {

        // ARRANGE

        // ACT
        final StatusLine sl = StatusLine.parse("HTTP/1.1 200 OK");

        // ASSERT
        assertEquals("HTTP/1.1", sl.getVersion());
        assertEquals("200", sl.getCode());
        assertEquals("OK", sl.getReasonPhrase());
    }

    @Test
    public void validStatusLineWithSpaceInReasonPhraseIsParsed() {

        // ARRANGE

        // ACT
        final StatusLine sl = StatusLine.parse("HTTP/1.1 101 Switching Protocols");

        // ASSERT
        assertEquals("HTTP/1.1", sl.getVersion());
        assertEquals("101", sl.getCode());
        assertEquals("Switching Protocols", sl.getReasonPhrase());
    }

    @Test
    public void reasonPhraseWithLineFeedIsInvalid() {

        // ARRANGE

        // ACT
        try {
            StatusLine.parse("HTTP/1.1 101 Switching\nProtocols");

        // ASSERT
        } catch (final ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }

    @Test
    public void reasonPhraseWithCarriageReturnIsInvalid() {

        // ARRANGE

        // ACT
        try {
            StatusLine.parse("HTTP/1.1 101 Switching\rProtocols");

            // ASSERT
        } catch (final ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }

    @Test
    public void validStatusLineWithExtensionCode() {

        // ARRANGE

        // ACT
        final StatusLine sl = StatusLine.parse("HTTP/1.1 999 Emergency");

        // ASSERT
        assertEquals("HTTP/1.1", sl.getVersion());
        assertEquals("999", sl.getCode());
        assertEquals("Emergency", sl.getReasonPhrase());
    }

    @Test
    public void invalidStatusLineIsRejected() {

        // ARRANGE

        // ACT
        try {
            StatusLine.parse("abc 123");

        // ASSERT
        } catch (final ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }
}
