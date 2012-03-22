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

import java.util.Locale;


/**
 * A language tag identifies a natural language spoken, written, or
 * otherwise conveyed by human beings for communication of information
 * to other human beings. Computer languages are explicitly excluded.
 * HTTP uses language tags within the Accept-Language and Content-
 * Language fields.

 * The syntax and registry of HTTP language tags is the same as that
 * defined by RFC 1766 [1]. In summary, a language tag is composed of 1
 * or more parts: A primary language tag and a possibly empty series of
 * subtags:
 * <pre>
 *      language-tag  = primary-tag *( "-" subtag )
 *      primary-tag   = 1*8ALPHA
 *      subtag        = 1*8ALPHA
 * </pre>
 * White space is not allowed within the tag and all tags are case-
 * insensitive. The name space of language tags is administered by the
 * IANA. Example tags include:
 * <pre>
 *     en, en-US, en-cockney, i-cherokee, x-pig-latin
 * </pre>
 * where any two-letter primary-tag is an ISO-639 language abbreviation
 * and any two-letter initial subtag is an ISO-3166 country code. (The
 * last three tags above are not registered tags; all but the last are
 * examples of tags which could be registered in future.)
 *
 * @author Keith Webster Johnston.
 */
@Specifications({
    @Specification(name="rfc-2616", section="3.10"),
    @Specification(name="rfc-1766")
})
public class LanguageTag {
    // TODO: Add a parse method, rather than a public constructor.

    private final String _value;


    /**
     * Constructor.
     *
     * @param value
     */
    public LanguageTag(final String value) {
        /*
         * White space is not allowed within the tag and all tags are
         * case-insensitive.
         */
        _value = Contract.require().matches("\\p{Alpha}{1,8}(-\\p{Alpha}{1,8})*", value);
    }


    /**
     * Determine if language tags match.
     *
     * A language-range matches a language-tag if it exactly equals the tag,
     * or if it exactly equals a prefix of the tag such that the first tag
     * character following the prefix is "-".
     *
     * @param languageTag The tag to match.
     *
     * @return True if the specified tag matches this tag, false otherwise.
     */
    @Specification(name="rfc-2616", section="14.4")
    public boolean matchedBy(final String languageTag) {
        final String ciTag   = _value.toLowerCase(Locale.US);
        final String ciRange = languageTag.toLowerCase(Locale.US);
        return ciTag.equals(ciRange)
               || (ciTag.startsWith(ciRange)
                   && '-'==ciTag.charAt(languageTag.length()));
    }


    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
            prime * result + ((_value == null) ? 0 : _value.hashCode());
        return result;
    }


    /** {@inheritDoc} */
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
        final LanguageTag other = (LanguageTag) obj;
        if (_value == null) {
            if (other._value != null) {
                return false;
            }
        } else if (!_value.equalsIgnoreCase(other._value)) {
            return false;
        }
        return true;
    }


    /** {@inheritDoc} */
    @Override
    public String toString() {
        return _value;
    }


    /**
     * Determine the number of elements by which language tags match.
     *
     * @param languageTag The tag to match.
     *
     * @return The number of matching elements, 0 if the tags don't match.
     */
    public int matchDepth(final String languageTag) {
        return (matchedBy(languageTag)) ? languageTag.split("-").length : 0;
    }
}
