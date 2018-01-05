package edu.cmu.cs214.hw5.core;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

public class FrameworkTests {
  private Framework framework;

  /**
   * Set up that is done before every test case. Just creates a Framework for
   * us.
   * 
   * @throws Exception
   */
  @Before
  public void setUp() {
    framework = new Framework();
  }

  /**
   * Tests that adding a data plugin works correctly.
   */
  @Test
  public void testAddDataPlugin() {
    TestDataPlugin dataPlugin = new TestDataPlugin();
    framework.registerDataPlugin(dataPlugin);
    assertTrue(framework.getDataPlugins().contains(dataPlugin));
  }

  /**
   * Tests that adding an analysis plugin works correctly.
   */
  @Test
  public void testAddAnalysisPlugin() {
    TestAnalysisPlugin analysisPlugin = new TestAnalysisPlugin();
    framework.registerAnalysisPlugin(analysisPlugin);
    assertTrue(framework.getAnalysisPlugins().contains(analysisPlugin));
  }

  /**
   * Tests that adding a data source works correctly.
   */
  @Test
  public void testAddDataSource() {
    TestDataPlugin dataPlugin = new TestDataPlugin();
    framework.registerDataPlugin(dataPlugin);
    TestAnalysisPlugin analysisPlugin = new TestAnalysisPlugin();
    framework.registerAnalysisPlugin(analysisPlugin);
    framework.addDataSourceForAnalysisPlugin(analysisPlugin, dataPlugin);
    TestDataPlugin dataPlugin2 = new TestDataPlugin();
    framework.registerDataPlugin(dataPlugin2);
    assertTrue(framework.getDataSourcesForAnalysisPlugin(analysisPlugin).contains(dataPlugin));
    assertFalse(framework.getDataSourcesForAnalysisPlugin(analysisPlugin).contains(dataPlugin2));
  }

  /**
   * Makes sure refreshing on empty does not crash anything.
   */
  @Test
  public void testRefreshEmpty() {
    framework.refresh();
  }

  /**
   * Another crash test for refreshing with no analysis plugins.
   */
  @Test
  public void testRefreshNoAnalysis() {
    TestDataPlugin dataPlugin = new TestDataPlugin();
    framework.registerDataPlugin(dataPlugin);
    framework.refresh();
  }

  /**
   * Yet another crash test for refreshing with no data.
   */
  @Test
  public void testRefreshNoData() {
    TestAnalysisPlugin analysisPlugin = new TestAnalysisPlugin();
    framework.registerAnalysisPlugin(analysisPlugin);
    framework.refresh();
  }

  /**
   * Final refresh test for refreshing when data does exist.
   */
  @Test
  public void testRefresh() {
    TestDataPlugin dataPlugin = new TestDataPlugin();
    framework.registerDataPlugin(dataPlugin);
    TestAnalysisPlugin analysisPlugin = new TestAnalysisPlugin();
    framework.registerAnalysisPlugin(analysisPlugin);
    framework.addDataSourceForAnalysisPlugin(analysisPlugin, dataPlugin);
    framework.refresh();
  }

  /**
   * Tests removing a data plugin works correctly.
   */
  @Test
  public void testRemoveDataPlugin() {
    TestDataPlugin dataPlugin = new TestDataPlugin();
    framework.registerDataPlugin(dataPlugin);
    assertTrue(framework.getDataPlugins().contains(dataPlugin));
    framework.removeDataPlugin(dataPlugin);
    assertFalse(framework.getDataPlugins().contains(dataPlugin));
  }

  /**
   * Tests removing an analysis plugin works correctly.
   */
  @Test
  public void testRemoveAnalysisPlugin() {
    TestAnalysisPlugin analysisPlugin = new TestAnalysisPlugin();
    framework.registerAnalysisPlugin(analysisPlugin);
    assertTrue(framework.getAnalysisPlugins().contains(analysisPlugin));
    framework.removeAnalysisPlugin(analysisPlugin);
    assertFalse(framework.getAnalysisPlugins().contains(analysisPlugin));
  }

  /**
   * Tests that getting the query parameters works correctly.
   */
  @Test
  public void testGetQueryParameters() {
    TestDataPluginWithPosts plugin = new TestDataPluginWithPosts();
    framework.registerDataPlugin(plugin);
    Map<String, String> params = framework.getQueryParametersForPlugin(plugin);
    assertTrue(params.get("user").equals("User"));
  }

  /**
   * Tests that getting the jpanel for a plugin isnt goofy.
   */
  @Test
  public void testGetJPanelForPlugin() {
    TestAnalysisPlugin plugin = new TestAnalysisPlugin();
    framework.registerAnalysisPlugin(plugin);
    JPanel panel = framework.getJPanelForPlugin(plugin);

    // Ensure nothing too goofy is going on here.
    assertTrue(panel == framework.getJPanelForPlugin(plugin));
    assertTrue(null == framework.getJPanelForPlugin(null));
  }

  /**
   * Tests that load data works without crashing.
   */
  @Test
  public void testLoadData() {
    TestDataPlugin dataPlugin = new TestDataPlugin();
    framework.registerDataPlugin(dataPlugin);
    framework.loadData(dataPlugin, null);
  }

  /**
   * Tests that refreshing and loading with posts works correctly
   */
  @Test
  public void testRefreshAndLoadWithPosts() {
    TestDataPluginWithPosts dataPlugin = new TestDataPluginWithPosts();
    framework.registerDataPlugin(dataPlugin);
    TestAnalysisPlugin analysisPlugin = new TestAnalysisPlugin();
    framework.registerAnalysisPlugin(analysisPlugin);
    framework.addDataSourceForAnalysisPlugin(analysisPlugin, dataPlugin);
    Map<String, String> params = framework.getQueryParametersForPlugin(dataPlugin);
    framework.loadData(dataPlugin, params);
  }

  /**
   * Tests that filtering posts works correctly.
   */
  @Test
  public void testFiltering() {
    TestDataPluginWithPosts dataPlugin = new TestDataPluginWithPosts();
    framework.registerDataPlugin(dataPlugin);
    TestAnalysisPlugin analysisPlugin = new TestAnalysisPlugin();
    framework.registerAnalysisPlugin(analysisPlugin);
    framework.addDataSourceForAnalysisPlugin(analysisPlugin, dataPlugin);
    Map<String, String> params = framework.getQueryParametersForPlugin(dataPlugin);
    framework.loadData(dataPlugin, params);
    Set<Post> posts = analysisPlugin.getPosts();
    assertTrue(posts.size() == 1);
    for (Post post : posts) {
      assertTrue(post.getUser().equals("User"));
    }
  }

  /**
   * Tests that the posts are still passed to plugins even if they do not supply
   * filters.
   */
  @Test
  public void testNoFiltering() {
    TestDataPluginWithPosts dataPlugin = new TestDataPluginWithPosts();
    framework.registerDataPlugin(dataPlugin);
    TestAnalysisPluginNoFilter analysisPlugin = new TestAnalysisPluginNoFilter();
    framework.registerAnalysisPlugin(analysisPlugin);
    framework.addDataSourceForAnalysisPlugin(analysisPlugin, dataPlugin);
    Map<String, String> params = framework.getQueryParametersForPlugin(dataPlugin);
    framework.loadData(dataPlugin, params);
    Set<Post> posts = analysisPlugin.getPosts();
    assertTrue(posts.size() == 3);

    for (Post post : posts) {
      assertTrue(post.getUser().equals("User") || post.getUser().equals("User2") || post.getUser().equals("User3"));
    }
  }

  /**
   * Tests that removing a data plugin tied to an analysis plugin works.
   */
  @Test
  public void testRemoveDataPluginSource() {
    TestDataPluginWithPosts dataPlugin = new TestDataPluginWithPosts();
    framework.registerDataPlugin(dataPlugin);
    TestAnalysisPluginNoFilter analysisPlugin = new TestAnalysisPluginNoFilter();
    framework.registerAnalysisPlugin(analysisPlugin);
    framework.addDataSourceForAnalysisPlugin(analysisPlugin, dataPlugin);
    Map<String, String> params = framework.getQueryParametersForPlugin(dataPlugin);
    framework.loadData(dataPlugin, params);
    Set<Post> posts = analysisPlugin.getPosts();
    assertTrue(posts.size() == 3);

    // now we remove the data plugin.
    framework.removeDataPlugin(dataPlugin);
    assertFalse(framework.getDataPlugins().contains(dataPlugin));
    framework.refresh();
    posts = analysisPlugin.getPosts();
    assertTrue(posts.size() == 0);
  }
  
  /**
   * Tests to make sure that removing a data source works
   */
  @Test
  public void testRemoveDataSource() {
    TestDataPluginWithPosts dataPlugin = new TestDataPluginWithPosts();
    framework.registerDataPlugin(dataPlugin);
    TestAnalysisPluginNoFilter analysisPlugin = new TestAnalysisPluginNoFilter();
    framework.registerAnalysisPlugin(analysisPlugin);
    framework.addDataSourceForAnalysisPlugin(analysisPlugin, dataPlugin);
    assertTrue(framework.getDataSourcesForAnalysisPlugin(analysisPlugin).contains(dataPlugin));
    framework.removeDataSourceForAnalysisPlugin(analysisPlugin, dataPlugin);
    assertFalse(framework.getDataSourcesForAnalysisPlugin(analysisPlugin).contains(dataPlugin));
    framework.removeDataSourceForAnalysisPlugin(analysisPlugin, dataPlugin);
    assertFalse(framework.getDataSourcesForAnalysisPlugin(analysisPlugin).contains(dataPlugin));
  }
}
