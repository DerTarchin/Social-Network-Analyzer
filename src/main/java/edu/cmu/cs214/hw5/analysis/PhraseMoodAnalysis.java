package edu.cmu.cs214.hw5.analysis;

import edu.cmu.cs214.hw5.core.AnalysisPlugin;
import edu.cmu.cs214.hw5.core.Data;
import edu.cmu.cs214.hw5.core.Filter;
import edu.cmu.cs214.hw5.core.Post;
import edu.cmu.cs214.hw5.core.Sentiment;
import edu.cmu.cs214.hw5.visualizer.BarChart;
import edu.cmu.cs214.hw5.visualizer.Chart;
import edu.cmu.cs214.hw5.visualizer.DataPoint;
import edu.cmu.cs214.hw5.visualizer.Visualizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

/**
 * PhraseMoodAnalysis is a simple analysis plugin meant to demonstrate three
 * primary ideas: 1: Creating Analysis Plugins for our framework is easy. 2:
 * Analysis Plugins created for the framework can easily use the Visualizer
 * library. 3: Analysis Plugins using the Visualizer library don't need to be
 * overly complicated.
 * 
 * This plugin analyzes posts by using the built in sentiment analysis for each
 * Post, and adds up the totals for each mood.
 * 
 * 
 * @author Marcel Oyuela-Bonzani
 *
 */
public class PhraseMoodAnalysis implements AnalysisPlugin {

  private Filter removeNonTweetsAndRT;
  private JPanel myPanel;
  private ArrayList<Filter> myFilters;
  private Visualizer vis;
  private Chart chart1;

  /**
   * The first thing we do is initialize the analysis plugin. Our plugin is
   * given a JPanel to use. From there, we can update it as need be with a bar
   * chart provided by the visualizer library.
   */
  @Override
  public void onRegister(JPanel panel) {
    this.myPanel = panel;

    /**
     * Here we create the filter for posts. This removes any retweets (If the
     * source is twitter), or just keeps the post as is, in case github is used.
     */
    this.removeNonTweetsAndRT = (new Filter() {
      @Override
      public boolean passesFilter(Post post) {
        if (post.getSource().equals("Twitter")) {
          return post.getText().substring(0, 2).equals("RT");
        }
        return true;
      }
    });
    this.myFilters = new ArrayList<Filter>();
    myFilters.add(removeNonTweetsAndRT);

    /**
     * Here we create the visualizer for the bar chart. This is actually very
     * easy. First we create the visualizer class, then we create a bar chart we
     * will use with it.
     */
    this.vis = new Visualizer();
    vis.setTitle(getName());
    this.chart1 = new BarChart();

    /**
     * We set some information about the bar chart, add it to the visualizer,
     * display the visualizer, and then add it to our jpanel.
     */
    chart1.setTitle("Mood vs Amount of Posts");
    chart1.setDetailed(true);
    vis.addChart(chart1);
    vis.display();

    this.myPanel.add(vis);

  }

  /**
   * Our main function for the analysis plugin is DoAnalysis. DoAnalysis takes
   * in Data given by the framework. This data contains any posts from the
   * DataPlugins that this analysis plugin is "linked" to (Determined by Main
   * and runtime).
   * 
   * Then we do the analysis itself.
   */
  @Override
  public void doAnalysis(Data data) {
    // We set up counters for each mood.
    int pos = 0;
    int neg = 0;
    int neu = 0;
    // We get the posts from the data, after passing it our filter we created.
    Set<Post> myPosts = data.getPostsForFilter(removeNonTweetsAndRT);
    // For each post in our filtered posts, we get the sentiment and increment
    // the corresponding mood value.
    for (Post post : myPosts) {
      Sentiment mood = post.getSentiment();
      if (mood == Sentiment.POSITIVE) {
        pos++;
      } else if (mood == Sentiment.NEGATIVE) {
        neg++;
      } else {
        neu++;
      }
    }

    /**
     * Finally, we reset our bar chart created in init, update it with our
     * values, and refresh the visualizer.
     * 
     * The values are set up in the following order in this analysis. Mood,
     * Group, Value
     * 
     * Mood is either Positive, Negative, or Neutral. Since we aren't grouping
     * data, Group is just "". Our value is the corresponding value, as
     * incremented and declared above.
     */
    chart1.removeAllData();
    chart1.addData(new DataPoint("Positive", "", pos));
    chart1.addData(new DataPoint("Negative", "", neg));
    chart1.addData(new DataPoint("Neutral", "", neu));

    vis.refresh();

  }

  /**
   * Get filters just returns the list of filters we created for this plugin.
   * 
   */
  @Override
  public List<Filter> getFilters() {
    return myFilters;
  }

  /**
   * And get name returns the name we want to be displayed for the plugin.
   * 
   */
  @Override
  public String getName() {
    return "Phrase Mood Analysis";
  }

}
