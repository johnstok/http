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

import static org.junit.Assert.*;
import org.junit.Test;



/**
 * Tests for the {@link LanguageTag} class.
 *
 * @author Keith Webster Johnston.
 */
@SuppressWarnings("unused")
public class LanguageTagTest {


    @Test
    public void sameAreEqual() {

        // ARRANGE
        final LanguageTag t = new LanguageTag("en");

        // ACT
        final boolean equal = t.equals(t);

        // ASSERT
        assertTrue(equal);
    }


    @Test
    public void equivalentAreEqual() {

        // ARRANGE
        final LanguageTag t = new LanguageTag("en");
        final LanguageTag u = new LanguageTag("en");

        // ACT
        final boolean equal = t.equals(u);

        // ASSERT
        assertTrue(equal);
    }


    @Test
    public void varyingCasesAreEqual() {

        // ARRANGE
        final LanguageTag t = new LanguageTag("En");
        final LanguageTag u = new LanguageTag("eN");

        // ACT
        final boolean equal = t.equals(u);

        // ASSERT
        assertTrue(equal);
    }


    @Test
    public void equivalentMatch() {

        // ARRANGE
        final LanguageTag t = new LanguageTag("en");

        // ACT
        final boolean matches = t.matchedBy("en");

        // ASSERT
        assertTrue(matches);
    }


    @Test
    public void varyingCasesMatch() {

        // ARRANGE
        final LanguageTag t = new LanguageTag("En");

        // ACT
        final boolean matches = t.matchedBy("eN");

        // ASSERT
        assertTrue(matches);
    }


    @Test
    public void lessSpecificMatch() {

        // ARRANGE
        final LanguageTag t = new LanguageTag("en-gb");

        // ACT
        final boolean matches = t.matchedBy("en");

        // ASSERT
        assertTrue(matches);
    }


    @Test
    public void lessSpecificCaseInsensitiveMatch() {

        // ARRANGE
        final LanguageTag t = new LanguageTag("eN-gB");

        // ACT
        final boolean matches = t.matchedBy("En");

        // ASSERT
        assertTrue(matches);
    }


    @Test
    public void moreSpecificWontMatch() {

        // ARRANGE
        final LanguageTag t = new LanguageTag("en");

        // ACT
        final boolean matches = t.matchedBy("en-gb");

        // ASSERT
        assertFalse(matches);
    }


    @Test
    public void tooShortWontMatch() {

        // ARRANGE
        final LanguageTag t = new LanguageTag("en-gb");

        // ACT
        final boolean matches = t.matchedBy("e");

        // ASSERT
        assertFalse(matches);
    }


    @Test
    public void EndsWithDashWontMatch() {

        // ARRANGE
        final LanguageTag t = new LanguageTag("en-gb");

        // ACT
        final boolean matches = t.matchedBy("en-");

        // ASSERT
        assertFalse(matches);
    }


    @Test
    public void emptyDisallowed() {

        // ARRANGE

        // ACT
        try {
            new LanguageTag(" \n\t");
            fail();

        // ASSERT
        } catch (final IllegalArgumentException e) {
            assertEquals("String ' \n\t' does not match regular expression /\\p{Alpha}{1,8}(-\\p{Alpha}{1,8})*/.", e.getMessage());
        }
    }


    @Test
    public void zlsDisallowed() {

        // ARRANGE

        // ACT
        try {
            new LanguageTag("");
            fail();

        // ASSERT
        } catch (final IllegalArgumentException e) {
            assertEquals("String '' does not match regular expression /\\p{Alpha}{1,8}(-\\p{Alpha}{1,8})*/.", e.getMessage());
        }
    }


    @Test
    public void nullDisallowed() {

        // ARRANGE

        // ACT
        try {
            new LanguageTag(null);
            fail();

        // ASSERT
        } catch (final NullPointerException e) {
            assertNull(e.getMessage());
        }
    }


    @Test
    public void whitespaceDisallowed() {

        // ARRANGE

        // ACT
        try {
            new LanguageTag("en gb");
            fail();

        // ASSERT
        } catch (final IllegalArgumentException e) {
            assertEquals("String 'en gb' does not match regular expression /\\p{Alpha}{1,8}(-\\p{Alpha}{1,8})*/.", e.getMessage());
        }
    }


    @Test
    public void nonAlphaDisallowed() {

        // ARRANGE

        // ACT
        try {
            new LanguageTag("e1");
            fail();

        // ASSERT
        } catch (final IllegalArgumentException e) {
            assertEquals("String 'e1' does not match regular expression /\\p{Alpha}{1,8}(-\\p{Alpha}{1,8})*/.", e.getMessage());
        }
    }


    @Test
    public void malformed1Disallowed() {

        // ARRANGE

        // ACT
        try {
            new LanguageTag("-en");
            fail();

        // ASSERT
        } catch (final IllegalArgumentException e) {
            assertEquals("String '-en' does not match regular expression /\\p{Alpha}{1,8}(-\\p{Alpha}{1,8})*/.", e.getMessage());
        }
    }


    @Test
    public void malformed2Disallowed() {

        // ARRANGE

        // ACT
        try {
            new LanguageTag("en-");
            fail();

        // ASSERT
        } catch (final IllegalArgumentException e) {
            assertEquals("String 'en-' does not match regular expression /\\p{Alpha}{1,8}(-\\p{Alpha}{1,8})*/.", e.getMessage());
        }
    }


    @Test
    public void malformed3Disallowed() {

        // ARRANGE

        // ACT
        try {
            new LanguageTag("en--gb");
            fail();

        // ASSERT
        } catch (final IllegalArgumentException e) {
            assertEquals("String 'en--gb' does not match regular expression /\\p{Alpha}{1,8}(-\\p{Alpha}{1,8})*/.", e.getMessage());
        }
    }


    @Test
    public void tooLong1Disallowed() {

        // ARRANGE

        // ACT
        try {
            new LanguageTag("abcdefghi");
            fail();

        // ASSERT
        } catch (final IllegalArgumentException e) {
            assertEquals("String 'abcdefghi' does not match regular expression /\\p{Alpha}{1,8}(-\\p{Alpha}{1,8})*/.", e.getMessage());
        }
    }


    @Test
    public void tooLong2Disallowed() {

        // ARRANGE

        // ACT
        try {
            new LanguageTag("en-abcdefghi");
            fail();

        // ASSERT
        } catch (final IllegalArgumentException e) {
            assertEquals("String 'en-abcdefghi' does not match regular expression /\\p{Alpha}{1,8}(-\\p{Alpha}{1,8})*/.", e.getMessage());
        }
    }
}
