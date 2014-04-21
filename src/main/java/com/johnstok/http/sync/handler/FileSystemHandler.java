/*-----------------------------------------------------------------------------
 * Copyright © 2014 Keith Webster Johnston.
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
package com.johnstok.http.sync.handler;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.Objects;

import com.johnstok.http.ETag;
import com.johnstok.http.Header;
import com.johnstok.http.RequestURI;
import com.johnstok.http.Status;
import com.johnstok.http.headers.DateHeader;
import com.johnstok.http.sync.Handler;
import com.johnstok.http.sync.Request;
import com.johnstok.http.sync.Response;
import com.johnstok.http.sync.writer.PathBodyWriter;


/**
 * A handler that serves resources from a file system.
 *
 * @author Keith Webster Johnston.
 */
public class FileSystemHandler
    implements
        Handler {

    private final FileSystem _fs;


    public FileSystemHandler(final FileSystem fs) {
        _fs = Objects.requireNonNull(fs);
    }


    /** {@inheritDoc} */
    @Override
    public void handle(final Request request,
                       final Response response) throws IOException {

        // TODO: Ideally we would call resolve() rather than string manip.
        final String path =
            RequestURI.parse(request.getRequestUri()).toUri().getRawPath();
        final String resourcePath = "/META-INF/resources"+path;
        final Path fsPath = _fs.getPath(resourcePath);

        if (!Files.exists(fsPath) || !Files.isReadable(fsPath)) {
            response.setStatus(Status.NOT_FOUND.getCode(), Status.NOT_FOUND.getReasonPhrase());

        } else {
            final FileTime ft = Files.getLastModifiedTime(fsPath);
            final long size = Files.size(fsPath);
            final String etag = ETag.eTag(size, ft.toMillis(), resourcePath);

            response.setHeader(
                Header.LAST_MODIFIED, DateHeader.format(new Date(ft.toMillis())));
            response.setHeader(Header.CONTENT_LENGTH, String.valueOf(size));
            if (null!=etag) {
                response.setHeader(
                    Header.E_TAG, new ETag(etag, false).toString());
            }

            new PathBodyWriter(fsPath).write(response.getBody());
        }
    }
}
