/*-----------------------------------------------------------------------------
 * Copyright © 2011 Keith Webster Johnston.
 * All rights reserved.
 *
 * Revision      $Rev$
 *---------------------------------------------------------------------------*/
package com.johnstok.http.engine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.johnstok.http.ContentCoding;
import com.johnstok.http.ETag;
import com.johnstok.http.Header;
import com.johnstok.http.HttpException;
import com.johnstok.http.LanguageTag;
import com.johnstok.http.MediaType;
import com.johnstok.http.Method;
import com.johnstok.http.ServerHttpException;
import com.johnstok.http.Status;
import com.johnstok.http.headers.DateHeader;
import com.johnstok.http.sync.Response;


/**
 * Tests for the {@link Engine} class.
 *
 * @author Keith Webster Johnston.
 */
public class EngineTest {


    /* MISSING TESTS
     * =============
     *
     *  + All date comparisons should occur at millisecond precision.
     *  + Multiple values for conditional request headers.
     *  + Comma separated values for conditional request headers.
     *
     * ===========*/


    public class ByteArrayBodyWriter
        implements
            BodyWriter {

        private final byte[] _body;


        /**
         * Constructor.
         *
         * @param body The body to write.
         */
        public ByteArrayBodyWriter(final byte[] body) {
            _body = body;
        }


        /** {@inheritDoc} */
        @Override
        public void write(final OutputStream outputStream) throws IOException {
            outputStream.write(_body);
        }
    }


    private static final class SimpleBodyReader
        implements
            BodyReader<byte[]> {

        private final ByteArrayOutputStream _baos;
        private final String                _createdPath;
        private final Resource              _resource;
        private final Response              _response;


        /**
         * Constructor.
         *
         * @param baos
         * @param createdPath
         * @param resource
         * @param response
         */
        SimpleBodyReader(final ByteArrayOutputStream baos,
                         final String createdPath,
                         final Resource resource,
                         final Response response) {
            _baos = baos;
            _createdPath = createdPath;
            _resource = resource;
            _response = response;
        }


        @Override
        public byte[] read(final InputStream inputStream) throws IOException, HttpException {
            if (!_resource.exists()) {
                _response.setHeader(Header.LOCATION, _createdPath);
            }

            Utils.copy(inputStream, _baos);

            return _baos.toByteArray();
        }
    }


    private static final class HelloWorldWriter
        implements
            BodyWriter {

        private final Charset _charset;


        /**
         * Constructor.
         *
         * @param charset
         */
        HelloWorldWriter(final Charset charset) {
            _charset = charset;
        }


        @Override public void write(final OutputStream outputStream) throws IOException {
            outputStream.write("Hello, world!".getBytes(_charset));
        }
    }


    private Engine       _engine;
    private TestResponse _response;
    private TestRequest  _request;
    private static final String TARGET_URI = "http://localhost/foo";
    static final Charset UTF_8 = Charset.forName("UTF-8");
    static final Charset UTF_16 = Charset.forName("UTF-16");


    @Test
    public void deleteResourceCanBeEnacted() throws IOException {

        // ARRANGE
        _request.setMethod(Method.DELETE);
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            @Override public Set<Method> getAllowedMethods() {
                return Collections.singleton(Method.DELETE);
            }

            @Override public boolean delete() {
                return true;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(
            Status.NO_CONTENT.getCode(), _response.getStatusCode());
    }


    @Test
    public void deleteResourceCannotBeEnacted() throws IOException {

        // ARRANGE
        _request.setMethod(Method.DELETE);
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            @Override public Set<Method> getAllowedMethods() {
                return Collections.singleton(Method.DELETE);
            }

            @Override public boolean isDeleted() {
                return false;
            }

            @Override public boolean delete() {
                return true;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.ACCEPTED.getCode(), _response.getStatusCode());
    }


    @Test
    public void disallowedMethodGivesNotImplemented() throws IOException {

        // ARRANGE
        _request.setMethod(Method.DELETE);
        final Resource resource = new TestResource(
            new HashMap<String, Object>());

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.METHOD_NOT_ALLOWED.getCode(), _response.getStatusCode());
    }


    @Test
    public void entityTooLarge() throws IOException {

        // ARRANGE
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            @Override public boolean isEntityLengthValid() {
                return false;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.REQUEST_ENTITY_TOO_LARGE.getCode(), _response.getStatusCode());
    }


    @Test
    public void forbidden() throws IOException {

        // ARRANGE
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            @Override public boolean isForbidden() {
                return true;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.FORBIDDEN.getCode(), _response.getStatusCode());
    }


    @Test
    public void deleteWithWildcardIfnonematchGivesPreconditionFailed() throws IOException { // I13, J18

        // ARRANGE
        _request.setHeader(Header.IF_NONE_MATCH, "*");
        _request.setMethod(Method.DELETE);
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            @Override public Set<Method> getAllowedMethods() {
                return Collections.singleton(Method.DELETE);
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.PRECONDITION_FAILED.getCode(), _response.getStatusCode());
    }



    @Test
    public void deleteWithMatchedIfnonematchGivesPreconditionFailed() throws IOException { // K13, J18

        // ARRANGE
        _request.setHeader(Header.IF_NONE_MATCH, "foo"); // FIXME: This value should be quoted - engine needs to parse as an entity-tag.
        _request.setMethod(Method.DELETE);
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            @Override public Set<Method> getAllowedMethods() {
                return Collections.singleton(Method.DELETE);
            }

            /** {@inheritDoc} */
            @Override
            public ETag generateEtag(final String base) { return ETag.parse("\"foo\""); }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.PRECONDITION_FAILED.getCode(), _response.getStatusCode());
    }



    @Test
    public void getWithMatchedIfnonematchGivesNotModified() throws IOException { // K13, J18

        // ARRANGE
        _request.setHeader(Header.IF_NONE_MATCH, "foo");
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            /** {@inheritDoc} */
            @Override
            public ETag generateEtag(final String base) { return ETag.parse("\"foo\""); }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.NOT_MODIFIED.getCode(), _response.getStatusCode());
    }


    @Test
    public void getWithLessRecentIfunmodifiedsinceGivesPreconditionFailed() throws IOException { // H12

        // ARRANGE
        _request.setHeader(Header.IF_UNMODIFIED_SINCE, DateHeader.format(new Date(0)));
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            /** {@inheritDoc} */
            @Override
            public Date getLastModifiedDate() { return new Date(); }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.PRECONDITION_FAILED.getCode(), _response.getStatusCode());
    }


    @Test
    public void getWithUnmatchedIfmatchGivesPreconditionFailed() throws IOException { // G11

        // ARRANGE
        _request.setHeader(Header.IF_MATCH, "foo");
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            /** {@inheritDoc} */
            @Override
            public ETag generateEtag(final String base) { return ETag.parse("\"bar\""); }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.PRECONDITION_FAILED.getCode(), _response.getStatusCode());
    }


    @Test
    public void getWithFutureIfmodifiedsinceReturnsOk() throws IOException { // L15

        // ARRANGE
        final Date d = new Date(Long.MAX_VALUE);
        _request.setHeader(Header.IF_MODIFIED_SINCE, DateHeader.format(d));
        final Charset UTF8 = Charset.forName("UTF-8");
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            /** {@inheritDoc} */
            @Override
            public Date getLastModifiedDate() { return new Date(0); }

            /** {@inheritDoc} */
            @Override
            public BodyWriter getWriter(final MediaType mediaType) {
                final BodyWriter bw = new BodyWriter() {
                    @Override public void write(final OutputStream outputStream) throws IOException {
                        outputStream.write("Hello, world!".getBytes(UTF8));
                    }
                };
                return bw;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
        Assert.assertEquals(
            "Hello, world!",
            _response.getBodyAsString(UTF8));
    }


    @Test
    public void getWithLessRecentIfmodifiedsinceReturnsOk() throws IOException { // L17

        // ARRANGE
        final Date d = new Date(0);
        _request.setHeader(Header.IF_MODIFIED_SINCE, DateHeader.format(d));
        final Charset UTF8 = Charset.forName("UTF-8");
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            /** {@inheritDoc} */
            @Override
            public Date getLastModifiedDate() { return new Date(2000); }

            /** {@inheritDoc} */
            @Override
            public BodyWriter getWriter(final MediaType mediaType) {
                final BodyWriter bw = new BodyWriter() {
                    @Override public void write(final OutputStream outputStream) throws IOException {
                        outputStream.write("Hello, world!".getBytes(UTF8));
                    }
                };
                return bw;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
        Assert.assertEquals(
            "Hello, world!",
            _response.getBodyAsString(UTF8));
    }


    @Test
    public void getWithSameIfmodifiedsinceReturnsNotModified() throws IOException { // L17

        // ARRANGE
        final Date d = new Date(1000);
        _request.setHeader(Header.IF_MODIFIED_SINCE, DateHeader.format(d));
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            /** {@inheritDoc} */
            @Override
            public Date getLastModifiedDate() { return d; }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.NOT_MODIFIED.getCode(), _response.getStatusCode());
    }


    @Test
    public void getWithMoreRecentIfmodifiedsinceReturnsNotModified() throws IOException { // L17

        // ARRANGE
        final Date d = new Date(2000);
        _request.setHeader(Header.IF_MODIFIED_SINCE, DateHeader.format(d));
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            /** {@inheritDoc} */
            @Override
            public Date getLastModifiedDate() { return new Date(0); }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.NOT_MODIFIED.getCode(), _response.getStatusCode());
    }


    @Test
    public void getWithMoreRecentIfunmodifiedsinceReturnsOk() throws IOException { // H12

        // ARRANGE
        final Date d = new Date();
        _request.setHeader(Header.IF_UNMODIFIED_SINCE, DateHeader.format(d));
        final Charset UTF8 = Charset.forName("UTF-8");
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            /** {@inheritDoc} */
            @Override
            public Date getLastModifiedDate() { return new Date(0); }

            /** {@inheritDoc} */
            @Override
            public BodyWriter getWriter(final MediaType mediaType) {
                final BodyWriter bw = new BodyWriter() {
                    @Override public void write(final OutputStream outputStream) throws IOException {
                        outputStream.write("Hello, world!".getBytes(UTF8));
                    }
                };
                return bw;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
        Assert.assertEquals(
            "Hello, world!",
            _response.getBodyAsString(UTF8));
    }


    @Test
    public void getWithSameIfunmodifiedsinceReturnsOk() throws IOException { // H12

        // ARRANGE
        final Date d = new Date(1000); // HTTP uses millisecond precision ;-)
        _request.setHeader(Header.IF_UNMODIFIED_SINCE, DateHeader.format(d));
        final Charset UTF8 = Charset.forName("UTF-8");
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            /** {@inheritDoc} */
            @Override
            public Date getLastModifiedDate() { return d; }

            /** {@inheritDoc} */
            @Override
            public BodyWriter getWriter(final MediaType mediaType) {
                final BodyWriter bw = new BodyWriter() {
                    @Override public void write(final OutputStream outputStream) throws IOException {
                        outputStream.write("Hello, world!".getBytes(UTF8));
                    }
                };
                return bw;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
        Assert.assertEquals(
            "Hello, world!",
            _response.getBodyAsString(UTF8));
    }


    @Test
    public void getWithInvalidIfmodifiedsinceReturnsOk() throws IOException { // L14

        // ARRANGE
        _request.setHeader(Header.IF_MODIFIED_SINCE, "foo");
        final Charset UTF8 = Charset.forName("UTF-8");
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            /** {@inheritDoc} */
            @Override
            public BodyWriter getWriter(final MediaType mediaType) {
                final BodyWriter bw = new BodyWriter() {
                    @Override public void write(final OutputStream outputStream) throws IOException {
                        outputStream.write("Hello, world!".getBytes(UTF8));
                    }
                };
                return bw;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
        Assert.assertEquals(
            "Hello, world!",
            _response.getBodyAsString(UTF8));
    }


    @Test
    public void getWithInvalidIfunmodifiedsinceReturnsOk() throws IOException { // H11

        // ARRANGE
        _request.setHeader(Header.IF_UNMODIFIED_SINCE, "foo");
        final Charset UTF8 = Charset.forName("UTF-8");
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            /** {@inheritDoc} */
            @Override
            public BodyWriter getWriter(final MediaType mediaType) {
                final BodyWriter bw = new BodyWriter() {
                    @Override public void write(final OutputStream outputStream) throws IOException {
                        outputStream.write("Hello, world!".getBytes(UTF8));
                    }
                };
                return bw;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
        Assert.assertEquals(
            "Hello, world!",
            _response.getBodyAsString(UTF8));
    }


    @Test
    public void getWithMatchedIfmatchReturnsOk() throws IOException { // G11

        // ARRANGE
        _request.setHeader(Header.IF_MATCH, "foo");
        final Charset UTF8 = Charset.forName("UTF-8");
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            /** {@inheritDoc} */
            @Override
            public ETag generateEtag(final String base) { return ETag.parse("\"foo\""); }

            /** {@inheritDoc} */
            @Override
            public BodyWriter getWriter(final MediaType mediaType) {
                final BodyWriter bw = new BodyWriter() {
                    @Override public void write(final OutputStream outputStream) throws IOException {
                        outputStream.write("Hello, world!".getBytes(UTF8));
                    }
                };
                return bw;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
        Assert.assertEquals(
            "Hello, world!",
            _response.getBodyAsString(UTF8));
    }


    @Test
    public void getWithWildcardIfmatchReturnsOk() throws IOException { // G09

        // ARRANGE
        _request.setHeader(Header.IF_MATCH, "*");
        final Charset UTF8 = Charset.forName("UTF-8");
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            /** {@inheritDoc} */
            @Override
            public ETag generateEtag(final String base) { return ETag.parse("\"bar\""); }

            /** {@inheritDoc} */
            @Override
            public BodyWriter getWriter(final MediaType mediaType) {
                final BodyWriter bw = new BodyWriter() {
                    @Override public void write(final OutputStream outputStream) throws IOException {
                        outputStream.write("Hello, world!".getBytes(UTF8));
                    }
                };
                return bw;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
        Assert.assertEquals(
            "Hello, world!",
            _response.getBodyAsString(UTF8));
    }


    @Test
    public void getForNonExistentResourceGivesPreconditionFailed() throws IOException {

        // ARRANGE
        _request.setHeader(Header.IF_MATCH, "*");
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            @Override public boolean exists() {
                return false;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.PRECONDITION_FAILED.getCode(), _response.getStatusCode());
    }


    @Test
    public void getForNonExistentResourceGivesGone() throws IOException {

        // ARRANGE
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            @Override public boolean existedPreviously() {
                return true;
            }

            @Override public boolean exists() {
                return false;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.GONE.getCode(), _response.getStatusCode());
    }


    @Test
    public void getForNonExistentResourceGivesMovedPermanently() throws IOException {

        // ARRANGE
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            @Override public URI movedPermanentlyTo() {
                try {
                    return new URI(TARGET_URI);
                } catch (final URISyntaxException e) {
                    throw new ServerHttpException(
                        Status.INTERNAL_SERVER_ERROR, e);
                }
            }

            @Override public boolean existedPreviously() {
                return true;
            }

            @Override public boolean exists() {
                return false;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.MOVED_PERMANENTLY.getCode(), _response.getStatusCode());
        Assert.assertEquals(TARGET_URI, _response.getHeader(Header.LOCATION));
    }


    @Test
    public void getForNonExistentResourceGivesMovedTemporarily() throws IOException {

        // ARRANGE
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            @Override public URI movedTemporarilyTo() {
                try {
                    return new URI(TARGET_URI);
                } catch (final URISyntaxException e) {
                    throw new ServerHttpException(
                        Status.INTERNAL_SERVER_ERROR, e);
                }
            }

            @Override public boolean existedPreviously() {
                return true;
            }

            @Override public boolean exists() {
                return false;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.TEMPORARY_REDIRECT.getCode(), _response.getStatusCode());
        Assert.assertEquals(TARGET_URI, _response.getHeader(Header.LOCATION));
    }


    @Test
    public void getForNonExistentResourceGivesNotFound() throws IOException {

        // ARRANGE
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            /** {@inheritDoc} */
            @Override
            public boolean exists() {
                return false;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.NOT_FOUND.getCode(), _response.getStatusCode());
    }


    @Test
    public void getResourceCanReturnOk() throws IOException {

        // ACT
        final Resource resource =
            new TestResource(
                new HashMap<String, Object>()) {

                @Override
                public BodyWriter getWriter(final MediaType mediaType) {
                    return new HelloWorldWriter(UTF_8);
                }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
        Assert.assertNull(_response.getHeader(Header.CONTENT_LANGUAGE));
        Assert.assertNull(_response.getHeader(Header.CONTENT_ENCODING));
        Assert.assertNull(_response.getHeader(Header.CONTENT_TYPE));
        Assert.assertEquals(
            "Hello, world!",
            _response.getBodyAsString(UTF_8));
    }


    @Test
    public void requestConnegWithoutAcceptSelectsDefaults() throws IOException {

        // ACT
        final Resource resource =
            new TestResource(
                new HashMap<String, Object>()) {

                @Override
                public Set<MediaType> getContentTypesProvided() {
                    return Collections.singleton(MediaType.JSON);
                }

                @Override
                public Set<LanguageTag> getLanguages() {
                    return Collections.singleton(new LanguageTag("da"));
                }

                @Override
                public Set<String> getEncodings() {
                    return Collections.singleton(ContentCoding.GZIP.toString());
                }

                @Override
                public Set<Charset> getCharsetsProvided() {
                    return Collections.singleton(UTF_16);
                }

                @Override
                public BodyWriter getWriter(final MediaType mediaType) {
                    return new HelloWorldWriter(UTF_8);
                }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
        Assert.assertEquals("da", _response.getHeader(Header.CONTENT_LANGUAGE));
        Assert.assertEquals(ContentCoding.GZIP.toString(), _response.getHeader(Header.CONTENT_ENCODING));
        Assert.assertEquals(MediaType.JSON.toString()+"; charset="+UTF_16, _response.getHeader(Header.CONTENT_TYPE));
        Assert.assertEquals(
            "Hello, world!",
            _response.getBodyAsString(UTF_8));
    }


    @Test
    public void okWithConnegReturnsLanguage() throws IOException {

        // ACT
        _request.setHeader(Header.ACCEPT_LANGUAGE, "en");
        final Resource resource =
            new TestResource(
                new HashMap<String, Object>()) {

                @Override
                public Set<MediaType> getContentTypesProvided() {
                    return Collections.singleton(MediaType.ANY);
                }

                @Override
                public BodyWriter getWriter(final MediaType mediaType) {
                    return new HelloWorldWriter(UTF_8);
                }

                @Override
                public Set<LanguageTag> getLanguages() {
                    return Collections.singleton(new LanguageTag("en"));
                }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
        Assert.assertEquals("en", _response.getHeader(Header.CONTENT_LANGUAGE));
        Assert.assertEquals(
            "Hello, world!",
            _response.getBodyAsString(UTF_8));
    }

    @Test
    public void acceptWithUnknownLanguageGivesNoHeader() throws IOException {

        // ACT
        _request.setHeader(Header.ACCEPT_LANGUAGE, "en");
        final Resource resource =
            new TestResource(
                new HashMap<String, Object>()) {

            @Override
            public Set<MediaType> getContentTypesProvided() {
                return Collections.singleton(MediaType.ANY);
            }

            @Override
            public BodyWriter getWriter(final MediaType mediaType) {
                return new HelloWorldWriter(UTF_8);
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
        Assert.assertNull(_response.getHeader(Header.CONTENT_LANGUAGE));
        Assert.assertEquals(
            "Hello, world!",
            _response.getBodyAsString(UTF_8));
    }


    @Test
    public void okWithConnegReturnsEncoding() throws IOException {

        // ACT
        _request.setHeader(
            Header.ACCEPT_ENCODING, ContentCoding.GZIP.toString());
        final Resource resource =
            new TestResource(
                new HashMap<String, Object>()) {

                @Override
                public Set<MediaType> getContentTypesProvided() {
                    return Collections.singleton(MediaType.ANY);
                }

                @Override
                public BodyWriter getWriter(final MediaType mediaType) {
                    return new HelloWorldWriter(UTF_8);
                }

                @Override
                public Set<String> getEncodings() {
                    return Collections.singleton(ContentCoding.GZIP.toString());
                }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
        Assert.assertEquals(
            ContentCoding.GZIP.toString(), _response.getHeader(Header.CONTENT_ENCODING));
        Assert.assertEquals(
            "Hello, world!",
            _response.getBodyAsString(UTF_8));
    }


    @Test
    public void acceptWithUnknownEncodingGivesNoHeader() throws IOException {

        // ACT
        _request.setHeader(Header.ACCEPT_ENCODING, ContentCoding.GZIP.toString());
        final Resource resource =
            new TestResource(
                new HashMap<String, Object>()) {

            @Override
            public Set<MediaType> getContentTypesProvided() {
                return Collections.singleton(MediaType.ANY);
            }

            @Override
            public BodyWriter getWriter(final MediaType mediaType) {
                return new HelloWorldWriter(UTF_8);
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
        Assert.assertNull(_response.getHeader(Header.CONTENT_ENCODING));
        Assert.assertEquals(
            "Hello, world!",
            _response.getBodyAsString(UTF_8));
    }


    @Test
    public void okWithConnegReturnsMediaType() throws IOException {

        // ACT
        _request.setHeader(Header.ACCEPT, MediaType.HTML.toString());
        final Resource resource =
            new TestResource(
                new HashMap<String, Object>()) {

                @Override
                public Set<MediaType> getContentTypesProvided() {
                    return Collections.singleton(MediaType.HTML);
                }

                @Override
                public BodyWriter getWriter(final MediaType mediaType) {
                    return new HelloWorldWriter(UTF_8);
                }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
        Assert.assertEquals(MediaType.HTML.toString(), _response.getHeader(Header.CONTENT_TYPE));
        Assert.assertEquals(
            "Hello, world!",
            _response.getBodyAsString(UTF_8));
    }


    @Test
    public void okWithConnegReturnsCharset() throws IOException {

        // ACT
        _request.setHeader(Header.ACCEPT_CHARSET, "utf-8");
        final Resource resource =
            new TestResource(
                new HashMap<String, Object>()) {

                @Override
                public Set<MediaType> getContentTypesProvided() {
                    return Collections.singleton(MediaType.HTML);
                }

                @Override
                public BodyWriter getWriter(final MediaType mediaType) {
                    return new HelloWorldWriter(UTF_8);
                }

                @Override
                public Set<Charset> getCharsetsProvided() {
                    return Collections.singleton(UTF_8);
                }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
        Assert.assertEquals(MediaType.HTML.toString()+"; charset="+UTF_8, _response.getHeader(Header.CONTENT_TYPE));
        Assert.assertEquals(
            "Hello, world!",
            _response.getBodyAsString(UTF_8));
    }


    @Test
    public void acceptWithUnknownCharsetGivesNoCharset() throws IOException {

        // ACT
        _request.setHeader(Header.ACCEPT_CHARSET, "utf-8");
        final Resource resource =
            new TestResource(
                new HashMap<String, Object>()) {

            @Override
            public Set<MediaType> getContentTypesProvided() {
                return Collections.singleton(MediaType.HTML);
            }

            @Override
            public BodyWriter getWriter(final MediaType mediaType) {
                return new HelloWorldWriter(UTF_8);
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
        Assert.assertEquals(MediaType.HTML.toString(), _response.getHeader(Header.CONTENT_TYPE));
        Assert.assertEquals(
            "Hello, world!",
            _response.getBodyAsString(UTF_8));
    }


    @Test
    public void malformed() throws IOException {

        // ARRANGE
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

                @Override public boolean isMalformed() {
                    return true;
                }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.BAD_REQUEST.getCode(), _response.getStatusCode());
    }


    @Test
    public void optionsRequested() throws IOException {

        // ARRANGE
        _request.setMethod(Method.OPTIONS);
        final Resource resource = new TestResource(
            new HashMap<String, Object>());

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
        Assert.assertEquals("0", _response.getHeader(Header.CONTENT_LENGTH));
        Assert.assertEquals(
            Utils.join(resource.getAllowedMethods(), ',').toString(),
            _response.getHeader(Header.ALLOW));
    }


    @Test
    public void postResourceCanReturnOk() throws Exception {

        // ARRANGE
        _request.setMethod(Method.POST);
        _response.setBody(new byte[] {0});
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

                @Override public Set<Method> getAllowedMethods() {
                    return Collections.singleton(Method.POST);
                }

                @Override public boolean isPostCreate() {
                    return false;
                }

                @Override public void processPost() {
                    // No Op.
                }

                /** {@inheritDoc} */
                @Override
                public BodyWriter getWriter(final MediaType mediaType) {
                    return new ByteArrayBodyWriter(new byte[] {0});
                }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.OK.getCode(), _response.getStatusCode());
    }


    @Test
    public void putForExistingResourceGivesNoContent() throws Exception {

        // ARRANGE
        final byte[] body = new byte[] {0};
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        _request.setMethod(Method.PUT);
        _request.setBody(body);
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            @Override public Set<Method> getAllowedMethods() {
                return Collections.singleton(Method.PUT);
            }

            @Override public boolean exists() {
                return true;
            }

            @Override
            public Map<MediaType, ? extends BodyReader<?>> getContentTypesAccepted() {
                return Collections.singletonMap(MediaType.ANY, new SimpleBodyReader(baos, null, this, _response));
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.NO_CONTENT.getCode(), _response.getStatusCode());
        Assert.assertTrue(Arrays.equals(body, baos.toByteArray()));
        // TODO: Assert last modified attached?
        // TODO: Assert ETag added?
    }


    @Test
    public void putForMissingResourceCanBeRedirected() throws IOException {

        // ARRANGE
        _request.setMethod(Method.PUT);
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            @Override public Set<Method> getAllowedMethods() {
                return Collections.singleton(Method.PUT);
            }

            @Override public URI movedPermanentlyTo() {
                try {
                    return new URI(TARGET_URI);
                } catch (final URISyntaxException e) {
                    throw new ServerHttpException(
                        Status.INTERNAL_SERVER_ERROR, e);
                }
            }

            @Override public boolean exists() {
                return false;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.MOVED_PERMANENTLY.getCode(), _response.getStatusCode());
        Assert.assertEquals(TARGET_URI, _response.getHeader(Header.LOCATION));
    }


    @Test
    public void putForMissingResourceCanCauseConflict() throws IOException {

        // ARRANGE
        _request.setMethod(Method.PUT);
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

                @Override public Set<Method> getAllowedMethods() {
                    return Collections.singleton(Method.PUT);
                }

                @Override
                public boolean isInConflict() {
                    return true;
                }

                @Override public boolean exists() {
                    return false;
                }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.CONFLICT.getCode(), _response.getStatusCode());
    }


    @Test
    public void putForMissingResourceCreatesResource() throws Exception {

        // ARRANGE
        final byte[] body = new byte[] {0};
        final String createdPath = "/foo";                         //$NON-NLS-1$
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        _request.setMethod(Method.PUT);
        _request.setBody(body);
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            @Override public Set<Method> getAllowedMethods() {
                return Collections.singleton(Method.PUT);
            }

            @Override public boolean exists() {
                return false;
            }

            @Override
            public Map<MediaType, ? extends BodyReader<?>> getContentTypesAccepted() {
                return Collections.singletonMap(MediaType.ANY, new SimpleBodyReader(baos, createdPath, this, _response));
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.CREATED.getCode(), _response.getStatusCode());
        Assert.assertTrue(Arrays.equals(body, baos.toByteArray()));
        Assert.assertEquals(createdPath, _response.getHeader(Header.LOCATION));
        // TODO: Assert last modified attached?
        // TODO: Assert ETag added?
    }


    @Test
    public void serviceUnavailable() throws IOException {

        // ARRANGE
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

                @Override public boolean isServiceAvailable() {
                    return false;
                }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.SERVICE_UNAVAILABLE.getCode(), _response.getStatusCode());
    }


    /**
     * TODO: Add a description for this method.
     *
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        _request  = new TestRequest();
        _response = new TestResponse();
        _engine   = new Engine();
    }


    /**
     * TODO: Add a description for this method.
     *
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        _engine   = null;
        _response = null;
        _request  = null;
    }

    @Test
    public void unacceptableRequestCharsetGivesNotAcceptable() throws IOException {

        // ARRANGE
        _request.setHeader(Header.ACCEPT_CHARSET, "UTF-8");
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {
            /** {@inheritDoc} */
            @Override
            public Set<Charset> getCharsetsProvided() {
                return Collections.singleton(Charset.forName("UTF-16"));
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.NOT_ACCEPTABLE.getCode(), _response.getStatusCode());
    }

    @Test
    public void unacceptableRequestEncodingGivesNotAcceptable() throws IOException {

        // ARRANGE
        _request.setHeader(
            Header.ACCEPT_ENCODING,
            ContentCoding.IDENTITY+";q=0,"+ContentCoding.GZIP);
        final Resource resource = new TestResource(
            new HashMap<String, Object>());

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.NOT_ACCEPTABLE.getCode(), _response.getStatusCode());
    }


    @Test
    public void unacceptableRequestLanguageGivesNotAcceptable() throws IOException {

        // ARRANGE
        _request.setHeader(Header.ACCEPT_LANGUAGE, Locale.UK.toString());
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

                @Override public Set<LanguageTag> getLanguages() {
                    return Collections.singleton(new LanguageTag("fr"));
                }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.NOT_ACCEPTABLE.getCode(), _response.getStatusCode());
    }


    @Test
    public void unacceptableRequestMediaTypeGivesNotAcceptable() throws IOException {

        // ARRANGE
        _request.setHeader(Header.ACCEPT, "text/html");
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            /** {@inheritDoc} */
            @Override
            public Set<MediaType> getContentTypesProvided() {
                return Collections.singleton(MediaType.XML);
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.NOT_ACCEPTABLE.getCode(), _response.getStatusCode());
    }


    @Test
    public void unauthorized() throws IOException {

        // ARRANGE
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {
                @Override public String authorize() { return "foo"; }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.UNAUTHORIZED.getCode(), _response.getStatusCode());
    }


    @Test
    public void unimplementedContentHeader() throws IOException {

        // ARRANGE
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            @Override public boolean hasValidContentHeaders() {
                return false;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.NOT_IMPLEMENTED.getCode(), _response.getStatusCode());
    }


    @Test
    public void unknownContentType() throws IOException {

        // ARRANGE
        _request.setHeader(Header.CONTENT_TYPE, "text/html");
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

            @Override public boolean isContentTypeKnown(final MediaType mediaType) {
                return false;
            }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.UNSUPPORTED_MEDIA_TYPE.getCode(), _response.getStatusCode());
    }


    @Test
    public void unknownMethodGivesNotImplemented() throws IOException {

        // ARRANGE
        _request.setMethod(Method.parse("FOO"));                   //$NON-NLS-1$
        final Resource resource = new TestResource(
            new HashMap<String, Object>());

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.NOT_IMPLEMENTED.getCode(), _response.getStatusCode());
    }


    @Test
    public void uriTooLong() throws IOException {

        // ARRANGE
        final Resource resource = new TestResource(
            new HashMap<String, Object>()) {

                @Override public boolean isUriTooLong() {
                    return true;
                }
        };

        // ACT
        _engine.process(resource, _request, _response);

        // ASSERT
        Assert.assertSame(Status.REQUEST_URI_TOO_LONG.getCode(), _response.getStatusCode());
    }
}
