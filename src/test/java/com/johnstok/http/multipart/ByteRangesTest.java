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
package com.johnstok.http.multipart;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;



/**
 * Tests for the {@link ByteRanges} class.
 *
 * @author Keith Webster Johnston.
 */
public class ByteRangesTest {

    /**
     * Test.
     *
     * @throws Exception If the test fails.
     */
    @Test
    public void testMimeTypeGeneratedCorrectly() throws Exception {

        // ACT
        final File f =
            new File("src/test/resources/index.html");
        final ByteRanges ranges = new ByteRanges(f);

        assertEquals(
            "multipart/byteranges; boundary="
                + ranges.getBoundary()
                + "; charset=UTF-8",
            ranges.getMimeType());
    }
}
