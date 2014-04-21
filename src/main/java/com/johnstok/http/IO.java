/*-----------------------------------------------------------------------------
 * Copyright © 2014 Keith Webster Johnston.
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
 * along with http. If not, see <http://www.gnu.org/licenses/>.
 *---------------------------------------------------------------------------*/
package com.johnstok.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Helper class for IO operations.
 */
public class IO {


    /**
     * Copy from an input stream to an output stream.
     *
     * @param is The input stream to read from.
     * @param os The output stream to write to.
     */
    public static void copy(final InputStream is,
                            final OutputStream os) throws IOException {
        final byte[] buffer = new byte[1024];
        int read = is.read(buffer);
        while (0<read) {
            os.write(buffer, 0, read);
            read = is.read(buffer);
        }
    }
}
