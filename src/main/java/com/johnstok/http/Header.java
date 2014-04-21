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

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A message header.
 *
 * <pre>
   HTTP header fields, which include general-header (section 4.5),
   request-header (section 5.3), response-header (section 6.2), and
   entity-header (section 7.1) fields, follow the same generic format as
   that given in Section 3.1 of RFC 822 [9]. Each header field consists
   of a name followed by a colon (":") and the field value. Field names
   are case-insensitive. The field value MAY be preceded by any amount
   of LWS, though a single SP is preferred. Header fields can be
   extended over multiple lines by preceding each extra line with at
   least one SP or HT. Applications ought to follow "common form", where
   one is known or indicated, when generating HTTP constructs, since
   there might exist some implementations that fail to accept anything
   beyond the common forms.

       message-header = field-name ":" [ field-value ]
       field-name     = token
       field-value    = *( field-content | LWS )
       field-content  = <the OCTETs making up the field-value
                        and consisting of either *TEXT or combinations
                        of token, separators, and quoted-string>

   The field-content does not include any leading or trailing LWS:
   linear white space occurring before the first non-whitespace
   character of the field-value or after the last non-whitespace
   character of the field-value. Such leading or trailing LWS MAY be
   removed without changing the semantics of the field value. Any LWS
   that occurs between field-content MAY be replaced with a single SP
   before interpreting the field value or forwarding the message
   downstream.

   The order in which header fields with differing field names are
   received is not significant. However, it is "good practice" to send
   general-header fields first, followed by request-header or response-
   header fields, and ending with the entity-header fields.

   Multiple message-header fields with the same field-name MAY be
   present in a message if and only if the entire field-value for that
   header field is defined as a comma-separated list [i.e., #(values)].
   It MUST be possible to combine the multiple header fields into one
   "field-name: field-value" pair, without changing the semantics of the
   message, by appending each subsequent field-value to the first, each
   separated by a comma. The order in which header fields with the same
   field-name are received is therefore significant to the
   interpretation of the combined field value, and thus a proxy MUST NOT
   change the order of these field values when a message is forwarded.
 * </pre>
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="4.2")
public final class Header {

    private static final String WS = "["+Syntax.SP+Syntax.HT+"]*";
    // Deliberately permissive on whitespace because the spec is ambiguous.
    // TODO: We don't allow quoted strings here.
    public static final String SYNTAX =
        "(["+Syntax.TOKEN+"]+):("+WS+Syntax.TEXT+WS+")";

    /** ALLOW : String. */
    public static final String ALLOW =
        "Allow";                                                   //$NON-NLS-1$

    /** LOCATION : String. */
    public static final String LOCATION =
        "Location";                                                //$NON-NLS-1$

    /** ACCEPT_ENCODING : String. */
    public static final String ACCEPT_ENCODING =
        "Accept-Encoding";                                         //$NON-NLS-1$

    /** SERVER : String. */
    public static final String SERVER =
        "Server";                                                  //$NON-NLS-1$

    /** CONTENT_TYPE : String. */
    public static final String CONTENT_TYPE =
        "Content-Type";                                            //$NON-NLS-1$

    /** DATE : String. */
    public static final String DATE =
        "Date";                                                    //$NON-NLS-1$

    /** LAST_MODIFIED : String. */
    public static final String LAST_MODIFIED =
        "Last-Modified";                                           //$NON-NLS-1$

    /** CONTENT_ENCODING : String. */
    public static final String CONTENT_ENCODING =
        "Content-Encoding";                                        //$NON-NLS-1$

    /** CONTENT_ENCODING : String. */
    public static final String E_TAG =
        "ETag";                                                    //$NON-NLS-1$

    /** RANGE : String. */
    public static final String RANGE =
        "Range";                                                   //$NON-NLS-1$

    /** CONTENT_RANGE : String. */
    public static final String CONTENT_RANGE =
        "Content-Range";                                           //$NON-NLS-1$

    /** ACCEPT_RANGES : String. */
    public static final String ACCEPT_RANGES =
        "Accept-Ranges";                                           //$NON-NLS-1$

    /** IF_MODIFIED_SINCE : String. */
    public static final String IF_MODIFIED_SINCE =
        "If-Modified-Since";                                       //$NON-NLS-1$

    /** IF_MATCH : String. */
    public static final String IF_MATCH =
        "If-Match";                                                //$NON-NLS-1$

    /** IF_NONE_MATCH: String. */
    public static final String IF_NONE_MATCH =
        "If-None-Match";                                           //$NON-NLS-1$

    /** COOKIE : String. */
    public static final String COOKIE =
        "Cookie";                                                  //$NON-NLS-1$

    /** ACCEPT : String. */
    public static final String ACCEPT =
        "Accept";                                                  //$NON-NLS-1$

    /** ACCEPT_LANGUAGE : String. */
    public static final String ACCEPT_LANGUAGE =
        "Accept-Language";                                         //$NON-NLS-1$

    /** ACCEPT_CHARSET : String. */
    public static final String ACCEPT_CHARSET =
        "Accept-Charset";                                          //$NON-NLS-1$

    /** CONTENT_LENGTH : String. */
    public static final String CONTENT_LENGTH =
        "Content-Length";                                          //$NON-NLS-1$

    /** IF_UNMODIFIED_SINCE : String. */
    public static final String IF_UNMODIFIED_SINCE =
        "If-Unmodified-Since";                                     //$NON-NLS-1$

    /** CONTENT_LANGUAGE : String. */
    public static final String CONTENT_LANGUAGE =
        "Content-Language";                                        //$NON-NLS-1$

    /** WWW_AUTHENTICATE : String. */
    public static final String WWW_AUTHENTICATE =
        "WWW-Authenticate";                                        //$NON-NLS-1$

    /** VARY: String */
    public static final String VARY =
        "Vary";                                                    //$NON-NLS-1$

    /** AGE: String */
    public static final String AGE =
        "Age";                                                     //$NON-NLS-1$

    /** PROXY_AUTHENTICATE: String */
    public static final String PROXY_AUTHENTICATE =
        "Proxy-Auhtenticate";                                      //$NON-NLS-1$

    /** HOST: String */
    public static final String HOST =
        "Host";                                                    //$NON-NLS-1$

    /** RETRY_AFTER: String */
    public static final String RETRY_AFTER =
        "Retry-After";                                             //$NON-NLS-1$

    /** AUTHORIZATION: String */
    public static final String AUTHORIZATION =
        "Authorization";                                           //$NON-NLS-1$

    /** EXPECT: String */
    public static final String EXPECT =
        "Expect";                                                  //$NON-NLS-1$

    /** FROM: String */
    public static final String FROM =
        "From";                                                    //$NON-NLS-1$

    /** IF_RANGE: String */
    public static final String IF_RANGE =
        "If-Range";                                                //$NON-NLS-1$

    /** MAX_FORWARDS: String */
    public static final String MAX_FORWARDS =
        "Max-Forwards";                                            //$NON-NLS-1$

    /** PROXY_AUTHORIZATION: String */
    public static final String PROXY_AUTHORIZATION =
        "Proxy-Authorization";                                     //$NON-NLS-1$

    /** REFERER: String */
    public static final String REFERER =
        "Referer";                                                 //$NON-NLS-1$

    /** TE: String*/
    public static final String TE =
        "TE";                                                      //$NON-NLS-1$

    /** USER_AGENT: String */
    public static final String USER_AGENT =
        "User-Agent";                                              //$NON-NLS-1$

    /** CACHE_CONTROL : String. */
    public static final String CACHE_CONTROL =
        "Cache-Control";                                           //$NON-NLS-1$

    /** CONNECTION : String. */
    public static final String CONNECTION =
        "Connection";                                              //$NON-NLS-1$

    /** PRAGMA : String. */
    public static final String PRAGMA =
        "Pragma";                                                  //$NON-NLS-1$

    /** TRAILER : String. */
    public static final String TRAILER =
        "Trailer";                                                 //$NON-NLS-1$

    /** TRANSFER_ENCODING : String. */
    public static final String TRANSFER_ENCODING =
        "Transfer-Encoding";                                       //$NON-NLS-1$

    /** UPGRADE : String. */
    public static final String UPGRADE =
        "Upgrade";                                                 //$NON-NLS-1$

    /** VIA : String. */
    public static final String VIA =
        "Via";                                                     //$NON-NLS-1$

    /** WARNING : String. */
    public static final String WARNING =
        "Warning";                                                 //$NON-NLS-1$

    /** CONTENT_LOCATION : String */
    public static final String CONTENT_LOCATION =
        "Content-Location";                                        //$NON-NLS-1$

    /** CONTENT_MD5 : String */
    public static final String CONTENT_MD5 =
        "Content-MD5";                                             //$NON-NLS-1$

    /** EXPIRES : String*/
    public static final String EXPIRES =
        "Expires";                                                 //$NON-NLS-1$



    private static final Set<String> _generalHeaders =
        Collections.unmodifiableSet(new HashSet<String>(
            Arrays.asList(new String[] {
                lower(CACHE_CONTROL),
                lower(CONNECTION),
                lower(DATE),
                lower(PRAGMA),
                lower(TRAILER),
                lower(TRANSFER_ENCODING),
                lower(UPGRADE),
                lower(VIA),
                lower(WARNING)
            })
        ));


    private static final Set<String> _requestHeaders =
        Collections.unmodifiableSet(new HashSet<String>(
            Arrays.asList(new String[] {
                lower(ACCEPT),
                lower(ACCEPT_CHARSET),
                lower(ACCEPT_ENCODING),
                lower(ACCEPT_LANGUAGE),
                lower(AUTHORIZATION),
                lower(EXPECT),
                lower(FROM),
                lower(HOST),
                lower(IF_MATCH),
                lower(IF_MODIFIED_SINCE),
                lower(IF_NONE_MATCH),
                lower(IF_RANGE),
                lower(IF_UNMODIFIED_SINCE),
                lower(MAX_FORWARDS),
                lower(PROXY_AUTHORIZATION),
                lower(RANGE),
                lower(REFERER),
                lower(TE),
                lower(USER_AGENT)
            })
        ));


    private static final Set<String> _responseHeaders =
        Collections.unmodifiableSet(new HashSet<String>(
            Arrays.asList(new String[] {
                lower(ACCEPT_RANGES),
                lower(AGE),
                lower(E_TAG),
                lower(LOCATION),
                lower(PROXY_AUTHENTICATE),
                lower(RETRY_AFTER),
                lower(SERVER),
                lower(VARY),
                lower(WWW_AUTHENTICATE)
            })
        ));

    private static final Set<String> _entityHeaders =
        Collections.unmodifiableSet(new HashSet<String>(
            Arrays.asList(new String[] {
                    lower(ALLOW),
                    lower(CONTENT_ENCODING),
                    lower(CONTENT_LANGUAGE),
                    lower(CONTENT_LENGTH),
                    lower(CONTENT_LOCATION),
                    lower(CONTENT_MD5),
                    lower(CONTENT_RANGE),
                    lower(CONTENT_TYPE),
                    lower(EXPIRES),
                    lower(LAST_MODIFIED)
            })
        ));


    /**
     * Determine if a header is a general header.
     *
     * <pre>
   There are a few header fields which have general applicability for
   both request and response messages, but which do not apply to the
   entity being transferred. These header fields apply only to the
   message being transmitted.

       general-header = Cache-Control            ; Section 14.9
                      | Connection               ; Section 14.10
                      | Date                     ; Section 14.18
                      | Pragma                   ; Section 14.32
                      | Trailer                  ; Section 14.40
                      | Transfer-Encoding        ; Section 14.41
                      | Upgrade                  ; Section 14.42
                      | Via                      ; Section 14.45
                      | Warning                  ; Section 14.46

   General-header field names can be extended reliably only in
   combination with a change in the protocol version. However, new or
   experimental header fields may be given the semantics of general
   header fields if all parties in the communication recognize them to
   be general-header fields. Unrecognized header fields are treated as
   entity-header fields.
     * </pre>
     *
     * @param header The name of the header.
     *
     * @return True if the header is allowed; false otherwise.
     */
    @Specification(name="rfc-2616", section="4.5")
    public static boolean isGeneralHeader(final String header) {
        return _generalHeaders.contains(lower(header));
    }


    /**
     * Determine if a header is allowed for a request message.
     *
     * <pre>
   The request-header fields allow the client to pass additional
   information about the request, and about the client itself, to the
   server. These fields act as request modifiers, with semantics
   equivalent to the parameters on a programming language method
   invocation.

       request-header = Accept                   ; Section 14.1
                      | Accept-Charset           ; Section 14.2
                      | Accept-Encoding          ; Section 14.3
                      | Accept-Language          ; Section 14.4
                      | Authorization            ; Section 14.8
                      | Expect                   ; Section 14.20
                      | From                     ; Section 14.22
                      | Host                     ; Section 14.23
                      | If-Match                 ; Section 14.24
                      | If-Modified-Since        ; Section 14.25
                      | If-None-Match            ; Section 14.26
                      | If-Range                 ; Section 14.27
                      | If-Unmodified-Since      ; Section 14.28
                      | Max-Forwards             ; Section 14.31
                      | Proxy-Authorization      ; Section 14.34
                      | Range                    ; Section 14.35
                      | Referer                  ; Section 14.36
                      | TE                       ; Section 14.39
                      | User-Agent               ; Section 14.43

   Request-header field names can be extended reliably only in
   combination with a change in the protocol version. However, new or
   experimental header fields MAY be given the semantics of request-
   header fields if all parties in the communication recognize them to
   be request-header fields. Unrecognized header fields are treated as
   entity-header fields.
     * </pre>
     *
     * @param header The name of the header.
     *
     * @return True if the header is allowed; false otherwise.
     */
    @Specification(name="rfc-2616", section="5.3")
    public static boolean isRequestHeader(final String header) {
        return _requestHeaders.contains(lower(header));
    }


    /**
     * Determine if a header is allowed for a response message.
     *
     * <pre>
   The response-header fields allow the server to pass additional
   information about the response which cannot be placed in the Status-
   Line. These header fields give information about the server and about
   further access to the resource identified by the Request-URI.

       response-header = Accept-Ranges           ; Section 14.5
                       | Age                     ; Section 14.6
                       | ETag                    ; Section 14.19
                       | Location                ; Section 14.30
                       | Proxy-Authenticate      ; Section 14.33
                       | Retry-After             ; Section 14.37
                       | Server                  ; Section 14.38
                       | Vary                    ; Section 14.44
                       | WWW-Authenticate        ; Section 14.47

   Response-header field names can be extended reliably only in
   combination with a change in the protocol version. However, new or
   experimental header fields MAY be given the semantics of response-
   header fields if all parties in the communication recognize them to
   be response-header fields. Unrecognized header fields are treated as
   entity-header fields.
     * </pre>
     *
     * @param header The name of the header.
     *
     * @return True if the header is allowed; false otherwise.
     */
    @Specification(name="rfc-2616", section="6.2")
    public static boolean isResponseHeader(final String header) {
        return _responseHeaders.contains(lower(header));
    }


    /**
     * Determine if a header is an entity header.
     *
     * <pre>
   Entity-header fields define meta-information about the entity-body or,
   if no body is present, about the resource identified by the request.
   Some of this meta-information is OPTIONAL; some might be REQUIRED by
   portions of this specification.

       entity-header  = Allow                    ; Section 14.7
                      | Content-Encoding         ; Section 14.11
                      | Content-Language         ; Section 14.12
                      | Content-Length           ; Section 14.13
                      | Content-Location         ; Section 14.14
                      | Content-MD5              ; Section 14.15
                      | Content-Range            ; Section 14.16
                      | Content-Type             ; Section 14.17
                      | Expires                  ; Section 14.21
                      | Last-Modified            ; Section 14.29
                      | extension-header

       extension-header = message-header

   The extension-header mechanism allows additional entity-header fields
   to be defined without changing the protocol, but these fields cannot
   be assumed to be recognizable by the recipient. Unrecognized header
   fields SHOULD be ignored by the recipient and MUST be forwarded by
   transparent proxies.
     * </pre>
     *
     * @param header The name of the header.
     *
     * @return True if the header is allowed; false otherwise.
     */
    @Specification(name="rfc-2616", section="7.1")
    public static boolean isEntityHeader(final String header) {
        // FIXME: Doesn't allow for extension.
        return _entityHeaders.contains(lower(header));
    }


    private static String lower(final String string) {
        return (null == string) ? null : string.toLowerCase(Locale.US);
    }


    /**
     * Parse an 'Accept-Charset' header into a list of weighted values.
     *
     * <pre>
     *       Accept-Charset = "Accept-Charset" ":"
     *        1#( ( charset | "*" )[ ";" "q" "=" qvalue ] )
     * </pre>
     *
     * Each charset MAY be given an associated quality value which represents
     * the user's preference for that charset. The default value is q=1.
     *
     * @param value
     *
     * @return
     */
    public static List<WeightedValue> parseAcceptCharset(final String value) {
        /*
         * TODO Handle:
         *  - duplicate cRange (incl case variations).
         *  - malformed cRange
         *  - malformed field
         */
        final List<WeightedValue> wValues = new ArrayList<WeightedValue>();

        if ((null==value) || (1>value.trim().length())) { return wValues; }

        final String[] cRanges = value.split(",");
        for (final String cRange : cRanges) {
            if ((null==cRange) || (1>cRange.trim().length())) { continue; }
            wValues.add(Value.parse(cRange).asWeightedValue("q", 1f));
        }

        return wValues;
    }


    /**
     * Parse an 'Accept-Encoding' header into a list of weighted values.
     *
     * @param value
     *
     * @return
     */
    public static List<WeightedValue> parseAcceptEncoding(final String value) {
        /*
         * TODO Handle:
         *  - duplicate eRange (incl case variations).
         *  - malformed eRange
         *  - malformed field
         */
        final List<WeightedValue> wValues = new ArrayList<WeightedValue>();

        if ((null==value) || (1>value.trim().length())) { return wValues; }

        final String[] eRanges = value.split(",");
        for (final String eRange : eRanges) {
            if ((null==eRange) || (1>eRange.trim().length())) { continue; }
            wValues.add(Value.parse(eRange).asWeightedValue("q", 1f));
        }

        return wValues;
    }


    /**
     * Parse a string into a Header.
     *
     * @param headerString A string representing the header.
     *
     * @return A corresponding header object.
     */
    public static Header parse(final String headerString) {
        final Matcher m = Pattern.compile(SYNTAX).matcher(headerString);
        if (m.matches()) {
            return new Header(m.group(1), m.group(2));
        }
        throw new ClientHttpException(Status.BAD_REQUEST);
    }


    private final String _name;
    private final String _value;

    private Header(final String name, final String value) {
        // TODO: How do we handle non-ASCII characters - escaping?
        /*
         *  TODO: This class has static query methods (isEntityHeader) but the
         *  Method class has non-static methods (isSafe) - change to be
         *  consistent.
         */
        _name = Objects.requireNonNull(name);
        _value = Objects.requireNonNull(value);
    }


    public String getName() {
        return _name;
    }


    public String getValue() {
        return _value;
    }


    public String getContent() {
        return _value.replaceAll("\\r\\n[ \\t]+", " ").trim();
    }


    public <T> T get(final HeaderName<T> headerName) {
        return headerName.parse(getContent());
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((_name == null) ? 0 : _name.hashCode());
        result = (prime * result) + ((_value == null) ? 0 : _value.hashCode());
        return result;
    }


    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Header other = (Header) obj;
        if (_name == null) {
            if (other._name != null) {
                return false;
            }
        } else if (!_name.toLowerCase(Locale.US).equals(other._name.toLowerCase(Locale.US))) {
            return false;
        }
        if (_value == null) {
            if (other._value != null) {
                return false;
            }
        } else if (!_value.equals(other._value)) {
            return false;
        }
        return true;
    }
}
