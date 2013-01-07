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
package com.johnstok.http.engine;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import com.johnstok.http.HttpException;
import com.johnstok.http.ServerHttpException;
import com.johnstok.http.Status;
import com.johnstok.http.sync.Request;
import com.johnstok.http.sync.Response;


/**
 * {@link Dispatcher} implementation that selects a single resource type.
 *
 * @param <T> The type of configuration object passed to the resource.
 *
 * @author Keith Webster Johnston.
 */
public class UnaryDispatcher<T>
    implements
        Dispatcher {


    private final Class<? extends Resource> _clazz;
    private final T                         _configuration;


    /**
     * Constructor.
     *
     * @param clazz The resource class that will handle requests.
     */
    public UnaryDispatcher(final Class<? extends Resource> clazz,
                           final T configuration) {
        _clazz = clazz; // FIXME: Check for NULL. Other checks too?
        _configuration = configuration; // FIXME: Check for NULL. Other checks too?
    }


    /** {@inheritDoc} */
    @Override
    public Resource dispatch(final Request request,
                             final Response response) throws HttpException {
        try {
            final Map<String, Object> context = new HashMap<String, Object>();
            return _clazz.getConstructor(_configuration.getClass(), Request.class, Response.class, Map.class)
                         .newInstance(_configuration, request, response, context);
        } catch (final InstantiationException e) {
            throw new ServerHttpException(Status.INTERNAL_SERVER_ERROR, e);
        } catch (final IllegalAccessException e) {
            throw new ServerHttpException(Status.INTERNAL_SERVER_ERROR, e);
        } catch (final InvocationTargetException e) {
            throw new ServerHttpException(Status.INTERNAL_SERVER_ERROR, e);
        } catch (final NoSuchMethodException e) {
            throw new ServerHttpException(Status.INTERNAL_SERVER_ERROR, e);
        } catch (final RuntimeException e) {
            throw new ServerHttpException(Status.INTERNAL_SERVER_ERROR, e);
        }
    }
}