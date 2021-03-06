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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.johnstok.http.MediaType;
import com.johnstok.http.Value;
import com.johnstok.http.WeightedValue;


/**
 * Negotiates the preferred media type for a resource.
 *
 * Implements RFC-2616, Section 14.1.
 *
 * @author Keith Webster Johnston.
 */
// FIXME: Add specifications.
public class MediaTypeNegotiator
    implements
        Negotiator<MediaType> {

    private final Set<MediaType> _availableMediaTypes;


    /**
     * Constructor.
     *
     * @param availableMediaTypes
     */
    public MediaTypeNegotiator(final Collection<MediaType> availableMediaTypes) {
        _availableMediaTypes = new LinkedHashSet<MediaType>(availableMediaTypes);
    }


    /**
     * Constructor.
     *
     * @param availableMediaTypes
     */
    public MediaTypeNegotiator(final MediaType... availableMediaTypes) {
        this(Arrays.asList(availableMediaTypes));
    }


    /**
     * Parse an 'Accept' header into a list of weighted values.
     *
     * <pre>
     * Accept         = "Accept" ":"
     *                  #( media-range [ accept-params ] )
     * media-range    = ( "&#42;/*"
     *                  | ( type "/" "*" )
     *                  | ( type "/" subtype )
     *                  ) *( ";" parameter )
     * accept-params  = ";" "q" "=" qvalue *( accept-extension )
     * accept-extension = ";" token [ "=" ( token | quoted-string ) ]
     * </pre>
     *
     * The asterisk "*" character is used to group media types into ranges, with
     * "&#42;/*" indicating all media types and "type/*" indicating all subtypes
     * of that type. The media-range MAY include media type parameters that are
     * applicable to that range.
     *
     * Each media-range MAY be followed by one or more accept-params, beginning
     * with the "q" parameter for indicating a relative quality factor. The
     * first "q" parameter (if any) separates the media-range parameter(s) from
     * the accept-params. Quality factors allow the user or user agent to
     * indicate the relative degree of preference for that media-range, using
     * the qvalue scale from 0 to 1 (section 3.9). The default value is q=1.
     *
     * Use of the "q" parameter name to separate media type parameters from
     * Accept extension parameters is due to historical practice. Although this
     * prevents any media type parameter named "q" from being used with a media
     * range, such an event is believed to be unlikely given the lack of any "q"
     * parameters in the IANA media type registry and the rare usage of any
     * media type parameters in Accept. Future media types are discouraged from
     * registering any parameter named "q".
     *
     * @param value The value to parse.
     *
     * @return The corresponding list of weighted values.
     */
    public static List<WeightedValue> parse(final String value) {
        /*
         * TODO Handle:
         *  - duplicate mtRange (incl case variations).
         *  - malformed mtRange
         *  - malformed value
         */
        // FIXME: Replace with implementation in the MediaType class.
        final List<WeightedValue> wValues = new ArrayList<WeightedValue>();

        if ((null==value) || (1>value.trim().length())) { return wValues; }

        final String[] mtRanges = value.split(",");
        for (final String mtRange : mtRanges) {
            if ((null==mtRange) || (1>mtRange.trim().length())) { continue; }
            try {
                wValues.add(Value.parse(mtRange).asWeightedValue("q", 1f));
            } catch (final NumberFormatException e) {
                // TODO: Log invalid weighted value.
            }
        }

        return wValues;
    }


    /**
     * Negotiate a media type.
     *
     * If no Accept header field is present, then it is assumed that the client
     * accepts all media types. If an Accept header field is present, and if the
     * server cannot send a response which is acceptable according to the
     * combined Accept field value, then the server SHOULD send a 406 (not
     * acceptable) response.
     *
     * Media ranges can be overridden by more specific media ranges or specific
     * media types. If more than one media range applies to a given type, the
     * most specific reference has precedence. For example,
     *
     * <pre>Accept: text/*, text/html, text/html;level=1, &#42;/*</pre>
     * have the following precedence:
     * <pre>
     * 1) text/html;level=1
     * 2) text/html
     * 3) text/*
     * 4) &#42;/*
     * </pre>
     *
     * The media type quality factor associated with a given type is determined
     * by finding the media range with the highest precedence which matches that
     * type. For example,
     *
     * <pre>
     * Accept: text/*;q=0.3, text/html;q=0.7, text/html;level=1,
     *         text/html;level=2;q=0.4, &#42;/*;q=0.5
     * </pre>
     * would cause the following values to be associated:
     * <pre>
     * text/html;level=1         = 1
     * text/html                 = 0.7
     * text/plain                = 0.3
     *
     * image/jpeg                = 0.5
     * text/html;level=2         = 0.4
     * text/html;level=3         = 0.7
     * </pre>
     * Note: A user agent might be provided with a default set of quality
     * values for certain media ranges. However, unless the user agent is
     * a closed system which cannot interact with other rendering agents,
     * this default set ought to be configurable by the user.
     *
     * @param mediaRanges The range of accepted media types.
     *
     * @return The selected media type.
     *
     * @see Negotiator#select(List)
     */
    @Override
    public MediaType select(final List<WeightedValue> mediaRanges) {

        if ((null==mediaRanges) || (0==mediaRanges.size())) {
            // Any media type is acceptable - return the first.
            // FIXME: Doesn't handle 0 available media types.
            return _availableMediaTypes.iterator().next();
        }

        // Calculate weights
        final Map<MediaType, Float> weightedMediaTypes = weights(mediaRanges);

        // No matches.
        if (0==weightedMediaTypes.size()) { return null; }

        // Select best quality media type.
        final Map.Entry<MediaType, Float> max =
            Collections.max(
                weightedMediaTypes.entrySet(),
                new Comparator<Map.Entry<MediaType, Float>>() {
                    @Override
                    public int compare(final Entry<MediaType, Float> o1,
                                       final Entry<MediaType, Float> o2) {
                        return Float.compare(o1.getValue(), o2.getValue());
                    }
            });

        return (max.getValue()>0f) ? max.getKey() : null;
    }


    /**
     * Negotiate a media type.
     *
     * @see MediaTypeNegotiator#select(List)
     *
     * @param mediaRanges The range of accepted media types.
     *
     * @return The selected media type.
     */
    public MediaType select(final WeightedValue... mediaRanges) {
        return select(Arrays.asList(mediaRanges));
    }


    /**
     * Calculate the quality for each available media type.
     *
     * @param mediaRanges The media ranges specifying quality weightings.
     *
     * @return A map from media type to quality weighting.
     */
    public Map<MediaType, Float> weights(final List<WeightedValue> mediaRanges) {

        final Map<MediaType, Float> weights = new HashMap<MediaType, Float>();

        /*
         * If no range in the field matches the media type, the quality factor
         * assigned is 0.
         */
        final float defaultWeight = 0;

        for (final MediaType avail : _availableMediaTypes) {
            float weight = defaultWeight;
            MediaType match = null;

            for (final WeightedValue v : mediaRanges) {
                final MediaType mediaRange = MediaType.parse(v.getValue());
                if (avail.matches(mediaRange) && mediaRange.precedes(match)) {
                    weight = v.getWeight();
                    match = mediaRange;
                }
            }
            weights.put(avail, weight);
        }

        return weights;
    }


    /**
     * Calculate the quality for each available media type.
     *
     * @param mediaRanges The media ranges specifying quality weightings.
     *
     * @return A map from media type to quality weighting.
     */
    public Map<MediaType, Float> weights(final WeightedValue... mediaRanges) {
        return weights(Arrays.asList(mediaRanges));
    }
}
