package edu.cmu.cs214.hw5.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import edu.cmu.cs214.hw5.visualizer.BarChart;
import edu.cmu.cs214.hw5.visualizer.Chart;
import edu.cmu.cs214.hw5.visualizer.DataPoint;
import edu.cmu.cs214.hw5.visualizer.Visualizer;

public class TestAnalysisPlugin implements AnalysisPlugin {
  private Filter removeNonTweetsAndRT;
  private JPanel myPanel;
  private ArrayList<Filter> myFilters;
  private Visualizer vis;
  private Chart chart1;
  private Set<Post> posts;


  @Override
  public void doAnalysis(Data data) {
    int pos = 0;
    int neg = 0;
    int neu = 0;
    Set<Post> myPosts = data.getPostsForFilter(removeNonTweetsAndRT);
    posts = myPosts;
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
    chart1.removeAllData();
    chart1.addData(new DataPoint("Positive", "", pos));
    chart1.addData(new DataPoint("Negative", "", neg));
    chart1.addData(new DataPoint("Neutral", "", neu));
    
    vis.refresh();
    

  }

  @Override
  public void onRegister(JPanel panel) {
    this.myPanel = panel;
    this.removeNonTweetsAndRT = (new Filter(){
      @Override
    public boolean passesFilter(Post post) {
        return post.getUser().equals("User");
      }
    });
    this.myFilters = new ArrayList<Filter>();
    myFilters.add(removeNonTweetsAndRT);

    
  this.vis = new Visualizer();
    vis.setTitle(getName());
    this.chart1 = new BarChart();
    
    chart1.setTitle("Mood vs Amount of Posts");
    chart1.setDetailed(true);
    vis.addChart(chart1);
    vis.display();
    
    
    this.myPanel.add(vis);
    
  }

  @Override
  public List<Filter> getFilters() {
    return myFilters;
  }

  @Override
  public String getName() {
    return "Phrase Mood Analysis";
  }
  
  public Set<Post> getPosts() {
    return posts;
  }

}
