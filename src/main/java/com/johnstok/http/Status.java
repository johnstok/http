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

@Specifications({
    @Specification(name="rfc-2616", section="6.1.1"),
    @Specification(name="rfc-2616", section="10")
})
public enum Status {

    CONTINUE(                       100, "Continue"),
    SWITCHING_PROTOCOLS(            101, "Switching Protocols"),

    OK(                             200, "OK"),
    CREATED(                        201, "Created"),
    ACCEPTED(                       202, "Accepted"),
    NON_AUTHORITATIVE_INFORMATION(  203, "Non-Authoritative Information"),
    NO_CONTENT(                     204, "No Content"),
    RESET_CONTENT(                  205, "Reset Content"),
    PARTIAL_CONTENT(                206, "Partial Content"),

    MULTIPLE_CHOICES(               300, "Multiple Choices"),
    MOVED_PERMANENTLY(              301, "Moved Permanently"),
    FOUND(                          302, "Found"),
    SEE_OTHER(                      303, "See Other"),
    NOT_MODIFIED(                   304, "Not Modified"),
    USE_PROXY(                      305, "Use Proxy"),
    TEMPORARY_REDIRECT(             307, "Temporary Redirect"),

    BAD_REQUEST(                    400, "Bad Request"),
    UNAUTHORIZED(                   401, "Unauthorized"),
    PAYMENT_REQUIRED(               402, "Payment Required"),
    FORBIDDEN(                      403, "Forbidden"),
    NOT_FOUND(                      404, "Not Found"),
    METHOD_NOT_ALLOWED(             405, "Method Not Allowed"),
    NOT_ACCEPTABLE(                 406, "Not Acceptable"),
    PROXY_AUTHENTICATION_REQUIRED(  407, "Proxy Authentication Required"),
    REQUEST_TIMEOUT(                408, "Request Timeout"),
    CONFLICT(                       409, "Conflict"),
    GONE(                           410, "Gone"),
    LENGTH_REQUIRED(                411, "Length Required"),
    PRECONDITION_FAILED(            412, "Precondition Failed"),
    REQUEST_ENTITY_TOO_LARGE(       413, "Request Entity Too Large"),
    REQUEST_URI_TOO_LONG(           414, "Request-URI Too Long"),
    UNSUPPORTED_MEDIA_TYPE(         415, "Unsupported Media Type"),
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested Range Not Satisfiable"),
    EXPECTATION_FAILED(             417, "Expectation Failed"),

    INTERNAL_SERVER_ERROR(          500, "Internal Server Error"),
    NOT_IMPLEMENTED(                501, "Not Implemented"),
    BAD_GATEWAY(                    502, "Bad Gateway"),
    SERVICE_UNAVAILABLE(            503, "Service Unavailable"),
    GATEWAY_TIMEOUT(                504, "Gateway Timeout"),
    VERSION_NOT_SUPPORTED(          505, "Version Not Supported");


    public static final String EXTENSION_CODE = "["+Syntax.DIGIT+"]{3}";
    public static final String REASON_PHRASE = Syntax.SP+Syntax.HT+"["+Syntax.OCTET+"&&[^"+Syntax.CTL+"]]"; // *<TEXT, excluding CR, LF>
    public static final String SYNTAX =
        "100" +
        "|101" +

        "|200" +
        "|201" +
        "|202" +
        "|203" +
        "|204" +
        "|205" +
        "|206" +

        "|300" +
        "|301" +
        "|302" +
        "|303" +
        "|304" +
        "|305" +
        "|307" +

        "|400" +
        "|401" +
        "|402" +
        "|403" +
        "|404" +
        "|405" +
        "|406" +
        "|407" +
        "|408" +
        "|409" +
        "|410" +
        "|411" +
        "|412" +
        "|413" +
        "|414" +
        "|415" +
        "|416" +
        "|417" +

        "|500" +
        "|501" +
        "|502" +
        "|503" +
        "|504" +
        "|505" +
        "|"+EXTENSION_CODE;


    private final String _reasonPhrase;
    private final int    _code;


    /**
     * Constructor.
     *
     * @param code         The integer code for the status.
     * @param reasonPhrase The reason phrase for the status.
     */
    private Status(final int code, final String reasonPhrase) {
        _reasonPhrase = reasonPhrase;
        _code = code;
    }


    public int getCode() {
        return _code;
    }


    public String getReasonPhrase() {
        return _reasonPhrase;
    }


    /**
     * Check if the status is informational.
     *
     * @return True if the request is informational; false otherwise.
     */
    public boolean isInformational() {
        return 1==(_code/100);
    }


    /**
     * Check if the status is a success.
     *
     * @return True if the request is a success; false otherwise.
     */
    public boolean isSuccess() {
        return 2==(_code/100);
    }


    /**
     * Check if the status is a redirect.
     *
     * @return True if the request is a redirect; false otherwise.
     */
    public boolean isRedirect() {
        return 3==(_code/100);
    }


    /**
     * Check if the status is a client error.
     *
     * @return True if the request is a client error; false otherwise.
     */
    public boolean isClientError() {
        return 4==(_code/100);
    }


    /**
     * Check if the status is a server error.
     *
     * @return True if the request is a server error; false otherwise.
     */
    public boolean isServerError() {
        return 5==(_code/100);
    }


    /** {@inheritDoc} */
    @Override
    public String toString() { return String.valueOf(getCode()); }
}
