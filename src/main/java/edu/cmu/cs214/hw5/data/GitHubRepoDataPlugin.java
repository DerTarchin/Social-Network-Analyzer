package edu.cmu.cs214.hw5.data;

import java.util.LinkedHashMap;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.cmu.cs214.hw5.core.DataPlugin;
import edu.cmu.cs214.hw5.core.Post;
import edu.cmu.cs214.hw5.web.HttpUtils;
import edu.cmu.cs214.hw5.web.SimpleJsonArray;
import edu.cmu.cs214.hw5.web.SimpleJsonObject;

/**
 * In the GitHubRepoDataPlugin, we examine the 100 most recent commit messages on a given github
 * repo.
 * 
 * This example should be very helpful in helping you to build your own plugins.
 * 
 * I encourage you to read through the methods (and their copious documentation)
 * in order of appearance in this file.
 * 
 * @author JordanBrown
 *
 */
public class GitHubRepoDataPlugin implements DataPlugin {

  /**
   * For sanity's sake, lets just print a message to the console on register.
   * Nothing else needs to be done here for this example.
   */
  public void onRegister() {
    System.out.println("GitHubRepoDataPlugin registered");
  }

  /**
   * First, we set the query parameters that should be built in the UI. The
   * framework handles this automatically, so all we need to do is tell it the
   * name of the parameter and a title for the textfield to input the parameter
   * value.
   * 
   * This code will create two textfields, one with the title "User" and the
   * other with the title "Repository". We will be able to access the values of
   * these fields in the query method via the myParam map.
   */
  @Override
  public LinkedHashMap<String, String> getQueryData() {
    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
    map.put("user", "User");
    map.put("repo", "Repository");
    return map;
  }

  /**
   * Here, we build the query string for the dataplugin, send a get request, and
   * parse the response. This is the meat of writing a DataPlugin.
   * 
   * The myParams map will have two keys: "user" and "repo", as we specified
   * above. The values in the textfields will be the values you receive by
   * looking up those keys in the myParams map.
   */
  @Override
  public List<Post> query(Map<String, String> myParams) {
    String response = "";
    List<Post> posts = new ArrayList<Post>();

    // We use HTTPUtils to send the GET request for us automatically. It will
    // return the response, and on error, will let us know that no commits could
    // be found.
    try {
      response = HttpUtils.sendGetRequest(
          "https://api.github.com/repos/" + myParams.get("user") + "/" + myParams.get("repo") + "/commits?per_page=100");
    } catch (IOException e) {
      System.out.println("No commits found for: " + myParams.get("user") + " at repo: " + myParams.get("repo"));
      return posts;
    }

    // Now we begin our parsing. Luckily, we have some classes that will assist
    // us in this process.

    // The SimpleJsonArray takes in JSON reprsenting an array and creates an
    // array
    // of SimpleJsonObjects. Note that SimpleJsonArray is NOT iterable
    // currently.
    SimpleJsonArray jsonArr = new SimpleJsonArray(response);

    // We use a SimpleDateFormat in order to parse the date into a Date class.
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
    for (int i = 0; i < jsonArr.size(); i++) {
      SimpleJsonObject postJson = jsonArr.getJsonObject(i);

      // SimpleJsonObjects allow you to easily access JSON fields. If a field
      // does
      // not exist, it'll print a message to the console saying so.
      String user = postJson.getJsonObject("commit").getJsonObject("author").getString("name");
      String location = null;
      String time = postJson.getJsonObject("commit").getJsonObject("author").getString("date");
      Date date = null;
      try {
        date = format.parse(time);
      } catch (ParseException e) {
        System.out.println("could not parse: " + time);
      }
      String text = postJson.getJsonObject("commit").getString("message");
      
      // Now we create the post and add it to our list of posts from the query.
      Post post = new Post(user, location, date, text, 0);
      posts.add(post);
    }

    return posts;
  }

  /**
   * In order to know where the posts come from, we ask you to provide us a name
   * for the source.
   */
  @Override
  public String getSource() {
    return "GitHub";
  }

  /**
   * Lastly, we use this value to create the title for your data plugin.
   */
  @Override
  public String getName() {
    return "GitHub Repos";
  }
}