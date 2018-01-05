package edu.cmu.cs214.hw5.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestDataPluginWithPosts implements DataPlugin {

  @Override
  public void onRegister() {
  }
  
  @Override
  public List<Post> query(Map<String, String> myParams) {
    Post post1 = new Post("User", "Location", null, "Neutral", 0);
    Post post2 = new Post("User2", "Location", null, "I'm So happy!", 0); 
    Post post3 = new Post("User3", "Location", null, "Fuck", 0); // For negative sentiment!
    
    List<Post> posts = new ArrayList<Post>();
    posts.add(post1);
    posts.add(post2);
    posts.add(post3);
    return posts;
  }

  @Override
  public String getSource() {
    return "Test Source";
  }

  @Override
  public String getName() {
    return "Test Name";
  }

  @Override
  public LinkedHashMap<String, String> getQueryData() {
    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
    map.put("user", "User");
    return map;
  }

}
