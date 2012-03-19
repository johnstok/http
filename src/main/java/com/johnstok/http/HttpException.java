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
 * Models a HTTP error.
 *
 * @author Keith Webster Johnston.
 */
public abstract class HttpException extends RuntimeException {

    private final Status _status;


    /**
     * Constructor.
     *
     * @param status The HTTP status for this error.
     * @param t      The cause of this exception.
     */
    public HttpException(final Status status, final Throwable t) {
        super(status.toString(), t);
        _status = status;
    }


    /**
     * Constructor.
     *
     * @param status The HTTP status for this error.
     */
    public HttpException(final Status status) {
        super(status.toString());
        _status = status;
    }


    /**
     * Accessor.
     *
     * @return Returns the status for this error.
     */
    public Status getStatus() {
        return _status;
    }
}
