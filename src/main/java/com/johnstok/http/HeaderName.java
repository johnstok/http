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


/**
 * API for serializing / deserializing headers.
 *
 * @author Keith Webster Johnston.
 */
public abstract class HeaderName<T> {

    public static enum Type {REQUEST, RESPONSE, ENTITY, GENERAL}

    // minOccurs: int
    // maxOccurs: int
    // allowed:  [request,response,entity,general]
    // TODO: Reify type?

    public abstract T parse(String headerValue);
    public abstract String write(T object);
//    public boolean isRequestHeader();
//    public boolean isResponseHeader();
//    public boolean isGeneralHeader();
//    public boolean isEntityHeader();
//    public int getMinOccurs();
//    public int getMaxOccurs();
}
