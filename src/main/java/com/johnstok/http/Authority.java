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

import static com.johnstok.http.Syntax.*;
import java.util.regex.Pattern;

/**
 * A URI authority.
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2396")
public class Authority {

    /*
     * An alternative implementation would be to coerce the string to a
     * 'net_path' by pre-pending "//" and then parse with java.net.URI.
     *
     * We would need to confirm that:
     *  1. The authority is present;
     *  2. The path is absent;
     *  3. The query string is missing;
     *  4. The fragment is missing.
     *
     *  To distinguish between registry-based and server-based authorities
     *  we can call URI#parseServerAuthority() and catch any thrown
     *  exception.
     *
     *  This approach would address both the to-do's below.
     */

    /*
     * TODO: Add a parse method that extracts the relevant components.
     *
     * TODO: Does not support IPv6 (not defined in RFC-2396).
     * Either:
     *  1. Update with rfc-2732
     *  2. Update with rfc-3986  <--- preferable?
     */

    public static final String ESCAPED      = "%["+HEX+"]["+HEX+"]";
    public static final String ALPHANUM     = ALPHA+DIGIT;
    public static final String MARK         = "-_\\.\\!~\\*'\\(\\)";
    public static final String UNRESERVED   = ALPHANUM+MARK;

    public static final String PORT         = DIGIT+"*";
    public static final String IP4_ADDR     = DIGIT+"+\\."+DIGIT+"+\\."+DIGIT+"+\\."+DIGIT+"+";
    public static final String DOMAIN_LABEL = "["+ALPHANUM+"]|["+ALPHANUM+"]["+ALPHANUM+"-"+"]*["+ALPHANUM+"]";
    public static final String TOP_LABEL    = "["+ALPHA+"]|["+ALPHA+"]["+ALPHANUM+"-"+"]*["+ALPHANUM+"]";
    public static final String HOSTNAME     = "(("+DOMAIN_LABEL+")\\.)*("+TOP_LABEL+")\\.?";
    public static final String HOST         = "("+HOSTNAME+")|("+IP4_ADDR+")";
    public static final String HOSTPORT     = "("+HOST+")(\\:"+PORT+")?";
    public static final String USERINFO     = "([\\$\\,;\\:\\&=\\+"+UNRESERVED+"]|"+ESCAPED+")*";
    public static final String SERVER       = "("+USERINFO+"@)?("+HOSTPORT+")";

    public static final String REG_NAME     = "([\\$\\,;\\:@\\&=\\+"+UNRESERVED+"]|"+ESCAPED+")+";

    public static final String AUTHORITY    = SERVER+"|"+REG_NAME;


    public static boolean isValid(final String string) {
        return Pattern.matches(AUTHORITY, string);
    }
}
