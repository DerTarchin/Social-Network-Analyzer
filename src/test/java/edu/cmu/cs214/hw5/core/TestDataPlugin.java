package edu.cmu.cs214.hw5.core;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.cmu.cs214.hw5.core.DataPlugin;
import edu.cmu.cs214.hw5.core.Post;

public class TestDataPlugin implements DataPlugin {

  @Override
  public void onRegister() {
  }

  @Override
  public List<Post> query(Map<String, String> myParams) {
    return null;
  }

  @Override
  public String getSource() {
    return "Test Source";
  }

  @Override
  public String getName() {
    return "Test Plugin";
  }

  @Override
  public LinkedHashMap<String, String> getQueryData() {
    return null;
  }

}
