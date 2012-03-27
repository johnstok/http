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
package com.johnstok.http.sync;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Test;



/**
 * Tests for the {@link Message} class.
 *
 * @author Keith Webster Johnston.
 */
public class MessageTest {

    /**
     * Test.
     *
     * @throws IOException If the test fails.
     */
    @Test
    public void ex1() throws IOException {

        // ARRANGE
        final InputStream is = getClass().getResourceAsStream("/ex1.txt");

        // ACT
        final Message m = new Message(is);

        // ASSERT
        assertEquals("GET / HTTP/1.1", m.getStartLine());
        assertEquals("Content-Length: 0", m.getHeader(0));
    }


    /**
     * Test.
     *
     * @throws IOException If the test fails.
     */
    @Test
    public void ex2() throws IOException {

        // ARRANGE
        final InputStream is = getClass().getResourceAsStream("/ex2.txt");

        // ACT
        final Message m = new Message(is);

        // ASSERT
        assertEquals("GET /foo/bar HTTP/1.1", m.getStartLine());
        assertEquals("Connection: keep-alive", m.getHeader(5));
    }


    /**
     * Test.
     *
     * @throws IOException If the test fails.
     */
    @Test
    public void ex3() throws IOException {

        // ARRANGE
        final InputStream is = getClass().getResourceAsStream("/ex3.txt");

        // ACT
        final Message m = new Message(is);

        // ASSERT
        assertEquals("GET /foo/bar HTTP/1.1", m.getStartLine());
        assertEquals("Host: www.w3.org", m.getHeader(6));
        assertEquals(
            "The quick brown fox jumped over the lazy dog.",
            consumeAsString(m.getBody(), "iso-8859-1"));
    }


    private String consumeAsString(final InputStream is,
                                   final String charset) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        copy(is, baos);
        return new String(baos.toByteArray(), charset);
    }


    private void copy(final InputStream is,
                      final OutputStream os) throws IOException {
        final byte[] buffer = new byte[8*1024];

        int read = is.read(buffer);
        while (read>0) {
            os.write(buffer, 0, read);
            read = is.read(buffer);
        }
    }
}
