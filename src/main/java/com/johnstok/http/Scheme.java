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
 * URI schemes supported by this server.
 *
 * @author Keith Webster Johnston.
 */
public enum Scheme {

    /** HTTP : Scheme. */
    http(false),

    /** HTTPS : Scheme. */
    https(true);

    private final boolean _confidential;


    /**
     * Constructor.
     *
     * @param confidential Is confidential data exchange possible.
     */
    private Scheme(final boolean confidential) {
        _confidential = confidential;
    }


    /**
     * Query if this scheme supports confidential exchange of data.
     *
     * @return True if confidential exchange is possible; false otherwise.
     */
    public boolean isConfidential() {
        return _confidential;
    }
}
