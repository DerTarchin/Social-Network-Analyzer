package edu.cmu.cs214.hw5.core;

/**
 * Filter interface.
 * 
 * @author Team32
 *
 *         A reusable filter for your Analysis plugins. If the filter is
 *         inclduded in your Analysis plugin's list of filters, it'll only
 *         receive data that passes the passesFilter method.
 */
public interface Filter {

  /**
   * Filter takes in a post and tells us if it passes the filter, or not.
   * 
   * @param post
   *          the post we are checking.
   * @return True if the filter passes, false otherwise.
   */
  boolean passesFilter(Post post);
}
