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


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Collection;


/**
 * Utility methods.
 *
 * @author Keith Webster Johnston.
 */
public final class Utils {

    /**
     * Calculate the ETag for a file.
     *
     * @param file The file to analyse.
     *
     * @return The ETag, as a string.
     */
    public static String eTag(final File file) { // TODO: Move to FileResource class.
        try {
            final String uid =
                file.length()
                +":"+file.lastModified()                           //$NON-NLS-1$
                +":"+file.getCanonicalPath();                      //$NON-NLS-1$

            final MessageDigest m =
                MessageDigest.getInstance("MD5");                  //$NON-NLS-1$
            final byte[] data = uid.getBytes();
            m.update(data, 0, data.length);
            final BigInteger i = new BigInteger(1, m.digest());
            return String.format("%1$032X", i);                    //$NON-NLS-1$

        } catch (final Exception e) {
            // FIXME: Log error?
            return null;
        }
    }

    /**
     * Join a collection of strings with a specified delimiter.
     *
     * @param values    The values to join.
     * @param delimiter The delimiter character.
     *
     * @return The joined value, in a string buffer.
     */
    public static StringBuffer join(final Collection<? extends Object> values,
                                    final char delimiter) {
        final StringBuffer buffer = new StringBuffer();
        for (final Object value : values) {
            buffer.append(value);
            buffer.append(delimiter);
        }
        final int length = buffer.length();
        if (length>0) {
            buffer.deleteCharAt(length-1);
        }
        return buffer;
    }


    /**
     * TODO: Add a description for this method.
     *
     * @param is
     * @param os
     */
    public static void copy(final InputStream  is,
                            final OutputStream os) throws IOException {
        byte[] buffer = new byte[1024];
        int read = is.read(buffer);
        while (0<read) {
            os.write(buffer, 0, read);
            read = is.read(buffer);
        }
    }

    private Utils() { super(); }
}
