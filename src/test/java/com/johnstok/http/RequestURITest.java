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
import java.net.URISyntaxException;
import org.junit.Test;


/**
 * Tests for the {@link RequestURI}.
 *
 * @author Keith Webster Johnston.
 */
public class RequestURITest {

    @Test
    public void registryAuthoritiesMatch() throws URISyntaxException {
        assertTrue(matches("http://%01$/foo"));
    }


    @Test
    public void missingPortIsEquivalentTo80() throws URISyntaxException {
        assertTrue(matches("http://abc.com:80", "http://abc.com"));
    }


    @Test
    public void emptyPortIsEquivalentTo80() throws URISyntaxException {
        assertTrue(matches("http://abc.com:80", "http://abc.com:"));
    }


    @Test
    public void hostnameIsCaseInsensitive() throws URISyntaxException {
        assertTrue(matches("http://abc.com", "http://aBc.com"));
        assertTrue(matches("http://abc.com", "http://aBc.cOm"));
        assertTrue(matches("http://abc.com", "http://abc.cOm"));
    }


    @Test
    public void schemeIsCaseInsensitive() throws URISyntaxException {
        assertTrue(matches("http://abc.com", "httP://abc.com"));
    }


    @Test
    public void emptyPathIsEquivalentToSlash() throws URISyntaxException {
        assertTrue(matches("http://abc.com/", "http://abc.com"));
        assertTrue(matches("http://abc.com", "http://abc.com/"));
    }


    @Test
    public void specExample() throws URISyntaxException {
        assertTrue(matches("http://abc.com:80/~smith/home.html", "http://ABC.com/%7Esmith/home.html"));
        assertTrue(matches("http://ABC.com/%7Esmith/home.html",  "http://ABC.com:/%7esmith/home.html"));
        assertTrue(matches("http://abc.com:80/~smith/home.html", "http://ABC.com:/%7esmith/home.html"));
    }


    @Test
    public void specUriTypes() throws URISyntaxException {
        assertTrue(matches("*"));                                       // No resource
        assertTrue(matches("/foo/bar"));                                // Absolute path
        assertTrue(matches("un:pw@www.example.com:80"));                // Authority
        assertTrue(matches("http://un:pw@www.example.com:80/meh?a=1")); // Absolute URI
    }


    private boolean matches(final String uriString) {
        return RequestURI.parse(uriString).matches(
            RequestURI.parse(uriString));
    }


    private boolean matches(final String uriString, final String thatUri) {
        return RequestURI.parse(uriString).matches(
            RequestURI.parse(thatUri));
    }
}
