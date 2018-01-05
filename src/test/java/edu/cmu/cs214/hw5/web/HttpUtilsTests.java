package edu.cmu.cs214.hw5.web;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class HttpUtilsTests {

  /**
   * Tests that get requests are sent correctly.
   * 
   * @throws IOException
   *           If the request fails.
   */
  @Test
  public void testGet() throws IOException {
    String response = HttpUtils.sendGetRequest("http://httpbin.org/get?test=true");
    SimpleJsonObject jsonResponse = new SimpleJsonObject(response);
    assertTrue(jsonResponse.getJsonObject("args").getString("test").equals("true"));
  }

  /**
   * Tests that post requests are sent correctly.
   * 
   * @throws IOException
   *           If the request fails.
   */
  @Test
  public void testPost() throws IOException {
    Map<String, String> params = new HashMap<String, String>();
    params.put("Test1", "test");
    params.put("Test2", "2");
    String response = HttpUtils.sendPostRequest("http://httpbin.org/post", params);
    SimpleJsonObject jsonResponse = new SimpleJsonObject(response);
    assertTrue(jsonResponse.getJsonObject("headers").getString("Test1").equals("test"));
    assertTrue(jsonResponse.getJsonObject("headers").getString("Test2").equals("2"));
  }

}
