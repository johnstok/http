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
package com.johnstok.http.negotiation;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import com.johnstok.http.ContentEncoding;
import com.johnstok.http.Specification;
import com.johnstok.http.Specifications;
import com.johnstok.http.WeightedValue;


/**
 * Negotiator for entity encodings.
 *
 * @author Keith Webster Johnston.
 */
@Specifications({
    @Specification(name="rfc-2616", section="3.5"),
    @Specification(name="rfc-2616", section="14.3")
})
public class ContentNegotiator
    implements
        Negotiator<String> {

    private static final WeightedValue ANY =
        new WeightedValue(ContentEncoding.ANY, 0.001f);
    private static final WeightedValue IDENTITY =
        new WeightedValue(ContentEncoding.IDENTITY, 0.001f);

    private final Set<WeightedValue> _supportedEncodings;


    /**
     * Constructor.
     *
     * @param values The supported encodings
     */
    public ContentNegotiator(final WeightedValue... values) {
        final List<WeightedValue> supported = Arrays.asList(values);
        Collections.sort(supported);
        _supportedEncodings = new LinkedHashSet<WeightedValue>(supported);
        _supportedEncodings.add(IDENTITY);
        _supportedEncodings.remove(ANY);
    }


    /**
     * Constructor.
     *
     * @param values The supported encodings
     */
    public ContentNegotiator(final Set<String> values) {
        final List<WeightedValue> supported = new ArrayList<WeightedValue>();
        for (final String value : values) {
            supported.add(new WeightedValue(value, 1.0f));
        }
        Collections.sort(supported);
        _supportedEncodings = new LinkedHashSet<WeightedValue>(supported);
        _supportedEncodings.add(IDENTITY);
        _supportedEncodings.remove(ANY);
    }


    /**
     * Select an encoding from the specified list.
     *
     * @param clientEncodings The allowed encodings.
     *
     * @return The encoding selected using the HTTP 1.1 algorithm.
     */
    @Override
    public String select(final List<WeightedValue> clientEncodings) {
        if (null == clientEncodings) { return ContentEncoding.IDENTITY; }

        final List<WeightedValue> disallowedEncodings =
            new ArrayList<WeightedValue>();
        final List<WeightedValue> allowedEncodings =
            new ArrayList<WeightedValue>();

        for (final WeightedValue clientEncoding : clientEncodings) {
            if (clientEncoding.getWeight()<=0) {
                disallowedEncodings.add(clientEncoding);
            } else {
                allowedEncodings.add(clientEncoding);
            }
        }

        Collections.sort(allowedEncodings); //14.3#3

        for (final WeightedValue clientEncoding : allowedEncodings) {
            if (_supportedEncodings.contains(clientEncoding)) {
                return clientEncoding.getValue();
            }
            if (ContentEncoding.ANY.equals(clientEncoding.getValue())) {
                for (final WeightedValue supported : _supportedEncodings) {
                    if (!disallowedEncodings.contains(supported)) {
                        return supported.getValue();
                    }
                }
            }
        }
        if (disallowedEncodings.contains(ANY)
            && !allowedEncodings.contains(IDENTITY)) { return null; }
        if (disallowedEncodings.contains(IDENTITY)) { return null; }
        return ContentEncoding.IDENTITY;
    }


    /**
     * Select an encoding from the specified list.
     *
     * @param clientEncodings The allowed encodings.
     *
     * @return The encoding selected using the HTTP 1.1 algorithm.
     */
    public String selectEncoding(final WeightedValue... clientEncodings) {
        return select(Arrays.asList(clientEncodings));
    }
}
