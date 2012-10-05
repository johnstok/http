/*-----------------------------------------------------------------------------
 * Copyright © 2012 Keith Webster Johnston.
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
 * along with http.  If not, see <http://www.gnu.org/licenses/>.
 *---------------------------------------------------------------------------*/
package com.johnstok.http;

import static org.junit.Assert.*;
import java.util.regex.Pattern;
import org.junit.Test;


/**
 * Tests for the {@link Authority} class.
 *
 * @author Keith Webster Johnston.
 */
public class AuthorityTest {

    @Test
    public void test() {

        // AUTHORITY
        assertTrue(matches(Authority.AUTHORITY, "un:pw@example.com:8080"));
        assertTrue(matches(Authority.AUTHORITY, "@example.com:8080"));
        assertTrue(matches(Authority.AUTHORITY, "example.com:8080"));
        assertTrue(matches(Authority.AUTHORITY, "$%00=")); //?!
        assertTrue(matches(Authority.AUTHORITY, "@com.:")); //?!

        // HOSTPORT
        assertTrue(matches(Authority.HOSTPORT, "www.example.com"));
        assertTrue(matches(Authority.HOSTPORT, "255.255.255.255"));
        assertTrue(matches(Authority.HOSTPORT, "www.example.com:80"));
        assertTrue(matches(Authority.HOSTPORT, "255.255.255.255:80"));
        assertTrue(matches(Authority.HOSTPORT, "www.example.com:"));
        assertTrue(matches(Authority.HOSTPORT, "255.255.255.255:"));
        assertTrue(matches(Authority.HOSTPORT, "com.:"));
        assertTrue(matches(Authority.HOSTPORT, "com"));

        // HOST
        assertTrue(matches(Authority.HOST, "www.example.com"));
        assertTrue(matches(Authority.HOST, "255.255.255.255"));

        // HOSTNAME
        assertTrue(matches(Authority.HOSTNAME, "com"));
        assertTrue(matches(Authority.HOSTNAME, "com."));
        assertTrue(matches(Authority.HOSTNAME, "example.com"));
        assertTrue(matches(Authority.HOSTNAME, "example.com."));
        assertTrue(matches(Authority.HOSTNAME, "www.example.com"));
        assertTrue(matches(Authority.HOSTNAME, "www.example.com."));
        assertTrue(matches(Authority.HOSTNAME, "a.b.c.example.com"));
        assertTrue(matches(Authority.HOSTNAME, "a.b.c.example.com."));

        // PORT
        assertTrue(matches(Authority.PORT, ""));
        assertTrue(matches(Authority.PORT, "1"));
        assertTrue(matches(Authority.PORT, "80"));
        assertTrue(matches(Authority.PORT, "8080"));
        assertFalse(matches(Authority.PORT, "a"));
        assertFalse(matches(Authority.PORT, "%"));

        // IP4_ADDR
        assertTrue(matches(Authority.IP4_ADDR, "1.1.1.1"));
        assertTrue(matches(Authority.IP4_ADDR, "127.0.0.1"));
        assertTrue(matches(Authority.IP4_ADDR, "255.255.255.255"));
        assertFalse(matches(Authority.IP4_ADDR, ""));
        assertFalse(matches(Authority.IP4_ADDR, "abcd"));
        assertFalse(matches(Authority.IP4_ADDR, "a.b.c.d"));

        // DOMAIN_LABEL
        assertTrue(matches(Authority.DOMAIN_LABEL, "a"));
        assertTrue(matches(Authority.DOMAIN_LABEL, "aa"));
        assertTrue(matches(Authority.DOMAIN_LABEL, "aaa"));
        assertTrue(matches(Authority.DOMAIN_LABEL, "a-a"));
        assertTrue(matches(Authority.DOMAIN_LABEL, "a---a"));
        assertTrue(matches(Authority.DOMAIN_LABEL, "a-b-a"));
        assertTrue(matches(Authority.DOMAIN_LABEL, "a--b--a"));
        assertFalse(matches(Authority.DOMAIN_LABEL, "-aa"));
        assertFalse(matches(Authority.DOMAIN_LABEL, "aa-"));
        assertFalse(matches(Authority.DOMAIN_LABEL, "-a-"));
        assertFalse(matches(Authority.DOMAIN_LABEL, "-aa-"));
        assertFalse(matches(Authority.DOMAIN_LABEL, "-a-a-"));
        assertTrue(matches(Authority.DOMAIN_LABEL, "1"));
        assertTrue(matches(Authority.DOMAIN_LABEL, "1a"));
        assertTrue(matches(Authority.DOMAIN_LABEL, "1-a"));
        assertTrue(matches(Authority.DOMAIN_LABEL, "a1"));
        assertTrue(matches(Authority.DOMAIN_LABEL, "a-1"));
        // TODO: More combinations?

        // TOP_LABEL
        assertTrue(matches(Authority.TOP_LABEL, "a"));
        assertTrue(matches(Authority.TOP_LABEL, "aa"));
        assertTrue(matches(Authority.TOP_LABEL, "aaa"));
        assertTrue(matches(Authority.TOP_LABEL, "a-a"));
        assertTrue(matches(Authority.TOP_LABEL, "a---a"));
        assertTrue(matches(Authority.TOP_LABEL, "a-b-a"));
        assertTrue(matches(Authority.TOP_LABEL, "a--b--a"));
        assertFalse(matches(Authority.TOP_LABEL, "-aa"));
        assertFalse(matches(Authority.TOP_LABEL, "aa-"));
        assertFalse(matches(Authority.TOP_LABEL, "-a-"));
        assertFalse(matches(Authority.TOP_LABEL, "-aa-"));
        assertFalse(matches(Authority.TOP_LABEL, "-a-a-"));
        assertFalse(matches(Authority.TOP_LABEL, "1"));
        assertFalse(matches(Authority.TOP_LABEL, "1a"));
        assertFalse(matches(Authority.TOP_LABEL, "1-a"));
        assertTrue(matches(Authority.TOP_LABEL, "a1"));
        assertTrue(matches(Authority.TOP_LABEL, "a-1"));

        // ESCAPED
        assertTrue(matches(Authority.ESCAPED, "%01"));
        assertTrue(matches(Authority.ESCAPED, "%aa"));
        assertTrue(matches(Authority.ESCAPED, "%BB"));
        assertTrue(matches(Authority.ESCAPED, "%c3"));
        assertFalse(matches(Authority.ESCAPED, "%4"));
        assertFalse(matches(Authority.ESCAPED, ""));

        // REG_NAME
        assertTrue(matches(Authority.REG_NAME, "%01"));
        assertTrue(matches(Authority.REG_NAME, "%01%aa"));
        assertTrue(matches(Authority.REG_NAME, "a1Z"));
        assertTrue(matches(Authority.REG_NAME, "a1Z%bbZ9a"));
        assertTrue(matches(Authority.REG_NAME, "%43a1Z%bbZ9a"));
        assertTrue(matches(Authority.REG_NAME, "$"));
        assertTrue(matches(Authority.REG_NAME, ","));
        assertTrue(matches(Authority.REG_NAME, ";"));
        assertTrue(matches(Authority.REG_NAME, ":"));
        assertTrue(matches(Authority.REG_NAME, "@"));
        assertTrue(matches(Authority.REG_NAME, "&"));
        assertTrue(matches(Authority.REG_NAME, "="));
        assertTrue(matches(Authority.REG_NAME, "+"));
        assertFalse(matches(Authority.REG_NAME, "%4"));
        assertFalse(matches(Authority.REG_NAME, ""));
    }


    private boolean matches(final String regex, final String string) {
        return Pattern.matches(regex, string);
    }
}
