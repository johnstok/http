/*-----------------------------------------------------------------------------
 * Copyright © 2012 Keith Webster Johnston.
 * All rights reserved.
 *
 * This file is part of http.
 *
 * http is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * http is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with http. If not, see <http://www.gnu.org/licenses/>.
 *---------------------------------------------------------------------------*/
package com.johnstok.http;

import java.util.HashMap;
import java.util.Locale;


/**
 * A HTTP parameter.
 *
 * @author Keith Webster Johnston.
 */
public class Parameter {

    public static final String ATTRIBUTE = Syntax.TOKEN;
    public static final String VALUE     = Syntax.TOKEN;
    public static final String PARAMETER = "["+ATTRIBUTE+"]+\\=["+VALUE+"]+";


    public static HashMap<String, String> parse(final String parameterString) {
        final String[] params = parameterString.split(";");
        final HashMap<String, String> paramMap = new HashMap<String, String>();
        for (final String param : params) {
            final String paramString = param; // TODO: Tolerate whitespace?
            if (paramString.length()<1) { continue; } // TODO: Tolerate empty params (e.g. ;;)
            final String[] paramParts = paramString.split("=");
            if (2==paramParts.length) {
                final String attribute = paramParts[0];
                final String value = paramParts[1];
                if (attribute.length()>0 && value.length()>0) {
                    paramMap.put(attribute.toLowerCase(Locale.US), value);
                } else {
                    throw new ClientHttpException(Status.BAD_REQUEST);
                }
            } else {
                throw new ClientHttpException(Status.BAD_REQUEST);
            }
        }
        return paramMap;
    }
}
