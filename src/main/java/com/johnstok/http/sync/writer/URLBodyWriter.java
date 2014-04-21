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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Objects;

import com.johnstok.http.IO;
import com.johnstok.http.sync.BodyWriter;

/**
 * A body writer that reads from a URL.
 *
 * @author Keith Webster Johnston.
 */
public final class URLBodyWriter
    implements
        BodyWriter {

    private final URL _url;


    /**
     * Constructor.
     *
     * @param url The URL to read from.
     */
    public URLBodyWriter(final URL url) {
        _url = Objects.requireNonNull(url);
    }


    @Override
    public void write(final OutputStream os) throws IOException {
        try (InputStream is = _url.openStream()) {
            IO.copy(is, os);
        }
    }
}