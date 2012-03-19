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
 * Supported content encodings.
 *
 * @author Keith Webster Johnston.
 */
public final class ContentEncoding {

    /** IDENTITY : String. */
    public static final String IDENTITY = "identity";              //$NON-NLS-1$

    /** GZIP : String. */
    public static final String GZIP     = "gzip";                  //$NON-NLS-1$

    /** ANY : String. */
    public static final String ANY      = "*";                     //$NON-NLS-1$


    private ContentEncoding() { super(); }
}
