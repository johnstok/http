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
import java.math.BigDecimal;
import org.junit.Test;


/**
 * Tests for the {@link QualityValue} class.
 *
 * @author Keith Webster Johnston.
 */
public class QualityValueTest {

    @Test
    public void halfEqualToHalf() {

        // ARRANGE
        QualityValue half1 = QualityValue.parse("0.5");
        QualityValue half = QualityValue.parse("0.5");

        // ACT

        // ASSERT
        assertEquals(half1, half);
    }


    @Test
    public void OneMoreThanHalf() {

        // ARRANGE
        QualityValue one  = QualityValue.parse("1");
        QualityValue half = QualityValue.parse("0.5");

        // ACT
        int i = one.compareTo(half);

        // ASSERT
        assertTrue(i>0);
    }


    @Test
    public void halfComparableToHalf() {

        // ARRANGE
        QualityValue half1 = QualityValue.parse("0.5");
        QualityValue half = QualityValue.parse("0.5");

        // ACT
        int i = half1.compareTo(half);

        // ASSERT
        assertEquals(0, i);
    }


    @Test
    public void zeroLessThanHalf() {

        // ARRANGE
        QualityValue zero = QualityValue.parse("0");
        QualityValue half = QualityValue.parse("0.5");

        // ACT
        int i = zero.compareTo(half);

        // ASSERT
        assertTrue(i<0);
    }


    @Test
    public void toStringValidForZero() {

        // ARRANGE

        // ACT
        QualityValue qv = QualityValue.parse("0.000");

        // ASSERT
        assertEquals("0.000", qv.toString());
    }


    @Test
    public void toStringValidForNoPrecision() {

        // ARRANGE

        // ACT
        QualityValue qv = QualityValue.parse("1");

        // ASSERT
        assertEquals("1", qv.toString());
    }


    @Test
    public void toStringValidForOne() {

        // ARRANGE

        // ACT
        QualityValue qv = QualityValue.parse("1.000");

        // ASSERT
        assertEquals("1.000", qv.toString());
    }


    @Test
    public void parseOne() {

        // ARRANGE

        // ACT
        QualityValue qv = QualityValue.parse("1.000");

        // ASSERT
        assertEquals(new BigDecimal("1.000"), qv.getValue());
    }


    @Test
    public void parseZero() {

        // ARRANGE

        // ACT
        QualityValue qv = QualityValue.parse("0.000");

        // ASSERT
        assertEquals(new BigDecimal("0.000"), qv.getValue());
    }


    @Test
    public void parseHalf() {

        // ARRANGE

        // ACT
        QualityValue qv = QualityValue.parse("0.5");

        // ASSERT
        assertEquals(new BigDecimal("0.5"), qv.getValue());
    }


    @Test
    public void excessPrecisionRejected() {

        // ARRANGE

        // ACT
        try {
            QualityValue.parse("0.0000");

        // ASSERT
        } catch (ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }


    @Test
    public void tooLargeRejected() {

        // ARRANGE

        // ACT
        try {
            QualityValue.parse("1.001");

            // ASSERT
        } catch (ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }


    @Test
    public void tooSmallRejected() {

        // ARRANGE

        // ACT
        try {
            QualityValue.parse("0.0001");

            // ASSERT
        } catch (ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }


    @Test
    public void negativeRejected() {

        // ARRANGE

        // ACT
        try {
            QualityValue.parse("-0.5");

            // ASSERT
        } catch (ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }


    @Test
    public void alphaNumRejected() {

        // ARRANGE

        // ACT
        try {
            QualityValue.parse("a0.5");

            // ASSERT
        } catch (ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }


    @Test
    public void numAlphaRejected() {

        // ARRANGE

        // ACT
        try {
            QualityValue.parse("0.5a");

            // ASSERT
        } catch (ClientHttpException e) {
            assertEquals(Status.BAD_REQUEST, e.getStatus());
        }
    }
}
