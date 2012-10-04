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
import org.junit.Test;


/**
 * Tests for the {@link Method} class.
 *
 * @author Keith Webster Johnston.
 */
public class MethodTest {


    @Test
    public void equalityIsCaseSensitive() {
        assertTrue(Method.parse("GET").equals(Method.parse("GET")));
        assertFalse(Method.parse("GET").equals(Method.parse("gET")));
    }


    @Test
    public void specifiedIsRecognized() {
        assertTrue(Method.GET.isRecognized());
    }


    @Test
    public void unspecifiedIsUnrecognized() {
        assertFalse(Method.parse("FOO").isRecognized());
    }
}
