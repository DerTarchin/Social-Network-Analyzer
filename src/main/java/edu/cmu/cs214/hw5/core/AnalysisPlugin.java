package edu.cmu.cs214.hw5.core;

import java.util.List;

import javax.swing.JPanel;

/**
 * Analysis Plugin interface.
 * 
 * @author Team32
 *
 */
public interface AnalysisPlugin {

  /**
   * doAnalysis takes in a mapping from filters to our posts. By doing the map
   * from filter to posts, you can get the posts that pass each filter and
   * analyze them however you would like.
   * 
   * @param data
   *          a data object containing the posts.
   */
  void doAnalysis(Data data);

  /**
   * onRegister is called when the plugin is registered with the Framework. The
   * JPanel passed in is the panel the plugin has access to. Usually you'll want
   * to save this in a private variable. It is the plugin's responsibility to
   * update the JPanel as you need.
   * 
   * @param panel
   *          the jpanel for this plugin.
   */
  void onRegister(JPanel panel);

  /**
   * getFilters returns a list of filters that the framework will use to filter
   * out posts accoding to your filter specifications.
   * 
   * @return The list of filters applicable to this plugin.
   */
  List<Filter> getFilters();

  /**
   * getName returns the name of the plugin.
   * 
   * @return the name of the plugin in string format.
   */
  String getName();
}
