package edu.cmu.cs214.hw5.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Change-able class that wraps posts and meta-data.
 * 
 * @author Team32
 *
 */
public class Data {

  private Map<Filter, Set<Post>> postMap = new HashMap<Filter, Set<Post>>();
  private Set<Post> posts;

  Data(Map<Filter, Set<Post>> myPosts) {
    this.postMap = myPosts;
    posts = new HashSet<Post>();
    for (Entry<Filter, Set<Post>> entry : postMap.entrySet()) {
      posts.addAll(entry.getValue());
    }
  }

  Data(Set<Post> posts) {
    this.posts = posts;
  }

  /**
   * Returns the posts this data class is holding.
   * 
   * @return all the posts for every filter.
   */
  public Set<Post> getPosts() {
    return posts;
  }

  /**
   * Returns the posts in the Data object that passed the input filter.
   * 
   * @param filter
   *          Filter to get posts for.
   * @return The posts that passed the input filter.
   */
  public Set<Post> getPostsForFilter(Filter filter) {
    return postMap.get(filter);
  }
}
