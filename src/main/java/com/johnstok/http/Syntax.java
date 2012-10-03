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




/**
 * Defines regex character classes for basic HTTP syntax.
 *
 * <pre>
   The following rules are used throughout this specification to
   describe basic parsing constructs. The US-ASCII coded character set
   is defined by ANSI X3.4-1986 [21].
 * </pre>
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="2.2")
public class Syntax {


/*  */

    public static final String OCTET   = "\\x00-\\xFF";
    public static final String CHAR    = "\\p{ASCII}";
    public static final String UPALPHA = "A-Z";
    public static final String LOALPHA = "a-z";
    public static final String ALPHA   = UPALPHA+LOALPHA;
    public static final String DIGIT   = "\\d";
    public static final String CTL     = "\\x00-\\x1F\\x7F";
    public static final String CR      = "\\x0D";
    public static final String LF      = "\\x0A";
    public static final String SP      = "\\x20";
    public static final String HT      = "\\x09";
    public static final String QU      = "\\x22";


    /**
     * <pre>
   HTTP/1.1 defines the sequence CR LF as the end-of-line marker for all
   protocol elements except the entity-body (see appendix 19.3 for
   tolerant applications). The end-of-line marker within an entity-body
   is defined by its associated media type, as described in section 3.7.
   
       CRLF           = CR LF
     * </pre>
     */
    public static final String CRLF = CR+LF;


    /**
     * <pre>
   HTTP/1.1 header field values can be folded onto multiple lines if the
   continuation line begins with a space or horizontal tab. All linear
   white space, including folding, has the same semantics as SP. A
   recipient MAY replace any linear white space with a single SP before
   interpreting the field value or forwarding the message downstream.

       LWS            = [CRLF] 1*( SP | HT )
     * </pre>
     */
    public static final String LWS = CRLF+"["+SP+HT+"]+";

    
    /**
     * <pre>
   The TEXT rule is only used for descriptive field contents and values
   that are not intended to be interpreted by the message parser. Words
   of *TEXT MAY contain characters from character sets other than ISO-
   8859-1 [22] only when encoded according to the rules of RFC 2047
   [14].

       TEXT           = &lt;any OCTET except CTLs,
                        but including LWS>

   A CRLF is allowed in the definition of TEXT only as part of a header
   field continuation. It is expected that the folding LWS will be
   replaced with a single SP before interpretation of the TEXT value.
     * </pre>
     */
    public static final String TEXT = "((?:["+OCTET+"&&[^"+CTL+"]])+|"+LWS+")*";


    /**
     * <pre>
   Hexadecimal numeric characters are used in several protocol elements.

       HEX            = "A" | "B" | "C" | "D" | "E" | "F"
                      | "a" | "b" | "c" | "d" | "e" | "f" | DIGIT
     * </pre>
     */
    public static final String HEX = "A-Fa-f\\d";


    /**
     * <pre>
   Many HTTP/1.1 header field values consist of words separated by LWS
   or special characters. These special characters MUST be in a quoted
   string to be used within a parameter value (as defined in section
   3.6).

       separators     = "(" | ")" | "<" | ">" | "@"
                      | "," | ";" | ":" | "\" | <">
                      | "/" | "[" | "]" | "?" | "="
                      | "{" | "}" | SP | HT
     * </pre>
     */
    public static final String SEPARATOR = "\\(\\)<>@,;\\:\\\\\"/\\[\\]\\?\\=\\{\\} \t";

    /**
     * <pre>
       token          = 1*&lt;any CHAR except CTLs or separators>
     * </pre>
     */
    public static final String TOKEN = CHAR+"&&[^"+SEPARATOR+"]&&[^"+CTL+"]";


    /**
     * <pre>
   The backslash character ("\") MAY be used as a single-character
   quoting mechanism only within quoted-string and comment constructs.

       quoted-pair    = "\" CHAR
     * </pre>
     */
    public static final String QUOTED_PAIR    = "\\\\["+CHAR+"]";


    /**
     * <pre>
       ctext          = &lt;any TEXT excluding "(" and ")">
     * </pre>
     */
    public static final String CTEXT = "((?:["+OCTET+"&&[^"+CTL+"\\(\\)]])+|"+LWS+")*";
    
    
    /**
     * <pre>
   Comments can be included in some HTTP header fields by surrounding
   the comment text with parentheses. Comments are only allowed in
   fields containing "comment" as part of their field value definition.
   In all other fields, parentheses are considered part of the field
   value.

       comment        = "(" *( ctext | quoted-pair | comment ) ")"
     * </pre>
     */
    public static final String COMMENT = "\\(("+CTEXT+"|"+QUOTED_PAIR+")*\\)"; // FIXME: Add recursive definition to allow nested comments.


    /**
     * <pre>
       qdtext         = &lt;any TEXT except &lt;"&gt;>
     * </pre>
     */
    public static final String QDTEXT = "((?:["+OCTET+"&&[^"+CTL+"\\\"]])+|"+LWS+")*";


    /**
     * <pre>
   A string of text is parsed as a single word if it is quoted using
   double-quote marks.

       quoted-string  = ( <"> *(qdtext | quoted-pair ) <"> )
     * </pre>
     */
    public static final String QUOTED_STRING  = "\"("+QDTEXT+"|"+QUOTED_PAIR+")*\"";
}
