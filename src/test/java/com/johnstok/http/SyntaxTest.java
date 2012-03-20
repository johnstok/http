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
 * Tests for the {@link Syntax} class.
 *
 * @author Keith Webster Johnston.
 */
public class SyntaxTest {


    @Test
    public void tokenMatching() {

        assertFalse(classOnce(Syntax.TOKEN, "\u0000"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0001"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0002"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0003"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0004"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0005"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0006"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0007"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0008"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0009"));
        assertFalse(classOnce(Syntax.TOKEN, "\\u000a"));
        assertFalse(classOnce(Syntax.TOKEN, "\u000b"));
        assertFalse(classOnce(Syntax.TOKEN, "\u000c"));
        assertFalse(classOnce(Syntax.TOKEN, "\\u000d"));
        assertFalse(classOnce(Syntax.TOKEN, "\u000e"));
        assertFalse(classOnce(Syntax.TOKEN, "\u000f"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0010"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0011"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0012"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0013"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0014"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0015"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0016"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0017"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0018"));
        assertFalse(classOnce(Syntax.TOKEN, "\u0019"));
        assertFalse(classOnce(Syntax.TOKEN, "\u001a"));
        assertFalse(classOnce(Syntax.TOKEN, "\u001b"));
        assertFalse(classOnce(Syntax.TOKEN, "\u001c"));
        assertFalse(classOnce(Syntax.TOKEN, "\u001d"));
        assertFalse(classOnce(Syntax.TOKEN, "\u001e"));
        assertFalse(classOnce(Syntax.TOKEN, "\u001f"));

        assertFalse(classOnce(Syntax.TOKEN, "\u007f"));

        assertFalse(classOnce(Syntax.TOKEN, "("));
        assertFalse(classOnce(Syntax.TOKEN, ")"));
        assertFalse(classOnce(Syntax.TOKEN, "<"));
        assertFalse(classOnce(Syntax.TOKEN, ">"));
        assertFalse(classOnce(Syntax.TOKEN, "@"));
        assertFalse(classOnce(Syntax.TOKEN, ","));
        assertFalse(classOnce(Syntax.TOKEN, ";"));
        assertFalse(classOnce(Syntax.TOKEN, ":"));
        assertFalse(classOnce(Syntax.TOKEN, "\\"));
        assertFalse(classOnce(Syntax.TOKEN, "\""));
        assertFalse(classOnce(Syntax.TOKEN, "/"));
        assertFalse(classOnce(Syntax.TOKEN, "["));
        assertFalse(classOnce(Syntax.TOKEN, "]"));
        assertFalse(classOnce(Syntax.TOKEN, "?"));
        assertFalse(classOnce(Syntax.TOKEN, "="));
        assertFalse(classOnce(Syntax.TOKEN, "{"));
        assertFalse(classOnce(Syntax.TOKEN, "}"));
        assertFalse(classOnce(Syntax.TOKEN, " "));
        assertFalse(classOnce(Syntax.TOKEN, "\t"));

        assertFalse(classOnceOrMore(Syntax.TOKEN, "()<>@,;:\\\"/[]?={} \t"));

        assertTrue(classOnce(Syntax.TOKEN, "q"));
        assertTrue(classOnce(Syntax.TOKEN, "http"));
        assertTrue(classOnce(Syntax.TOKEN, "filename"));
        assertTrue(classOnce(Syntax.TOKEN, "level"));
    }


    @Test
    public void octetMatching() {
        for (int i=0; i<256; i++) {
            String s = ""+(char)i;
            assertTrue(classOnce(Syntax.OCTET, s));
        }

        assertFalse(classOnce(Syntax.OCTET, ""+(char)-1));
        assertFalse(classOnce(Syntax.OCTET, ""+(char)256));
    }


    @Test
    public void textMatching() {
        for (int i=0; i<32; i++) {
            String s = ""+(char)i;
            assertFalse("Matched char: "+i, classOnce(Syntax.TEXT, s));
        }
        for (int i=32; i<127; i++) {
            String s = ""+(char)i;
            assertTrue("Unmatched char: "+i, classOnce(Syntax.TEXT, s));
        }
        assertFalse(classOnce(Syntax.TEXT, ""+(char)127));
        for (int i=128; i<256; i++) {
            String s = ""+(char)i;
            assertTrue("Unmatched char: "+i, classOnce(Syntax.TEXT, s));
        }

        assertTrue(pattern(Syntax.TEXT, "a\r\n\tb"));

        assertFalse(classOnce(Syntax.TEXT, ""+(char)-1));
        assertFalse(classOnce(Syntax.TEXT, ""+(char)256));
    }


    @Test
    public void charMatching() {
        for (int i=0; i<128; i++) {
            String s = ""+(char)i;
            assertTrue(classOnce(Syntax.CHAR, s));
        }

        assertFalse(classOnce(Syntax.CHAR, ""+(char)-1));
        assertFalse(classOnce(Syntax.CHAR, ""+(char)128));
    }


    @Test
    public void alphaMatching() {
        assertTrue(classOnce(Syntax.ALPHA, "A"));
        assertTrue(classOnce(Syntax.ALPHA, "B"));
        assertTrue(classOnce(Syntax.ALPHA, "C"));
        assertTrue(classOnce(Syntax.ALPHA, "D"));
        assertTrue(classOnce(Syntax.ALPHA, "E"));
        assertTrue(classOnce(Syntax.ALPHA, "F"));
        assertTrue(classOnce(Syntax.ALPHA, "G"));
        assertTrue(classOnce(Syntax.ALPHA, "H"));
        assertTrue(classOnce(Syntax.ALPHA, "I"));
        assertTrue(classOnce(Syntax.ALPHA, "J"));
        assertTrue(classOnce(Syntax.ALPHA, "K"));
        assertTrue(classOnce(Syntax.ALPHA, "L"));
        assertTrue(classOnce(Syntax.ALPHA, "M"));
        assertTrue(classOnce(Syntax.ALPHA, "N"));
        assertTrue(classOnce(Syntax.ALPHA, "O"));
        assertTrue(classOnce(Syntax.ALPHA, "P"));
        assertTrue(classOnce(Syntax.ALPHA, "Q"));
        assertTrue(classOnce(Syntax.ALPHA, "R"));
        assertTrue(classOnce(Syntax.ALPHA, "S"));
        assertTrue(classOnce(Syntax.ALPHA, "T"));
        assertTrue(classOnce(Syntax.ALPHA, "U"));
        assertTrue(classOnce(Syntax.ALPHA, "V"));
        assertTrue(classOnce(Syntax.ALPHA, "W"));
        assertTrue(classOnce(Syntax.ALPHA, "X"));
        assertTrue(classOnce(Syntax.ALPHA, "Y"));
        assertTrue(classOnce(Syntax.ALPHA, "Z"));
        assertTrue(classOnce(Syntax.ALPHA, "a"));
        assertTrue(classOnce(Syntax.ALPHA, "b"));
        assertTrue(classOnce(Syntax.ALPHA, "c"));
        assertTrue(classOnce(Syntax.ALPHA, "d"));
        assertTrue(classOnce(Syntax.ALPHA, "e"));
        assertTrue(classOnce(Syntax.ALPHA, "f"));
        assertTrue(classOnce(Syntax.ALPHA, "g"));
        assertTrue(classOnce(Syntax.ALPHA, "h"));
        assertTrue(classOnce(Syntax.ALPHA, "i"));
        assertTrue(classOnce(Syntax.ALPHA, "j"));
        assertTrue(classOnce(Syntax.ALPHA, "k"));
        assertTrue(classOnce(Syntax.ALPHA, "l"));
        assertTrue(classOnce(Syntax.ALPHA, "m"));
        assertTrue(classOnce(Syntax.ALPHA, "n"));
        assertTrue(classOnce(Syntax.ALPHA, "o"));
        assertTrue(classOnce(Syntax.ALPHA, "p"));
        assertTrue(classOnce(Syntax.ALPHA, "q"));
        assertTrue(classOnce(Syntax.ALPHA, "r"));
        assertTrue(classOnce(Syntax.ALPHA, "s"));
        assertTrue(classOnce(Syntax.ALPHA, "t"));
        assertTrue(classOnce(Syntax.ALPHA, "u"));
        assertTrue(classOnce(Syntax.ALPHA, "v"));
        assertTrue(classOnce(Syntax.ALPHA, "w"));
        assertTrue(classOnce(Syntax.ALPHA, "x"));
        assertTrue(classOnce(Syntax.ALPHA, "y"));
        assertTrue(classOnce(Syntax.ALPHA, "z"));
    }


    @Test
    public void upAlphaMatching() {
        assertTrue(classOnce(Syntax.UPALPHA, "A"));
        assertTrue(classOnce(Syntax.UPALPHA, "B"));
        assertTrue(classOnce(Syntax.UPALPHA, "C"));
        assertTrue(classOnce(Syntax.UPALPHA, "D"));
        assertTrue(classOnce(Syntax.UPALPHA, "E"));
        assertTrue(classOnce(Syntax.UPALPHA, "F"));
        assertTrue(classOnce(Syntax.UPALPHA, "G"));
        assertTrue(classOnce(Syntax.UPALPHA, "H"));
        assertTrue(classOnce(Syntax.UPALPHA, "I"));
        assertTrue(classOnce(Syntax.UPALPHA, "J"));
        assertTrue(classOnce(Syntax.UPALPHA, "K"));
        assertTrue(classOnce(Syntax.UPALPHA, "L"));
        assertTrue(classOnce(Syntax.UPALPHA, "M"));
        assertTrue(classOnce(Syntax.UPALPHA, "N"));
        assertTrue(classOnce(Syntax.UPALPHA, "O"));
        assertTrue(classOnce(Syntax.UPALPHA, "P"));
        assertTrue(classOnce(Syntax.UPALPHA, "Q"));
        assertTrue(classOnce(Syntax.UPALPHA, "R"));
        assertTrue(classOnce(Syntax.UPALPHA, "S"));
        assertTrue(classOnce(Syntax.UPALPHA, "T"));
        assertTrue(classOnce(Syntax.UPALPHA, "U"));
        assertTrue(classOnce(Syntax.UPALPHA, "V"));
        assertTrue(classOnce(Syntax.UPALPHA, "W"));
        assertTrue(classOnce(Syntax.UPALPHA, "X"));
        assertTrue(classOnce(Syntax.UPALPHA, "Y"));
        assertTrue(classOnce(Syntax.UPALPHA, "Z"));
    }


    @Test
    public void loAlphaMatching() {
        assertTrue(classOnce(Syntax.LOALPHA, "a"));
        assertTrue(classOnce(Syntax.LOALPHA, "b"));
        assertTrue(classOnce(Syntax.LOALPHA, "c"));
        assertTrue(classOnce(Syntax.LOALPHA, "d"));
        assertTrue(classOnce(Syntax.LOALPHA, "e"));
        assertTrue(classOnce(Syntax.LOALPHA, "f"));
        assertTrue(classOnce(Syntax.LOALPHA, "g"));
        assertTrue(classOnce(Syntax.LOALPHA, "h"));
        assertTrue(classOnce(Syntax.LOALPHA, "i"));
        assertTrue(classOnce(Syntax.LOALPHA, "j"));
        assertTrue(classOnce(Syntax.LOALPHA, "k"));
        assertTrue(classOnce(Syntax.LOALPHA, "l"));
        assertTrue(classOnce(Syntax.LOALPHA, "m"));
        assertTrue(classOnce(Syntax.LOALPHA, "n"));
        assertTrue(classOnce(Syntax.LOALPHA, "o"));
        assertTrue(classOnce(Syntax.LOALPHA, "p"));
        assertTrue(classOnce(Syntax.LOALPHA, "q"));
        assertTrue(classOnce(Syntax.LOALPHA, "r"));
        assertTrue(classOnce(Syntax.LOALPHA, "s"));
        assertTrue(classOnce(Syntax.LOALPHA, "t"));
        assertTrue(classOnce(Syntax.LOALPHA, "u"));
        assertTrue(classOnce(Syntax.LOALPHA, "v"));
        assertTrue(classOnce(Syntax.LOALPHA, "w"));
        assertTrue(classOnce(Syntax.LOALPHA, "x"));
        assertTrue(classOnce(Syntax.LOALPHA, "y"));
        assertTrue(classOnce(Syntax.LOALPHA, "z"));
    }


    @Test
    public void digitMatching() {
        assertTrue(classOnce(Syntax.DIGIT, "0"));
        assertTrue(classOnce(Syntax.DIGIT, "1"));
        assertTrue(classOnce(Syntax.DIGIT, "2"));
        assertTrue(classOnce(Syntax.DIGIT, "3"));
        assertTrue(classOnce(Syntax.DIGIT, "4"));
        assertTrue(classOnce(Syntax.DIGIT, "5"));
        assertTrue(classOnce(Syntax.DIGIT, "6"));
        assertTrue(classOnce(Syntax.DIGIT, "7"));
        assertTrue(classOnce(Syntax.DIGIT, "8"));
        assertTrue(classOnce(Syntax.DIGIT, "9"));
    }


    @Test
    public void ctlMatching() {
        assertTrue(classOnce(Syntax.CTL, "\0"));
        assertTrue(classOnce(Syntax.CTL, "\u0001"));
        assertTrue(classOnce(Syntax.CTL, "\u0002"));
        assertTrue(classOnce(Syntax.CTL, "\u0003"));
        assertTrue(classOnce(Syntax.CTL, "\u0004"));
        assertTrue(classOnce(Syntax.CTL, "\u0005"));
        assertTrue(classOnce(Syntax.CTL, "\u0006"));
        assertTrue(classOnce(Syntax.CTL, "\u0007"));
        assertTrue(classOnce(Syntax.CTL, "\b"));
        assertTrue(classOnce(Syntax.CTL, "\t"));
        assertTrue(classOnce(Syntax.CTL, "\n"));
        assertTrue(classOnce(Syntax.CTL, "\u000b"));
        assertTrue(classOnce(Syntax.CTL, "\f"));
        assertTrue(classOnce(Syntax.CTL, "\r"));
        assertTrue(classOnce(Syntax.CTL, "\u000e"));
        assertTrue(classOnce(Syntax.CTL, "\u000f"));
        assertTrue(classOnce(Syntax.CTL, "\u0010"));
        assertTrue(classOnce(Syntax.CTL, "\u0011"));
        assertTrue(classOnce(Syntax.CTL, "\u0012"));
        assertTrue(classOnce(Syntax.CTL, "\u0013"));
        assertTrue(classOnce(Syntax.CTL, "\u0014"));
        assertTrue(classOnce(Syntax.CTL, "\u0015"));
        assertTrue(classOnce(Syntax.CTL, "\u0016"));
        assertTrue(classOnce(Syntax.CTL, "\u0017"));
        assertTrue(classOnce(Syntax.CTL, "\u0018"));
        assertTrue(classOnce(Syntax.CTL, "\u0019"));
        assertTrue(classOnce(Syntax.CTL, "\u001a"));
        assertTrue(classOnce(Syntax.CTL, "\u001b"));
        assertTrue(classOnce(Syntax.CTL, "\u001c"));
        assertTrue(classOnce(Syntax.CTL, "\u001d"));
        assertTrue(classOnce(Syntax.CTL, "\u001e"));
        assertTrue(classOnce(Syntax.CTL, "\u001f"));
        assertTrue(classOnce(Syntax.CTL, "\u007f"));
    }


    @Test
    public void crMatching() {
        assertTrue(classOnce(Syntax.CR, "\r"));
    }


    @Test
    public void lfMatching() {
        assertTrue(classOnce(Syntax.LF, "\n"));
    }


    @Test
    public void spMatching() {
        assertTrue(classOnce(Syntax.SP, " "));
    }


    @Test
    public void htMatching() {
        assertTrue(classOnce(Syntax.HT, "\t"));
    }


    @Test
    public void quMatching() {
        assertTrue(classOnce(Syntax.QU, "\""));
    }


    @Test
    public void crlfMatching() {

        assertTrue(classOnce(Syntax.CRLF, "\r"));
        assertTrue(classOnce(Syntax.CRLF, "\n"));

        assertFalse(classOnce(Syntax.CRLF, "a"));
        assertFalse(classOnce(Syntax.CRLF, "A"));
        assertFalse(classOnce(Syntax.CRLF, "."));
        assertFalse(classOnce(Syntax.CRLF, "5"));
    }


    @Test
    public void lwsMatching() {
        assertTrue(classOnce(Syntax.LWS, "\r "));
        assertTrue(classOnce(Syntax.LWS, "\n\t"));
        assertTrue(classOnce(Syntax.LWS, "\r "));
        assertTrue(classOnce(Syntax.LWS, "\n\t"));

        assertTrue(classOnce(Syntax.LWS, "\r \t"));
        assertTrue(classOnce(Syntax.LWS, "\n\t "));
        assertTrue(classOnce(Syntax.LWS, "\r \t"));
        assertTrue(classOnce(Syntax.LWS, "\n\t "));
    }


    @Test
    public void separatorMatching() {
        assertTrue(classOnce(Syntax.SEPARATOR, "("));
        assertTrue(classOnce(Syntax.SEPARATOR, ")"));
        assertTrue(classOnce(Syntax.SEPARATOR, "<"));
        assertTrue(classOnce(Syntax.SEPARATOR, ">"));
        assertTrue(classOnce(Syntax.SEPARATOR, "@"));
        assertTrue(classOnce(Syntax.SEPARATOR, ","));
        assertTrue(classOnce(Syntax.SEPARATOR, ";"));
        assertTrue(classOnce(Syntax.SEPARATOR, ":"));
        assertTrue(classOnce(Syntax.SEPARATOR, "\\"));
        assertTrue(classOnce(Syntax.SEPARATOR, "\""));
        assertTrue(classOnce(Syntax.SEPARATOR, "/"));
        assertTrue(classOnce(Syntax.SEPARATOR, "["));
        assertTrue(classOnce(Syntax.SEPARATOR, "]"));
        assertTrue(classOnce(Syntax.SEPARATOR, "?"));
        assertTrue(classOnce(Syntax.SEPARATOR, "="));
        assertTrue(classOnce(Syntax.SEPARATOR, "{"));
        assertTrue(classOnce(Syntax.SEPARATOR, "}"));
        assertTrue(classOnce(Syntax.SEPARATOR, " "));
        assertTrue(classOnce(Syntax.SEPARATOR, "\t"));
    }


    @Test
    public void hexMatching() {

        assertTrue(classOnce(Syntax.HEX, "A"));
        assertTrue(classOnce(Syntax.HEX, "a"));
        assertTrue(classOnce(Syntax.HEX, "F"));
        assertTrue(classOnce(Syntax.HEX, "f"));
        assertTrue(classOnce(Syntax.HEX, "0"));
        assertTrue(classOnce(Syntax.HEX, "9"));
        assertTrue(classOnceOrMore(Syntax.HEX, "abcedfABCDEF0123456789"));

        assertFalse(classOnce(Syntax.HEX, "G"));
        assertFalse(classOnce(Syntax.HEX, "g"));
        assertFalse(classOnce(Syntax.HEX, "Z"));
        assertFalse(classOnce(Syntax.HEX, "z"));
        assertFalse(classOnce(Syntax.HEX, "."));
        assertFalse(classOnce(Syntax.HEX, "?"));
    }


    @Test
    public void quotedPair() {
        assertTrue(pattern(Syntax.QUOTED_PAIR, "\\n"));
        assertTrue(pattern(Syntax.QUOTED_PAIR, "\\\\"));
    }


    private boolean pattern(final String regex, final String string) {
        return string.matches(regex);
    }


    private boolean classOnce(final String regex, final String string) {
        return string.matches("["+regex+"]+");
    }


    private boolean classOnceOrMore(final String regex, final String string) {
        return string.matches("["+regex+"]*");
    }
}
