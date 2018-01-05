package edu.cmu.cs214.hw5.analysis;

import edu.cmu.cs214.hw5.core.Filter;
import edu.cmu.cs214.hw5.core.Post;

/**
 * An example filter class that filters by user.
 * 
 * @author JordanBrown
 *
 */
public class UserFilter implements Filter {

  private String user = "";

  /**
   * Constructs a user filter based on some user string.
   * 
   * @param user
   *          User string.
   */
  public UserFilter(String user) {
    this.user = user;
  }

  /** {@inheritDoc} */
  @Override
  public boolean passesFilter(Post post) {
    return post.getUser().equals(user);
  }
}
