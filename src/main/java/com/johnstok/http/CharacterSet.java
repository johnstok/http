/*-----------------------------------------------------------------------------
 * Copyright © 2012 Keith Webster Johnston.
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
 * along with http.  If not, see <http://www.gnu.org/licenses/>.
 *---------------------------------------------------------------------------*/
package com.johnstok.http;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A MIME character set.
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="3.4")
public class CharacterSet {
    // TODO: Implementors should be aware of IETF character set requirements [38][41].

    private static final String CHARSET = Syntax.TOKEN;
    public  static final String SYNTAX  = "(["+CHARSET+"])+";

    public static final Charset UTF_8      = Charset.forName("UTF-8");
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset DEFAULT    = ISO_8859_1;



    /**
     * Parse a string into a character set.
     *
     * @param charsetString A string representing the charset.
     *
     * @return A corresponding charset object.
     */
    public static Charset parse(final String charsetString) {
        final Matcher m = Pattern.compile(SYNTAX).matcher(charsetString);
        if (m.matches()) {
            // N.B.
            // The HTTP syntax and JDK syntax charset names do not match.
            // Java charset names are case-insensistive - consistent with HTTP.
            return Charset.forName(charsetString);
        }
        throw new ClientHttpException(Status.BAD_REQUEST);
    }
}
