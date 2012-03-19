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


import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import com.johnstok.http.ContentEncoding;
import com.johnstok.http.WeightedValue;


/**
 * Tests for the {@link ContentNegotiator} class.
 *
 * @author Keith Webster Johnston.
 */
@SuppressWarnings({"nls"})
public class ContentNegotiatorTest {

    /**
     * Tests RFC-2616/14.3/4.
     */
    @Test
    public void anyWeightedZeroButIdentityWeightedOneGivesIdenity() {

        // ARRANGE
        final ContentNegotiator negotiator = new ContentNegotiator();

        // ACT
        final String encoding =
            negotiator.selectEncoding(
                new WeightedValue(ContentEncoding.ANY, 0),
                new WeightedValue(ContentEncoding.IDENTITY, 1));

        // ASSERT
        assertEquals(ContentEncoding.IDENTITY, encoding);
    }


    /**
     * Tests RFC-2616/14.3/4.
     */
    @Test
    public void anyWeightedZeroGivesNull() {

        // ARRANGE
        final ContentNegotiator negotiator = new ContentNegotiator();

        // ACT
        final String encoding =
            negotiator
                .selectEncoding(new WeightedValue(ContentEncoding.ANY, 0));

        // ASSERT
        assertNull(encoding);
    }


    /**
    * Tests RFC-2616/14.3/4.
    */
    @Test
    public void emptyAcceptEncodingGivesIdentity() {

        // ARRANGE
        final ContentNegotiator negotiator =
            new ContentNegotiator(new WeightedValue("foo", 1));

        // ACT
        final String encoding =
            negotiator.select(new ArrayList<WeightedValue>());

        // ASSERT
        assertEquals(ContentEncoding.IDENTITY, encoding);
    }


    /**
         * Tests RFC-2616/14.3/3.
         */
    @Test
    public void highestNonZeroValueSelected() {

        // ARRANGE
        final ContentNegotiator negotiator =
            new ContentNegotiator(
                new WeightedValue("foo", 1),
                new WeightedValue("bar", 1));

        // ACT
        final String encoding =
            negotiator.selectEncoding(
                new WeightedValue("foo", 0.001f),
                new WeightedValue("bar", 1));

        // ASSERT
        assertEquals("bar", encoding);
    }


    /**
     * Tests RFC-2616/14.3/4.
     */
    @Test
    public void identityCodingIsAlwaysAcceptable() {

        // ARRANGE
        final ContentNegotiator negotiator =
            new ContentNegotiator(new WeightedValue("foo", 1));

        // ACT
        final String encoding =
            negotiator.select(new ArrayList<WeightedValue>());

        // ASSERT
        assertEquals(ContentEncoding.IDENTITY, encoding);
    }


    /**
     * Tests RFC-2616/14.3/4.
     */
    @Test
    public void identityWeightedZeroGivesNull() {

        // ARRANGE
        final ContentNegotiator negotiator = new ContentNegotiator();

        // ACT
        final String encoding =
            negotiator.selectEncoding(new WeightedValue(
                ContentEncoding.IDENTITY,
                0));

        // ASSERT
        assertNull(encoding);
    }


    /**
     * Tests RFC-2616/14.3.
     *
     * "If no Accept-Encoding field is present in a request, the
     * server MAY assume that the client will accept any content
     * coding. In this case, if "identity" is one of the available
     * content-codings, then the server SHOULD use the "identity"
     * content-coding, unless it has additional information that
     * a different content-coding is meaningful to the client."
     */
    @Test
    public void missingAcceptEncodingGivesIdentity() {

        // ARRANGE
        final ContentNegotiator negotiator = new ContentNegotiator();

        // ACT
        final String encoding =
            negotiator.select((List<WeightedValue>) null);

        // ASSERT
        assertEquals(ContentEncoding.IDENTITY, encoding);
    }


    /**
     * Tests RFC-2616/14.3/2.
     */
    @Test
    public void starMatchesAnyCoding() {

        // ARRANGE
        final ContentNegotiator negotiator =
            new ContentNegotiator(
                new WeightedValue("foo", 1),
                new WeightedValue("bar", 0.5f));

        // ACT
        final String encoding =
            negotiator.selectEncoding(
                new WeightedValue("foo", 0),
                new WeightedValue("*", 1));

        // ASSERT
        assertEquals("bar", encoding);
    }


    /**
     * Tests RFC-2616/14.3/1.
     */
    @Test
    public void weightOfZeroDisallowsCoding() {

        // ARRANGE
        final ContentNegotiator negotiator =
            new ContentNegotiator(
                new WeightedValue("foo", 1),
                new WeightedValue("bar", 1));

        // ACT
        final String encoding =
            negotiator.selectEncoding(
                new WeightedValue("foo", 0),
                new WeightedValue("bar", 1));

        // ASSERT
        assertEquals("bar", encoding);
    }
}
