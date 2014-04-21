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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

/**
 * A codec for the 'x-www-form-urlencoded' encoding.
 */
public class UrlCodec {


    /**
     * Decode a 'x-www-form-urlencoded' string.
     *
     * @param string  The string to decode.
     * @param charset The charset used to decode the string.
     *
     * @return The decoded string.
     */
    public static String decode(final String string, final Charset charset) {
        try {
            return URLDecoder.decode(string, charset.name());
        } catch (final UnsupportedEncodingException e) {
            throw new UnsupportedCharsetException(charset.name());
        }
    }
}
