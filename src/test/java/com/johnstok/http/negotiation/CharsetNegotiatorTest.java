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

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.johnstok.http.WeightedValue;



/**
 * TODO: Add a description for this type.
 *
 * @author Keith Webster Johnston.
 */
public class CharsetNegotiatorTest {

    private static final Charset UTF_8      = Charset.forName("UTF-8");
    private static final Charset UTF_16BE   = Charset.forName("UTF-16BE");
    private static final Charset ISO_8859_1 = Charset.forName("iso-8859-1");

    CharsetNegotiator _negotiator;


    @Before
    public void setUp() {
        _negotiator = new CharsetNegotiator(UTF_16BE, UTF_8, ISO_8859_1);
    }


    @After
    public void tearDown() {
        _negotiator = null;
    }


    @Test
    public void nullGivesNull() {

        // ACT
        final Charset selected  = _negotiator.select(null);

        // ASSERT
        Assert.assertNull(selected);
    }


    @Test
    public void defaultGivesDefault() {

        // ACT
        final Charset selected  =
            _negotiator.select(
                Collections.singletonList(
                    new WeightedValue("utf-8", 1f)));

        // ASSERT
        Assert.assertEquals(UTF_8, selected);
    }


    @Test
    public void subOptimaldefaultGivesISO_8859_1() {

        // ACT
        final Charset selected  =
            _negotiator.select(
                Collections.singletonList(
                    new WeightedValue("utf-8", 0.5f)));

        // ASSERT
        Assert.assertEquals(ISO_8859_1, selected);
    }


    @Test
    public void missingCharsetGivesNullIfAnyDisallowed() {

        // ACT
        final Charset selected  =
            _negotiator.select(
                new ArrayList<WeightedValue>() {{
                    add(new WeightedValue("*", 0f));
                    add(new WeightedValue("foo", 1f));
                }}
            );

        // ASSERT
        Assert.assertNull(selected);
    }


    @Test
    public void missingCharsetGivesNullIfISO_8859_1Disallowed() {

        // ACT
        final Charset selected  =
            _negotiator.select(
                new ArrayList<WeightedValue>() {{
                    add(new WeightedValue("iso-8859-1", 0f));
                    add(new WeightedValue("foo", 1f));
                }}
            );

        // ASSERT
        Assert.assertNull(selected);
    }


    @Test
    public void noneAllowedGivesNull() {

        // ACT
        final Charset selected  =
            _negotiator.select(
                new ArrayList<WeightedValue>() {{
                    add(new WeightedValue("*", 0f));
                }}
            );

        // ASSERT
        Assert.assertNull(selected);
    }


    @Test
    public void missingCharsetGivesISO_8859_1() {

        // ACT
        final Charset selected  =
            _negotiator.select(
                new ArrayList<WeightedValue>() {{
                    add(new WeightedValue("foo", 1f));
                    add(new WeightedValue("bar", 0.5f));
                }}
            );

        // ASSERT
        Assert.assertEquals(Charset.forName("iso-8859-1"), selected);
    }


    @Test
    public void wildcardMatchGivesBig5() {

        // ACT
        final Charset selected  =
            _negotiator.select(
                new ArrayList<WeightedValue>() {{
                    add(new WeightedValue("*", 0.1f));
                }}
            );

        // ASSERT
        Assert.assertEquals(UTF_8, selected);
    }


    @Test
    public void wildcardMatchRespectsExclusionOnName() {

        // ACT
        final Charset selected  =
            _negotiator.select(
                new ArrayList<WeightedValue>() {{
                    add(new WeightedValue("utf-8", 0f));
                    add(new WeightedValue("*",    1f));
                }}
            );

        // ASSERT
        Assert.assertEquals(UTF_16BE, selected);
    }


    @Test
    public void wildcardMatchRespectsExclusionOnAlias() {

        // ACT
        final Charset selected  =
            _negotiator.select(
                new ArrayList<WeightedValue>() {{
                    add(new WeightedValue("UnicodeBigUnmarked", 0f));
                    add(new WeightedValue("*",    1f));
                }}
            );

        // ASSERT
        Assert.assertEquals(UTF_8, selected);
    }


    @Test
    public void highestWeightCharsetIsSelected() {

        // ACT
        final Charset selected  =
            _negotiator.select(
                new ArrayList<WeightedValue>() {{
                    add(new WeightedValue("*",   0.001f));
                    add(new WeightedValue("utf-8", 0.5f));
                    add(new WeightedValue("utf-16be", 1f));
                }}
            );

        // ASSERT
        Assert.assertEquals(UTF_16BE, selected);
    }
}
