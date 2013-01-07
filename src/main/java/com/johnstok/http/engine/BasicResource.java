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

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.johnstok.http.ContentEncoding;
import com.johnstok.http.ETag;
import com.johnstok.http.Header;
import com.johnstok.http.LanguageTag;
import com.johnstok.http.MediaType;
import com.johnstok.http.Method;
import com.johnstok.http.ServerHttpException;
import com.johnstok.http.Status;


/**
 * Implements defaults for many of the {@link Resource} API methods.
 *
 * @author Keith Webster Johnston.
 */
public abstract class BasicResource<T>
    implements
        Resource {

    protected final T _context;


    /**
     * Constructor.
     *
     * @param context The server context.
     */
    public BasicResource(final T context) {
        _context = context;
    }


    /** {@inheritDoc} */
    @Override
    public boolean allowsPostToMissing() { return false; }


    /** {@inheritDoc} */
    @Override
    public Set<Method> getAllowedMethods() {
        return
            new HashSet<Method>(
                Arrays.asList(new Method[] {Method.GET, Method.HEAD}));
    }


    /** {@inheritDoc} */
    @Override
    public Set<Charset> getCharsetsProvided() { return null; }


    /** {@inheritDoc} */
    @Override
    public URI getCreatePath() { return null; }


    /** {@inheritDoc} */
    @Override
    public boolean isDeleted() { return true; }


    /** {@inheritDoc} */
    @Override
    public boolean delete() { return false; }


    /** {@inheritDoc} */
    @Override
    public Set<String> getEncodings() {
        return Collections.singleton(ContentEncoding.IDENTITY.toString());
    }


    /** {@inheritDoc} */
    @Override
    public Date getExpiryDate() { return null; }


    /** {@inheritDoc} */
    @Override
    public void finishRequest() { /* No Op */ }


    /** {@inheritDoc} */
    @Override
    public boolean isForbidden() { return false; }


    /** {@inheritDoc} */
    @Override
    public ETag generateEtag(final String base) { return null; }


    /** {@inheritDoc} */
    @Override
    public String authorize() { return null; }


    /** {@inheritDoc} */
    @Override
    public boolean isInConflict() { return false; }


    /** {@inheritDoc} */
    @Override
    public boolean isContentTypeKnown(final MediaType mediaType) {
        return true;
    }


    /** {@inheritDoc} */
    @Override
    public Set<LanguageTag> getLanguages() { return null; }


    /** {@inheritDoc} */
    @Override
    public Date getLastModifiedDate() { return null; }


    /** {@inheritDoc} */
    @Override
    public boolean isMalformed() { return false; }


    /** {@inheritDoc} */
    @Override
    public URI movedPermanentlyTo() { return null; }


    /** {@inheritDoc} */
    @Override
    public URI movedTemporarilyTo() { return null; }


    /** {@inheritDoc} */
    @Override
    public boolean hasMultipleChoices() { return false; }


    /** {@inheritDoc} */
    @Override
    public Map<String, List<String>> getOptionsResponseHeaders() {
        return new HashMap<String, List<String>>();
    }


    /** {@inheritDoc} */
    @Override
    public boolean isPostCreate() { return false; }


    /** {@inheritDoc} */
    @Override
    public boolean existedPreviously() { return false; }


    /** {@inheritDoc} */
    @Override
    public void processPost() {
        throw new ServerHttpException(Status.NOT_IMPLEMENTED);
    }


    /** {@inheritDoc} */
    @Override
    public boolean exists() { return true; }


    /** {@inheritDoc} */
    @Override
    public boolean isServiceAvailable() { return true; }


    /** {@inheritDoc} */
    @Override
    public boolean isUriTooLong() { return false; }


    /** {@inheritDoc} */
    @Override
    public boolean hasValidContentHeaders() { return true; }


    /** {@inheritDoc} */
    @Override
    public boolean isEntityLengthValid() { return true; }


    /** {@inheritDoc} */
    @Override
    public Header[] getVariances() { return new Header[] {}; }
}
