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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Product tokens are used to allow communicating applications to identify
 * themselves by software name and version. Most fields using product tokens
 * also allow sub-products which form a significant part of the application to
 * be listed, separated by white space. By convention, the products are listed
 * in order of their significance for identifying the application.
 * <pre>
 *        product         = token ["/" product-version]
 *        product-version = token
 * </pre>
 * Examples:
 * <pre>
 *        User-Agent: CERN-LineMode/2.15 libwww/2.17b3
 *        Server: Apache/0.8.4
 * </pre>
 * Product tokens SHOULD be short and to the point. They MUST NOT be used for
 * advertising or other non-essential information. Although any token character
 * MAY appear in a product-version, this token SHOULD only be used for a version
 * identifier (i.e., successive versions of the same product SHOULD only differ
 * in the product-version portion of the product value).
 *
 * @author Keith Webster Johnston.
 */
@Specification(name="rfc-2616", section="3.8")
public class ProductToken {

    public  static final String SYNTAX =
        "(["+Syntax.TOKEN+"]+)(?:/(["+Syntax.TOKEN+"]+))?";

    private final String _product;
    private final String _version;


    /**
     * Constructor.
     *
     * @param product The product name.
     * @param version The product version.
     */
    public ProductToken(final String product, final String version) {
        // FIXME: Validate params.
        _product = product;
        _version = version;
    }


    /**
     * Accessor.
     *
     * @return Returns the product.
     */
    public String getProduct() {
        return _product;
    }


    /**
     * Accessor.
     *
     * @return Returns the version.
     */
    public String getVersion() {
        return _version;
    }


    /**
     * Parse a string into a product token.
     *
     * @param productTokenString A string representing the product token.
     *
     * @return A corresponding product token object.
     */
    public static ProductToken parse(final String productTokenString) {
        final Matcher m = Pattern.compile(SYNTAX).matcher(productTokenString);
        if (m.matches()) {
            return new ProductToken(m.group(1), m.group(2));
        }
        throw new ClientHttpException(Status.BAD_REQUEST);
    }


    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
            prime * result + ((_product == null) ? 0 : _product.hashCode());
        result =
            prime * result + ((_version == null) ? 0 : _version.hashCode());
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
        final ProductToken other = (ProductToken) obj;
        if (_product == null) {
            if (other._product != null) {
                return false;
            }
        } else if (!_product.equals(other._product)) {
            return false;
        }
        if (_version == null) {
            if (other._version != null) {
                return false;
            }
        } else if (!_version.equals(other._version)) {
            return false;
        }
        return true;
    }
}
