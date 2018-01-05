package edu.cmu.cs214.hw5.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

public class TestAnalysisPluginNoFilter implements AnalysisPlugin {
  Set<Post> posts = new HashSet<Post>();
  
  @Override
  public void doAnalysis(Data data) {
    this.posts = data.getPosts();
  }

  @Override
  public void onRegister(JPanel panel) {
  }

  @Override
  public List<Filter> getFilters() {
    return null;
  }

  @Override
  public String getName() {
    return "Test no filter";
  }
  
  public Set<Post> getPosts() {
    return this.posts;
  }

}
