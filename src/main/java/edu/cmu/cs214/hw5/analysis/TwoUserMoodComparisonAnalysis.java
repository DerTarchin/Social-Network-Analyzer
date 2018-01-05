package edu.cmu.cs214.hw5.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import edu.cmu.cs214.hw5.core.AnalysisPlugin;
import edu.cmu.cs214.hw5.core.Data;
import edu.cmu.cs214.hw5.core.Filter;
import edu.cmu.cs214.hw5.core.Post;
import edu.cmu.cs214.hw5.core.Sentiment;
import edu.cmu.cs214.hw5.visualizer.BarChart;
import edu.cmu.cs214.hw5.visualizer.ControlPanel;
import edu.cmu.cs214.hw5.visualizer.DataPoint;
import edu.cmu.cs214.hw5.visualizer.TextFieldControl;
import edu.cmu.cs214.hw5.visualizer.Visualizer;

/**
 * This is an example of how to use the visualizer library to compare the moods
 * of two users in a Bar Graph.
 * 
 * @author JordanBrown
 */
public class TwoUserMoodComparisonAnalysis implements AnalysisPlugin {

  private TextFieldControl user1Control = new TextFieldControl();
  private TextFieldControl user2Control = new TextFieldControl();
  private Filter user1Filter;
  private Filter user2Filter;
  private BarChart chart = new BarChart();
  private Visualizer vis = new Visualizer();

  /**
   * 
   * In this method, we are given a JPanel which will be the JPanel this
   * analysis plugin gets to play with. This is called when the plugin is
   * registered, so we take the time here to do initial set up. The rest is
   * pretty self-explanatory.
   * 
   * @param JPanel
   *          the panel assigned to the analysis plugin.
   */
  @Override
  public void onRegister(JPanel panel) {
    vis.setTitle("User Moods");

    ControlPanel controlPanel = new ControlPanel();
    controlPanel.setLabel("Users");

    user1Control.setLabel("User 1");
    user2Control.setLabel("User 2");

    controlPanel.addControl(user1Control);
    controlPanel.addControl(user2Control);

    vis.addControlPanel(controlPanel);
    vis.addChart(chart);
    chart.setDetailed(true);

    vis.display();
    panel.add(vis);
  }

  /**
   * Here, we construct the filters so that we can get posts matching only a
   * certain user's name. The UserFilter takes in an argument that represents a
   * user's name and checks to make sure that the users that pass the filter
   * have that name.
   */
  @Override
  public List<Filter> getFilters() {

    // We fill it with the values in the textfield controls.
    user1Filter = new UserFilter(user1Control.getValue());
    user2Filter = new UserFilter(user2Control.getValue());

    List<Filter> filters = new ArrayList<Filter>();
    filters.add(user1Filter);
    filters.add(user2Filter);

    return filters;
  }

  /**
   * doAnalysis is the meat of the plugin. This is what gets called every
   * refresh.
   */
  @Override
  public void doAnalysis(Data data) {
    // We start by clearing all the data currently being displayed in our graph.
    chart.removeAllData();

    Set<Post> user1Posts = data.getPostsForFilter(user1Filter);
    Set<Post> user2Posts = data.getPostsForFilter(user2Filter);

    // In these next two loops, we sum the amount of positive, negative, and
    // neutral posts user 1 and user 2 have.
    int user1Pos = 0;
    int user1Neu = 0;
    int user1Neg = 0;
    for (Post post : user1Posts) {
      if (post.getSentiment() == Sentiment.POSITIVE) {
        user1Pos++;
      } else if (post.getSentiment() == Sentiment.NEUTRAL) {
        user1Neu++;
      } else {
        user1Neg++;
      }
    }

    int user2Pos = 0;
    int user2Neu = 0;
    int user2Neg = 0;

    for (Post post : user2Posts) {
      if (post.getSentiment() == Sentiment.POSITIVE) {
        user2Pos++;
      } else if (post.getSentiment() == Sentiment.NEUTRAL) {
        user2Neu++;
      } else {
        user2Neg++;
      }
    }

    chart.setTitle(user1Control.getValue() + " vs. " + user2Control.getValue());

    // Now we add all this information as data points to the graphs. They'll
    // display at the value of the third value in the constructor. If they share
    // the first value in the constructor, they'll be grouped together in the
    // chart. The second value assigns the colors in the bar graph. Since we
    // have two unique values that were passing into the second argument in the
    // constructor, our graph will have two colors, one for each user.

    DataPoint dataPointUser1Pos = new DataPoint("Positive", user1Control.getValue(), user1Pos);
    DataPoint dataPointUser1Neu = new DataPoint("Neutral", user1Control.getValue(), user1Neu);
    DataPoint dataPointUser1Neg = new DataPoint("Negative", user1Control.getValue(), user1Neg);
    DataPoint dataPointUser2Pos = new DataPoint("Positive", user2Control.getValue(), user2Pos);
    DataPoint dataPointUser2Neu = new DataPoint("Neutral", user2Control.getValue(), user2Neu);
    DataPoint dataPointUser2Neg = new DataPoint("Negative", user2Control.getValue(), user2Neg);

    // Now we just add all the data points to the chart.
    chart.addData(dataPointUser1Pos);
    chart.addData(dataPointUser1Neu);
    chart.addData(dataPointUser1Neg);
    chart.addData(dataPointUser2Pos);
    chart.addData(dataPointUser2Neu);
    chart.addData(dataPointUser2Neg);
    // And we end by refreshing the visualizer.
    vis.refresh();
  }

  /* {@inheritDoc} */
  @Override
  public String getName() {
    return "Compare Mood";
  }
}