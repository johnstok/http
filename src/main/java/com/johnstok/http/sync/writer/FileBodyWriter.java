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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import com.johnstok.http.engine.Utils;
import com.johnstok.http.sync.BodyWriter;


/**
 * Writes a message body from a file.
 *
 * @author Keith Webster Johnston.
 */
public class FileBodyWriter
    implements
        BodyWriter {

    private final File _file;


    /**
     * Constructor.
     *
     * @param file The file to write.
     */
    public FileBodyWriter(final File file) {
        _file = Utils.checkNotNull(file);
    }


    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("resource")
    public void write(final OutputStream outputStream) throws IOException {
        try (FileInputStream fis = new FileInputStream(_file)) {
            Utils.copy(fis, outputStream);
        }
    }
}
