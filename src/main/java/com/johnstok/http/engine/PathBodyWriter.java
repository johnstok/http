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
package com.johnstok.http.engine;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import com.johnstok.http.sync.BodyWriter;

/**
 * A body writer that reads from a {@link Path}.
 *
 * @author Keith Webster Johnston.
 */
public final class PathBodyWriter
    implements
        BodyWriter {

    private final Path _resourcePath;


    /**
     * Constructor.
     *
     * @param path The path to read from.
     */
    PathBodyWriter(final Path path) {
        _resourcePath = Utils.checkNotNull(path);
    }


    @Override
    public void write(final OutputStream os) throws IOException {
        final InputStream is = Files.newInputStream(_resourcePath);
        try {
            Utils.copy(is, os);
        } finally {
            Utils.close(is);
        }
    }
}
