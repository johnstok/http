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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import com.johnstok.http.Specification;


/**
 * Reads a message body into a String.
 *
 * @author Keith Webster Johnston.
 */
public class StringBodyReader
    implements
        BodyReader<String> {

    private final Charset _charset;


    /**
     * Constructor.
     *
     * <p>Reads a message body into a string using the HTTP default encoding of
     * ISO-8859-1.
     */
    @Specification(name="rfc-2616", section="3.7.1")
    public StringBodyReader() {
        this(StandardCharsets.ISO_8859_1);
    }


    /**
     * Constructor.
     *
     * @param charset The character set used to decode the bytes in the body.
     * NULL is not allowed.
     */
    public StringBodyReader(final Charset charset) {
        _charset = Utils.checkNotNull(charset);
    }


    /** {@inheritDoc} */
    @Override
    public String read(final InputStream inputStream) throws IOException {
        // TODO: Inefficient - replace w StringReader + StringBuilder.
        return
            new String(
                new ByteArrayBodyReader().read(inputStream),
                _charset);
    }
}
