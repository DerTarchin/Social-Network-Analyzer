package edu.cmu.cs214.hw5.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import java.awt.FlowLayout;

/**
 * The framework. In most cases, these functions should not be called. Unless a
 * function specifies it should be called willingly, generally not a good idea
 * to call it.
 * 
 * @author Team32
 *
 */
public class Framework {
  private ArrayList<DataPlugin> dataPlugins = new ArrayList<DataPlugin>();
  private ArrayList<AnalysisPlugin> analysisPlugins = new ArrayList<AnalysisPlugin>();
  private Map<DataPlugin, HashSet<Post>> postMap = new HashMap<DataPlugin, HashSet<Post>>();
  private Map<AnalysisPlugin, JPanel> panelMap = new HashMap<AnalysisPlugin, JPanel>();
  private Map<AnalysisPlugin, HashSet<DataPlugin>> dataSourceMap = new HashMap<AnalysisPlugin, HashSet<DataPlugin>>();
  private SentimentClassifier sentimentClassifier = new SentimentClassifier();

  /**
   * Adds a data source to the specified analysis plugin. This means that the
   * posts form this data plugin will be included in the results for the given
   * analysis plugin, assuming they pass the filters.
   * 
   * @param analysis
   *          Analysis plugin.
   * @param dataSource
   *          Data plugin.
   */
  public void addDataSourceForAnalysisPlugin(AnalysisPlugin analysis, DataPlugin dataSource) {
    HashSet<DataPlugin> sources = dataSourceMap.get(analysis);
    if (sources == null) {
      sources = new HashSet<DataPlugin>();
    }

    sources.add(dataSource);

    dataSourceMap.put(analysis, sources);
  }

  /**
   * Removes a data source from an analysis plugin.
   * 
   * @param analysis
   *          The Analysis plugin.
   * @param dataSource
   *          The DataPlugin data source.
   */
  public void removeDataSourceForAnalysisPlugin(AnalysisPlugin analysis, DataPlugin dataSource) {
    if (dataSourceMap.get(analysis) != null) {
      dataSourceMap.get(analysis).remove(dataSource);
    }
  }

  /**
   * Gets the Data Plugins that feed data to the input analysis plugin.
   * 
   * @param plugin
   *          The analysis plugin to get the datasources for.
   * @return The data sources for the Analysis Plugin.
   */
  public Set<DataPlugin> getDataSourcesForAnalysisPlugin(AnalysisPlugin plugin) {
    return this.dataSourceMap.get(plugin);
  }

  /**
   * refresh refreshes the data plugins in case the filters being used have
   * changed. Even if they haven't changed, refresh re-gets all filters,
   * re-filters the posts, and passes the respective posts to the respective
   * analysis plugins, to be analyzed.
   */
  public void refresh() {
    for (AnalysisPlugin plugin : analysisPlugins) {
      Map<Filter, Set<Post>> postsByFilter = new HashMap<Filter, Set<Post>>();
      List<Filter> filters = plugin.getFilters();

      Set<Post> posts = new HashSet<Post>();

      HashSet<DataPlugin> dataSources = dataSourceMap.get(plugin);
      if (dataSources != null) {
        for (DataPlugin dataSource : dataSources) {
          if (postMap.get(dataSource) != null) {
            posts.addAll(postMap.get(dataSource));
          }
        }
      }

      Data data = null;
      if (filters != null) {
        for (Filter filter : filters) {
          Set<Post> filteredPosts = new HashSet<Post>();
          for (Post post : posts) {
            if (filter.passesFilter(post)) {
              filteredPosts.add(post);
            }
          }
          postsByFilter.put(filter, filteredPosts);
        }
        data = new Data(postsByFilter);
      } else {
        data = new Data(posts);
      }
      plugin.doAnalysis(data);
    }
  }

  /**
   * This implementation of registerDataPlugin just takes in the Data plugin we
   * are registering to our framework.
   * 
   * @param plugin
   *          the aforementioned plugin.
   */
  public void registerDataPlugin(DataPlugin plugin) {
    dataPlugins.add(plugin);
    plugin.onRegister();
  }

  /**
   * This implementation of registerAnalysisPlugin takes in a the Analysis
   * plugin, and registers it to our framework.
   * 
   * @param plugin
   *          the Analysis plugin.
   */
  public void registerAnalysisPlugin(AnalysisPlugin plugin) {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    plugin.onRegister(panel);
    panelMap.put(plugin, panel);
    analysisPlugins.add(plugin);
  }

  /**
   * removeDataPlugin takes in a data plugin and removes it from our framework.
   * 
   * @param plugin
   *          the plugin we are removing
   */
  public void removeDataPlugin(DataPlugin plugin) {
    dataPlugins.remove(plugin);
    // We remove the plugin from anything that requires it.
    for (AnalysisPlugin currPlugin : analysisPlugins) {
      if (this.dataSourceMap.get(currPlugin) != null) {
        this.dataSourceMap.get(currPlugin).remove(plugin);
      }
    }
  }

  /**
   * removeAnalysisPlugin takes in an analysis plugin and removes it from our
   * framework.
   * 
   * @param plugin
   *          the plugin we are removing.
   */
  public void removeAnalysisPlugin(AnalysisPlugin plugin) {
    analysisPlugins.remove(plugin);
    panelMap.remove(plugin);
    dataSourceMap.remove(plugin);
  }

  /**
   * loadData utilizes the data plugin to load all the posts for that singular
   * data plugin. It does this by passing the parameters into the plugin, asking
   * for that string to be converted into strings of posts, and then asking for
   * individual parts of the posts from those strings. We then store all posts
   * created this way.
   * 
   * @param plugin
   *          the data plugin for which you want to load data.
   * @param params
   *          the parameters from the text field inputs.
   */
  public void loadData(DataPlugin plugin, Map<String, String> params) {
    List<Post> responsePosts = plugin.query(params);
    if (responsePosts == null) {
      System.out.println("No Posts for plugin " + plugin.getName());
      return;
    }

    HashSet<Post> posts = new HashSet<Post>();

    for (Post post : responsePosts) {
      Post frameworkPost = new Post(post, plugin.getSource(), plugin.getName(),
          this.getSentimentAnalysis(post.getText()));
      posts.add(frameworkPost);
    }
    this.postMap.put(plugin, posts);
    refresh();
  }

  /**
   * This provides sentiment analysis for the posts you're analyzing.
   * 
   * @param text
   *          Text to perform sentiment analysis on.
   * @return a Sentiment value from the Sentiment enum.
   */
  public Sentiment getSentimentAnalysis(String text) {
    return sentimentClassifier.classify(text);
  }

  /**
   * getDataPlugins returns a list of data plugins tied to the framework.
   * 
   * @return the dataPlugins
   */
  public ArrayList<DataPlugin> getDataPlugins() {
    return dataPlugins;
  }

  /**
   * getAnalysisPlugins returns a list of analysis plugins tied to the
   * framework.
   * 
   * @return the analysisPlugins
   */
  public ArrayList<AnalysisPlugin> getAnalysisPlugins() {
    return analysisPlugins;
  }

  /**
   * getQueryParametersForPlugin gets the mapping from string to string from the
   * plugin and uses that in the GUI to provide a way to change the values used
   * for the params in the plugin.
   * 
   * @param plugin
   *          the plugin we are looking at
   * @return the mapping from parameter to value.
   */
  public Map<String, String> getQueryParametersForPlugin(DataPlugin plugin) {
    return plugin.getQueryData();
  }

  /**
   * this get's the Jpanel for the Analysis Plugin.
   * 
   * @param plugin
   *          the analysis plugin we want the Jpanel for
   * @return the jpanel of the plugin.
   */
  public JPanel getJPanelForPlugin(AnalysisPlugin plugin) {
    return panelMap.get(plugin);
  }
}