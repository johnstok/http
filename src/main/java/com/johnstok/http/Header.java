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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * Supported HTTP headers.
 *
 * @author Keith Webster Johnston.
 */
public final class Header {

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
    private static final String AUTHORIZATION =
        "Authorization";                                           //$NON-NLS-1$

    /** EXPECT: String */
    private static final String EXPECT =
        "Expect";                                                  //$NON-NLS-1$

    /** FROM: String */
    private static final String FROM =
        "From";                                                    //$NON-NLS-1$

    /** IF_RANGE: String */
    private static final String IF_RANGE =
        "If-Range";                                                //$NON-NLS-1$

    /** MAX_FORWARDS: String */
    private static final String MAX_FORWARDS =
        "Max-Forwards";                                            //$NON-NLS-1$

    /** PROXY_AUTHORIZATION: String */
    private static final String PROXY_AUTHORIZATION =
        "Proxy-Authorization";                                     //$NON-NLS-1$

    /** REFERER: String */
    private static final String REFERER =
        "Referer";                                                 //$NON-NLS-1$

    /** TE: String*/
    private static final String TE =
        "TE";                                                      //$NON-NLS-1$

    /** USER_AGENT: String */
    private static final String USER_AGENT =
        "User-Agent";                                              //$NON-NLS-1$

    /** CACHE_CONTROL : String. */
    private static final String CACHE_CONTROL =
        "Cache-Control";                                           //$NON-NLS-1$

    /** CONNECTION : String. */
    private static final String CONNECTION =
        "Connection";                                              //$NON-NLS-1$

    /** PRAGMA : String. */
    private static final String PRAGMA =
        "Pragma";                                                  //$NON-NLS-1$

    /** TRAILER : String. */
    private static final String TRAILER =
        "Trailer";                                                 //$NON-NLS-1$

    /** TRANSFER_ENCODING : String. */
    private static final String TRANSFER_ENCODING =
        "Transfer-Encoding";                                       //$NON-NLS-1$

    /** UPGRADE : String. */
    private static final String UPGRADE =
        "Upgrade";                                                 //$NON-NLS-1$

    /** VIA : String. */
    private static final String VIA =
        "Via";                                                     //$NON-NLS-1$

    /** WARNING : String. */
    private static final String WARNING =
        "Warning";                                                 //$NON-NLS-1$
    
    /** CONTENT_LOCATION : String */
    private static final String CONTENT_LOCATION =
        "Content-Location";                                        //$NON-NLS-1$
    
    /** CONTENT_MD5 : String */
    private static final String CONTENT_MD5 =
        "Content-MD5";                                             //$NON-NLS-1$
    
    /** EXPIRES : String*/
    private static final String EXPIRES =
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
     * @param header The name of the header.
     *
     * @return True if the header is allowed; false otherwise.
     */
    public static boolean isGeneralHeader(final String header) {
        return _generalHeaders.contains(lower(header));
    }


    /**
     * Determine if a header is allowed for a request message.
     *
     * @param header The name of the header.
     *
     * @return True if the header is allowed; false otherwise.
     */
    public static boolean isRequestHeader(final String header) {
        return _requestHeaders.contains(lower(header));
    }


    /**
     * Determine if a header is allowed for a response message.
     *
     * @param header The name of the header.
     *
     * @return True if the header is allowed; false otherwise.
     */
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

        if (null==value || 1>value.trim().length()) { return wValues; }

        final String[] cRanges = value.split(",");
        for (final String cRange : cRanges) {
            if (null==cRange || 1>cRange.trim().length()) { continue; }
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

        if (null==value || 1>value.trim().length()) { return wValues; }

        final String[] eRanges = value.split(",");
        for (final String eRange : eRanges) {
            if (null==eRange || 1>eRange.trim().length()) { continue; }
            wValues.add(Value.parse(eRange).asWeightedValue("q", 1f));
        }

        return wValues;
    }


    private Header() { super(); }
}
