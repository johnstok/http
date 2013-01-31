/*-----------------------------------------------------------------------------
 * Copyright © 2013 Keith Webster Johnston.
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
 * Configuration parameters for HTTP processing.
 *
 * @author Keith Webster Johnston.
 */
public interface Configuration {


    /**
     * The maximum length of a request body.
     *
     * @param size Size in bytes.
     */
    void getMaxRequestBodySize(int size);


    /**
     * The maximum length of the start line of a HTTP message.
     *
     * @param size Size in bytes.
     */
    void getMaxInitialLineSize(int size);


    /**
     * The maximum length of all headers in a message.
     *
     * @param size Size in bytes.
     */
    void getMaxHeaderSize(int size);


    /**
     * The maximum length of each chunk when using "chunked" encoding.
     *
     * @param size Size in bytes.
     */
    void getMaxChunkSize(int size);
}
