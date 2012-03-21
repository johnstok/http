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

import static org.junit.Assert.*;
import org.junit.Test;


/**
 * Tests for the {@link Time} class.
 *
 * @author Keith Webster Johnston.
 */
public class TimeTest {

    @Test
    public void toStringForNegativeZeroDiscardsSign() {

        // ARRANGE
        Time t = new Time(-0);

        // ACT
        String timeString = t.toString();

        // ASSERT
        assertEquals("0", timeString);
    }

    @Test
    public void toStringIsValid() {

        // ARRANGE
        Time t = new Time(Long.MAX_VALUE);

        // ACT
        String timeString = t.toString();

        // ASSERT
        assertEquals(String.valueOf(Long.MAX_VALUE), timeString);
    }


    @Test
    public void parseMaxLongIsValid() {

        // ARRANGE

        // ACT
        Time t = Time.parse(String.valueOf(Long.MAX_VALUE));

        // ASSERT
        assertEquals(Long.MAX_VALUE, t.getSeconds());
    }


    @Test
    public void parseZeroIsValid() {

        // ARRANGE

        // ACT
        Time t = Time.parse("0");

        // ASSERT
        assertEquals(0l, t.getSeconds());
    }


    @Test
    public void parseOneIsValid() {

        // ARRANGE

        // ACT
        Time t = Time.parse("1");

        // ASSERT
        assertEquals(1l, t.getSeconds());
    }


    @Test
    public void parseAlphaIsInvalid() {

        // ARRANGE

        // ACT
        try {
            Time.parse("abc");

        // ASSERT
        } catch (ClientHttpException e){
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }


    @Test
    public void parseAlphaNumIsInvalid() {

        // ARRANGE

        // ACT
        try {
            Time.parse("abc123");

            // ASSERT
        } catch (ClientHttpException e){
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }


    @Test
    public void parseNumAlphaIsInvalid() {

        // ARRANGE

        // ACT
        try {
            Time.parse("123abc");

            // ASSERT
        } catch (ClientHttpException e){
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }


    @Test
    public void parseNegativeZeroIsInvalid() {

        // ARRANGE

        // ACT
        try {
            Time.parse("-0");

            // ASSERT
        } catch (ClientHttpException e){
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }


    @Test
    public void parseNegativeOneIsInvalid() {

        // ARRANGE

        // ACT
        try {
            Time.parse("-1");

            // ASSERT
        } catch (ClientHttpException e){
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }


    @Test
    public void zeroIsValid() {

        // ARRANGE

        // ACT
        Time t = new Time(0);

        // ASSERT
        assertEquals(0l, t.getSeconds());
    }


    @Test
    public void oneIsValid() {

        // ARRANGE

        // ACT
        Time t = new Time(1);

        // ASSERT
        assertEquals(1l, t.getSeconds());
    }


    @Test
    public void maxLongIsValid() {

        // ARRANGE

        // ACT
        Time t = new Time(Long.MAX_VALUE);

        // ASSERT
        assertEquals(Long.MAX_VALUE, t.getSeconds());
    }


    @Test
    public void negativeZeroIsValid() {

        // ARRANGE

        // ACT
        Time t = new Time(-0);

        // ASSERT
        assertEquals(0l, t.getSeconds());
    }


    @Test
    public void negativeOneIsInvalid() {

        // ARRANGE

        // ACT
        try {
            new Time(-1);

        // ASSERT
        } catch (ClientHttpException e){
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }
}
