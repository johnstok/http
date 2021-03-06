/*-----------------------------------------------------------------------------
 * Copyright © 2013 Keith Webster Johnston.
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
package com.johnstok.http.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * A trivial HTTP client, primarily for testing.
 *
 * @author Keith Webster Johnston.
 */
public class SynchronousClient {



    /**
     * GET the body of a specified URI.
     *
     * This method shouldn't be used for testing server errors.
     * {@link HttpURLConnection} silently retries failed requests giving
     * confusing log output.
     *
     * @param uri The URI to GET.
     *
     * @return The body of the HTTP response.
     */
    @Deprecated
    public static String get(final String uri) {
        try {
            final StringBuilder responseBody = new StringBuilder();
            final URL oracle = new URL("http://localhost:4444"+uri);
            final URLConnection yc = oracle.openConnection();
            final BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                    yc.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                responseBody.append(inputLine);
            }
            in.close();
            return responseBody.toString();
        } catch (final MalformedURLException e) {
            throw new RuntimeException("Error GETting '"+uri+"'", e);
        } catch (final IOException e) {
            throw new RuntimeException("Error GETting '"+uri+"'", e);
        }
    }



    /**
     * POST to the specified URI.
     *
     * @param uri       The URI to POST to.
     * @param body      The request body.
     * @param chunkSize The size of each chunk in bytes.
     */
    public static void post(final String uri,
                            final String body,
                            final int chunkSize) throws Exception {
        HttpURLConnection connection = null;
        try {
          //Create connection
          final URL url = new URL(uri);
          connection = (HttpURLConnection) url.openConnection();
          connection.setChunkedStreamingMode(chunkSize);
          connection.setRequestMethod("POST");
          connection.setRequestProperty("Transfer-Encoding", "chunked");
          connection.setUseCaches(false);
          connection.setDoInput(false);
          connection.setDoOutput(true);

          //Send request
          final OutputStream os = connection.getOutputStream();
          for (final char c : body.toCharArray()) {
              os.write(c);
              os.flush();
          }
          os.close();

        } finally {
          if(connection != null) {
            connection.disconnect();
          }
        }
    }
}
