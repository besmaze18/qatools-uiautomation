/**
 * TestRail API binding for Java (API v2, available since TestRail 3.0)
 *
 * Learn more:
 *
 * http://docs.gurock.com/testrail-api2/start http://docs.gurock.com/testrail-api2/accessing
 *
 * Copyright Gurock Software GmbH. See license.md for details.
 */

package testrail;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class APIClient {
    private String user;
    private String password;
    private final String url;

    public APIClient(String baseUrl) {
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }

        url = baseUrl + "index.php?/api/v2/";
    }

    /**
     * Get/Set User
     *
     * Returns/sets the user used for authenticating the API requests.
     */
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Get/Set Password
     *
     * Returns/sets the password used for authenticating the API requests.
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Send Get
     *
     * Issues a GET request (read) against the API and returns the result (as Object, see below).
     *
     * Arguments:
     *
     * @param uri The API method to call including parameters (e.g. get_case/1)
     *
     * @return the parsed JSON response as standard object which can either be an instance of JSONObject or JSONArray
     * (depending on the API method). In most cases, this returns a JSONObject instance which is basically the same as
     * java.util.Map.
     */
    public Object sendGet(String uri)
            throws IOException, APIException {
        return sendRequest("GET", uri, null);
    }

    /**
     * Send POST
     *
     * Issues a POST request (write) against the API and returns the result (as Object, see below).
     *
     * Arguments:
     *
     * @param uri The API method to call including parameters (e.g. add_case/1) data
     * @param data to submit as part of the request (e.g., a map)
     *
     * @return the parsed JSON response as standard object which can either be an instance of JSONObject or JSONArray
     * (depending on the API method). In most cases, this returns a JSONObject instance which is basically the same as
     * java.util.Map.
     */
    public Object sendPost(String uri, Object data)
            throws IOException, APIException {
        return sendRequest("POST", uri, data);
    }

    private Object sendRequest(String method, String uri, Object data)
            throws IOException, APIException {
        URL requestUrl = new URL(url + uri);

        // Create the connection object and set the required HTTP method
        // (GET/POST) and headers (content type and basic auth).
        HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
        conn.addRequestProperty("Content-Type", "application/json");

        String auth = getAuthorization(user, password);
        conn.addRequestProperty("Authorization", "Basic " + auth);

        if ("POST".equals(method) && data != null) {
            // Add the POST arguments, if any. We just serialize the passed
            // data object (i.e. a dictionary) and then add it to the
            // request body.
            byte[] block = JSONValue.toJSONString(data).
                    getBytes(StandardCharsets.UTF_8);

            conn.setDoOutput(true);
            OutputStream ostream = conn.getOutputStream();
            ostream.write(block);
            ostream.flush();
        }

        // Execute the actual web request (if it wasn't already initiated
        // by getOutputStream above) and record any occurred errors (we use
        // the error stream in this case).
        int status = conn.getResponseCode();

        InputStream istream;
        if (status != 200) {
            istream = conn.getErrorStream();
            if (istream == null) {
                throw new APIException(
                        "TestRail API return HTTP " + status +
                                " (No additional error message received)"
                );
            }
        } else {
            istream = conn.getInputStream();
        }

        // Read the response body, if any, and deserialize it from JSON.
        String text = "";
        if (istream != null) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            istream,
                            StandardCharsets.UTF_8
                    )
            );

            String line;
            while ((line = reader.readLine()) != null) {
                text += line + System.getProperty("line.separator");
            }

            reader.close();
        }

        Object result;
        result = text != "" ? JSONValue.parse(text) : new JSONObject();

        // Check for any occurred errors and add additional details to
        // the exception message, if any (e.g. the error message returned
        // by TestRail).
        if (status != 200) {
            String error = "No additional error message received";
            if (result instanceof JSONObject) {
                JSONObject obj = (JSONObject) result;
                if (obj.containsKey("error")) {
                    error = '"' + (String) obj.get("error") + '"';
                }
            }

            throw new APIException(
                    "TestRail API returned HTTP " + status +
                            "(" + error + ")"
            );
        }

        return result;
    }

    private static String getAuthorization(String user, String password) {
        return Base64.getEncoder().encodeToString((user + ":" + password).getBytes(StandardCharsets.UTF_8));
    }
}
