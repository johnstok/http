/*-----------------------------------------------------------------------------
 * Copyright © 2013 Keith Webster Johnston.
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
package com.johnstok.http.sync.writer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import com.johnstok.http.engine.Utils;
import com.johnstok.http.sync.BodyWriter;


/**
 * Writes a message body from a string.
 *
 * @author Keith Webster Johnston.
 */
public class StringBodyWriter
    implements
        BodyWriter {

    private final String  _string;
    private final Charset _charset;


    /**
     * Constructor.
     *
     * @param string  The string to write.
     * @param charset The character set used to encode the string.
     */
    public StringBodyWriter(final String string, final Charset charset) {
        _string = Utils.checkNotNull(string);
        _charset = Utils.checkNotNull(charset);
    }


    /** {@inheritDoc} */
    @Override
    public void write(final OutputStream outputStream) throws IOException {
        final InputStream is =
            new ByteArrayInputStream(_string.getBytes(_charset)); // FIXME: duplicates string in memory as a byte array.
        try {
            Utils.copy(is, outputStream);
        } finally {
            Utils.close(is);
        }
    }
}
