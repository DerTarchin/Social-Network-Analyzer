package edu.cmu.cs214.hw5.core;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The Data Plugin Interface
 * 
 * @author Team32
 *
 */
public interface DataPlugin {

  /**
   * Called when the DataPlugin is registered with the Framework.
   */
  void onRegister();
  
  /**
   * query takes in a mapping from string to string as a method of allowing the
   * data plugin to change what data it searches in accordance to what the user
   * types in as its parameters. It then returns all of the posts that were
   * received from that query.
   * 
   * @param myParams
   *          the Parameters mapping from parameter (string) to value (string).
   * @return a list of posts from the query.
   */
  List<Post> query(Map<String, String> myParams);

  /**
   * getSource tells us what the source of our data is, eg: twitter, github,
   * etc.
   * 
   * @return the source of our data.
   */
  String getSource();

  /**
   * getName tells us the name of the plugin.
   * 
   * @return the name of this plugin.
   */
  String getName();

  /**
   * getQueryData returns a mapping from string to string, where the first
   * string is our parameter and the second string is the displayable title for
   * the text field.
   * 
   * For example: "user": "Username" "location": "Location"
   * 
   * This would create two textfields, one with the title Username and the other
   * with the title Location. In the query method, the values of these
   * textfields will be contained at myParams.get("user") and
   * myParams.get("location").
   * 
   * The framework creates the GUI for passing in new results for each
   * parameter, so feel free to create complicated, or simple, data plugins!
   * 
   * @return the mapping from parameter to value.
   */
  LinkedHashMap<String, String> getQueryData();
}
