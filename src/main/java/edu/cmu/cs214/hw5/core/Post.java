package edu.cmu.cs214.hw5.core;

import java.util.Date;

/**
 * The post class, analysis plugins analyze this and the framework builds it.
 * 
 * @author Team32
 *
 */
public class Post {
  private String user;
  private String location;
  private Date time;
  private String text;
  private String source;
  private String pluginName;
  private Sentiment sentiment;
  private int responses = 0;

  /*
   * This constructor is private to this package in order to make it easier on
   * plugin writers to not have to set the source and plugin names themselves.
   * Since they have a method that returns the source and plugin names, we opt
   * to input those fields for them instead of making sure they put the same
   * string in the constructor for the Post.
   */
  Post(Post post, String source, String pluginName, Sentiment sentiment) {
    if (post != null) {
      this.user = post.user;
      this.location = post.location;
      this.time = post.time;
      this.text = post.text;
    }
    this.source = source;
    this.pluginName = pluginName;
    this.sentiment = sentiment;
  }

  /**
   * Takes in a user, location, time, text, and responses to allow plugin
   * writers to create posts.
   * 
   * @param user
   *          Name of user.
   * @param location
   *          Name of location.
   * @param time
   *          Time of post.
   * @param text
   *          Text of post.
   * @param responses
   *          Number of responses to post.
   */
  public Post(String user, String location, Date time, String text, int responses) {
    if (user == null) {
      user = "";
    }

    if (location == null) {
      location = "";
    }

    if (text == null) {
      text = "";
    }

    this.user = user;
    this.location = location;
    if (time != null) {
      this.time = (Date) time.clone();
    }
    this.text = text;
    this.responses = responses;
  }

  /**
   * @return the user
   */
  public String getUser() {
    return user;
  }

  /**
   * @return the location
   */
  public String getLocation() {
    return location;
  }

  /**
   * @return the time
   */
  public Date getTime() {
    if (time == null) {
      return null;
    }

    return (Date) time.clone();
  }

  /**
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * @return the source
   */
  public String getSource() {
    return source;
  }

  /**
   * @return the pluginName
   */
  public String getPluginName() {
    return pluginName;
  }

  /**
   * @return the responses
   */
  public int getResponses() {
    return responses;
  }

  /**
   * @return the sentiment
   */
  public Sentiment getSentiment() {
    return sentiment;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   * 
   * We define equals to be if the user, text, location, and source are the
   * same.
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Post)) {
      return false;
    }

    Post comparison = (Post) obj;
    return (this.user.equals(comparison.user)) && (this.location.equals(comparison.location))
        && this.text.equals(comparison.text) && this.source.equals(comparison.source);
  }

  @Override
  public int hashCode() {
    int result = 32;
    int c;
    c = this.user.hashCode();
    if (location != null) {
      c = c + this.location.hashCode();
    }
    if (time != null) {
      c = c + this.time.hashCode();
    }
    if (text != null) {
      c = c + this.text.hashCode();
    }
    if (source != null) {
      c = c + this.source.hashCode();
    }
    result = 37 * result + c;
    return result;
  }
}