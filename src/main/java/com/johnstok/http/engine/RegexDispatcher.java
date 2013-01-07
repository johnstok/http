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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.johnstok.http.ClientHttpException;
import com.johnstok.http.HttpException;
import com.johnstok.http.ServerHttpException;
import com.johnstok.http.Status;
import com.johnstok.http.sync.Request;
import com.johnstok.http.sync.Response;


/**
 * {@link Dispatcher} implementation that selects a resource type by regex.
 *
 * This implementation matches patterns based on the order that they were bound.
 * Regular expressions match against the encoded, non-normalised path from the
 * request URI.
 *
 * @param <T> The type of configuration object passed to the resource.
 *
 * @author Keith Webster Johnston.
 */
public class RegexDispatcher<T>
    implements
        Dispatcher {

    private final Map<Pattern, Class<? extends Resource>> _bindings;
    private final T                                       _configuration;


    /**
     * Constructor.
     *
     * @param clazz The resource class that will handle requests.
     */
    public RegexDispatcher(final T configuration) {
        _bindings      = new LinkedHashMap<Pattern, Class<? extends Resource>>();
        _configuration = configuration; // FIXME: Check for NULL.
    }


    /** {@inheritDoc} */
    @Override
    public Resource dispatch(final Request request,
                             final Response response) throws HttpException {

            try {
                final String encodedPath = new URI(request.getRequestUri()).getRawPath();

                final Class<? extends Resource> clazz = selectResourceBinding(encodedPath);
                if (null==clazz) {
                    throw new ClientHttpException(Status.NOT_FOUND);
                }

                return constructResource(request, response, clazz);
            } catch (URISyntaxException e) {
                // FIXME: Either use request.getPath or use RequestURI.
                throw new RuntimeException(e);
            }
    }


    /**
     * Bind a resource type to a regular expression.
     *
     * @param regex        The matching regular expression.
     * @param resourceType The type of resource dispatched to.
     */
    public void bind(final String regex, final Class<? extends Resource> resourceType) {
        // FIXME: Check param's for null.
        _bindings.put(Pattern.compile(regex), resourceType);
    }


    private Resource constructResource(final Request request,
                                       final Response response,
                                       final Class<? extends Resource> clazz) {
        try {
            final Map<String, Object> context = new HashMap<String, Object>();
            return clazz.getConstructor(Map.class).newInstance(context);
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


    private Class<? extends Resource> selectResourceBinding(final String encodedPath) {
        for (final Map.Entry<Pattern, Class<? extends Resource>> binding : _bindings.entrySet()) {
            final Matcher m = binding.getKey().matcher(encodedPath);
            if (m.matches()) { return binding.getValue(); }
        }
        return null;
    }
}
