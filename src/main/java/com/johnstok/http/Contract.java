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
 * Tests objects to confirm they meet specified requirements.
 *
 * @author Keith Webster Johnston.
 */
public class Contract {

    /**
     * Constructor.
     */
    private Contract() { super(); }


    /**
     * Create a contract for input parameters.
     *
     * @return A new contract.
     */
    public static Contract require() { return new Contract(); }


    /**
     * Test that a string matches a given regular expression.
     *
     * @param regex The regular expression.
     * @param value The string to test.
     *
     * @return Returns the 'value' parameter.
     */
    public String matches(final String regex, final String value) {
        if (!value.matches(regex)) {
            throw new IllegalArgumentException(
                "String '"
                + value
                + "' does not match regular expression /"
                + regex
                + "/.");
        }
        return value;
    }


}
