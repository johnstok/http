/*-----------------------------------------------------------------------------
 * Copyright © 2012 Keith Webster Johnston.
 * All rights reserved.
 *
 * This file is part of http.
 *
 * http is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * http is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with http.  If not, see <http://www.gnu.org/licenses/>.
 *---------------------------------------------------------------------------*/
package com.johnstok.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A HTTP response's status line.
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="6.1")
public class StatusLine {

    public static final String SYNTAX =
        "("+Version.SYNTAX+") ("+Status.SYNTAX+") (["+Status.REASON_PHRASE+"]+)";


    private final String _version;
    private final String _code;
    private final String _reasonPhrase;


    /**
     * Constructor.
     *
     * @param version      The HTTP version of the response.
     * @param code         The status code for the response.
     * @param reasonPhrase The reason phrase describing the status.
     */
    public StatusLine(final String version,
                      final String code,
                      final String reasonPhrase) {
        _version = version;
        _code = code;
        _reasonPhrase = reasonPhrase;
    }


    /**
     * Accessor.
     *
     * @return Returns the version.
     */
    public final String getVersion() {
        return _version;
    }


    /**
     * Accessor.
     *
     * @return Returns the status code.
     */
    public String getCode() {
        return _code;
    }


    /**
     * Accessor.
     *
     * @return Returns the reason phrase.
     */
    public String getReasonPhrase() {
        return _reasonPhrase;
    }


    /**
     * Parse a string into a status line.
     *
     * @param statusLineString A string representing the status line.
     *
     * @return A corresponding status line object.
     */
    public static StatusLine parse(final String statusLineString) {
        final Matcher m = Pattern.compile(SYNTAX).matcher(statusLineString);
        if (m.matches()) {
            return new StatusLine(m.group(1), m.group(4), m.group(5));
        }
        throw new ClientHttpException(Status.BAD_REQUEST);
    }
}
