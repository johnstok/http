/*-----------------------------------------------------------------------------
 * Copyright © 2013 Keith Webster Johnston.
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
 * along with http. If not, see <http://www.gnu.org/licenses/>.
 *---------------------------------------------------------------------------*/
package com.johnstok.http;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.johnstok.http.engine.Utils;


/**
 * Implements a URI query.
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-3986", section="3.4")
public class Query {

    final Map<String, List<String>> _params =
        new HashMap<String, List<String>>();


    /**
     * Constructor.
     *
     * @param queryString The string to parse into a query.
     * @param charset     The character set used to URL encode the query string.
     *
     * @throws UnsupportedEncodingException If the specified {@code charset} is
     *  not supported.
     */
    public Query(final String queryString,
                 final Charset charset) throws UnsupportedEncodingException {
        for (String param : queryString.split("&")) {
            if (0==param.trim().length()) { continue; }
            String[] paramParts = param.split("=");
            if (1==paramParts.length) {
                String key = Utils.decode(paramParts[0].trim(), charset);
                if (0==key.length()) { continue; }
                List<String> values = _params.get(key);
                if (null==values) { _params.put(key, new ArrayList<String>()); }
            } else if (2==paramParts.length) {
                String key = Utils.decode(paramParts[0].trim(), charset);
                String value = Utils.decode(paramParts[1].trim(), charset);
                if (0==key.length()) { continue; }
                List<String> values = _params.get(key);
                if (null==values) { _params.put(key, new ArrayList<String>()); }
                values.add(value);
            } // Discard params with multiple '=' char's as invalid.
        }
    }


    /**
     * Given the name of a key, look up the corresponding value in the query
     * string.
     *
     * <p>If multiple values were present in the query string for this key it is
     * unspecified which of the values will be returned.
     *
     * @param paramName The name of the required query parameter.
     *
     * @return Returns the decoded query value.
     */
    String getQueryValue(final String paramName) {
        return getQueryValue(paramName, null);
    }


    /**
     * Given the name of a key, look up the corresponding values in the query
     * string.
     *
     * @param paramName The name of the required query parameter.
     * @param defaultValue The default value to return if no such parameter
     *  exists.
     *
     * @return Returns the decoded query value.
     */
    List<String> getQueryValues(final String paramName) {
        // FIXME: Copy the internal state.
        return _params.get(paramName);
    }


    /**
     * Given the name of a key and a default value if not present, look up the
     * corresponding value in the query string.
     *
     * <p>If multiple values were present in the query string for this key it is
     * unspecified which of the values will be returned.
     *
     * @param paramName The name of the required query parameter.
     * @param defaultValue The default value to return if no such parameter
     *  exists.
     *
     * @return Returns the decoded query value.
     */
    String getQueryValue(final String paramName, final String defaultValue) {
        final List<String> value = _params.get(paramName);
        return
            ((null==value) || (value.size()<1)) ? defaultValue : value.get(0);
    }


    /**
     * Get all available query values.
     *
     * @return A map containing all decoded query values.
     */
    Map<String, List<String>> getQueryValues() {
        // FIXME: Copy the internal state.
        return _params;
    }


    /**
     * Does this request have at least one param with the specified name.
     *
     * @param paramName The name of the required query param.
     *
     * @return True if the request has such a param; false otherwise.
     */
    boolean hasParam(final String paramName) {
        return null!=getQueryValue(paramName);
    }
}
