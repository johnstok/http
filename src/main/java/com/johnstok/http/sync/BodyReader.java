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
package com.johnstok.http.sync;

import java.io.IOException;
import java.io.InputStream;
import com.johnstok.http.HttpException;


/**
 * API for reading the body of a request.
 *
 * @author Keith Webster Johnston.
 */
public interface BodyReader {


    /**
     * Read the body from the supplied input stream.
     *
     * @param inputStream The input stream to read from.
     */
    void read(InputStream inputStream) throws IOException, HttpException;
    // FIXME: Do we need to throw both these exceptions?
}
