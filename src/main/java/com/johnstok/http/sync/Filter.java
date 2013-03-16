/*-----------------------------------------------------------------------------
 * Copyright © 2013 Keith Webster Johnston.
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
 * along with http. If not, see <http://www.gnu.org/licenses/>.
 *---------------------------------------------------------------------------*/
package com.johnstok.http.sync;

import com.johnstok.http.engine.Utils;


/**
 * A filter performs an action before or after delegating to another handler.
 *
 * <p>An implementation will typically call {@link #handle(Request, Response)}
 * on the delegate handler during its own {@code handle} implementation.
 *
 * @author Keith Webster Johnston.
 */
public abstract class Filter
    implements
        Handler {

    private final Handler _delegate;


    /**
     * Constructor.
     *
     * @param delegate The handler to call next.
     */
    public Filter(final Handler delegate) {
        _delegate = Utils.checkNotNull(delegate);
    }


    /**
     * Accessor.
     *
     * @return Returns the delegate.
     */
    protected final Handler getDelegate() {
        return _delegate;
    }
}
