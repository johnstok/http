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
package com.johnstok.http.sync.reader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.johnstok.http.IO;
import com.johnstok.http.sync.BodyReader;


/**
 * Reads a message body into a byte array.
 *
 * @author Keith Webster Johnston.
 */
public class ByteArrayBodyReader
    implements
        BodyReader<byte[]> {

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("resource")
    public byte[] read(final InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            IO.copy(inputStream, baos);
            return baos.toByteArray();
        }
    }
}
