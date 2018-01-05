package edu.cmu.cs214.hw5.web;

import java.util.Map;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A util class for common HTTP methods. Useful for writing data plugins
 * 
 * @author JordanBrown
 *
 */
public class HttpUtils {

  /**
   * Sends a GET request and returns the response.
   * 
   * @param requestString
   *          The request to execute.
   * @return The request response.
   * @throws IOException
   *           If the request is malformed or errored.
   */
  public static String sendGetRequest(String requestString) throws IOException {
    URL url = new URL(requestString);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();

    con.setRequestMethod("GET");

    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();
    return response.toString();
  }

  /**
   * Sends a POST request and returns the response.
   * 
   * @param requestString
   *          The request to execute.
   * @param params
   *          the parameters to add to the post request.
   * @return The response to the request.
   * @throws IOException
   *           If the request is malformed or errored.
   */
  public static String sendPostRequest(String requestString, Map<String, String> params) throws IOException {
    URL url = new URL(requestString);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();

    con.setRequestMethod("POST");
    for (Map.Entry<String, String> entry : params.entrySet()) {
      con.setRequestProperty(entry.getKey(), entry.getValue());
    }

    con.setDoOutput(true);
    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
    wr.flush();
    wr.close();

    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();

    return response.toString();
  }
}
