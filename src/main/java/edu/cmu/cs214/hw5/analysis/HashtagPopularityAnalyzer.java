package edu.cmu.cs214.hw5.analysis;

import edu.cmu.cs214.hw5.core.AnalysisPlugin;
import edu.cmu.cs214.hw5.core.Data;
import edu.cmu.cs214.hw5.core.Filter;
import edu.cmu.cs214.hw5.core.Post;
import edu.cmu.cs214.hw5.visualizer.Chart;
import edu.cmu.cs214.hw5.visualizer.ControlPanel;
import edu.cmu.cs214.hw5.visualizer.DataPoint;
import edu.cmu.cs214.hw5.visualizer.RadarChart;
import edu.cmu.cs214.hw5.visualizer.SliderControl;
import edu.cmu.cs214.hw5.visualizer.TextFieldControl;
import edu.cmu.cs214.hw5.visualizer.ToggleControl;
import edu.cmu.cs214.hw5.visualizer.Visualizer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

/**
 * Hashtag Popularity Analyzer Analyzes hashtags by hourly occurance, over the
 * specified interval of time
 * 
 * @author Hizal
 *
 */
public class HashtagPopularityAnalyzer implements AnalysisPlugin {

	private Filter dailyChartFilter;
	private TextFieldControl hashtagControl;
	private SliderControl hoursControl;
	private ToggleControl rtControl;
	private ToggleControl detailControl;
	private ArrayList<Filter> filters = new ArrayList<Filter>();
	private Visualizer vis;
	private Chart chart1;

	/**
	 * 
	 * In this method, we are given a JPanel which will be the JPanel this
	 * analysis plugin gets to play with. This is called when the plugin is
	 * registered, so we take the time here to do initial set up. The rest is
	 * pretty self-explanatory.
	 * 
	 * @param JPanel
	 *            the panel assigned to the analysis plugin.
	 */
	@Override
	public void onRegister(JPanel panel) {
		vis = new Visualizer();
		vis.setTitle(getName());

		ControlPanel cp = new ControlPanel();
		vis.addControlPanel(cp);

		// allows user to change the hashtag
		hashtagControl = new TextFieldControl();
		hashtagControl.setLabel("Hashtag:");
		hashtagControl.setDefaultValue("drunk");
		cp.addControl(hashtagControl);

		// allows user to change the number of hours to display
		hoursControl = new SliderControl(1, 72);
		hoursControl.setLabel("Number of Hours");
		hoursControl.setDefaultValue(24);
		cp.addControl(hoursControl);

		// allows the user to toggle retweets
		rtControl = new ToggleControl();
		rtControl.setLabel("Include Retweets");
		cp.addControl(rtControl);

		// allows user to toggle details display
		detailControl = new ToggleControl();
		detailControl.setLabel("Show Details");
		detailControl.setDefaultValue(true);
		cp.addControl(detailControl);

		// create the chart and title/detail settings
		chart1 = new RadarChart();
		chart1.setDetailed(true);
		chart1.setTitle("Occurances per Hour");
		vis.addChart(chart1);

		// display the visualizer and add to the jpanel
		vis.display();
		panel.add(vis);
	}

	/**
	 * this is where the plugin recieves a set of data matching the filter(s) it
	 * created, and creates the graph data based on informatio from the posts.
	 * In this case I am displaying the number of occurances of a particular
	 * hashtag every hour, over the given amount of time
	 */
	@Override
	public void doAnalysis(Data data) {
		chart1.removeAllData();
		Date today = new Date();

		ArrayList<Integer> hours = new ArrayList<Integer>();
		for (int i = 0; i < hoursControl.getValue(); i++)
			hours.add(Integer.valueOf(0));
		Set<Post> posts = data.getPostsForFilter(dailyChartFilter);
		for (Post post : posts) {
			Date postDate = post.getTime();
			int diff = (int) TimeUnit.HOURS.convert(
					(today.getTime() - postDate.getTime()),
					TimeUnit.MILLISECONDS);
			hours.add(diff, Integer.valueOf((hours.get(diff) + 1)));
			hours.remove(diff + 1);
		}

		for (int i = 0; i < hours.size(); i++) {
			String label = i + " Hours Ago";
			chart1.addData(new DataPoint(label, "morning", hours.get(i)));
		}
		chart1.setDetailed(detailControl.getValue());

		vis.refresh();
	}

	/**
	 * Get filters just returns the list of filters we created for this plugin.
	 * In this case, it checks if the post is not a retweet (assuming the toggle
	 * is off) and that it fits within the hours specified and has the same
	 * hashtag as specified
	 * 
	 */
	@Override
	public List<Filter> getFilters() {
		dailyChartFilter = new Filter() {
			@Override
			public boolean passesFilter(Post post) {
				if (post.getSource().equals("Twitter")
						&& post.getText().substring(0, 2).equals("RT")
						&& !rtControl.getValue())
					return false;
				else if (!post.getText().contains(
						"#" + hashtagControl.getValue()))
					return false;

				Date today = new Date();
				Date postDate = post.getTime();
				int diff = (int) TimeUnit.HOURS.convert(
						(today.getTime() - postDate.getTime()),
						TimeUnit.MILLISECONDS);
				if (diff >= hoursControl.getValue())
					return false;
				return true;
			}
		};
		filters.add(dailyChartFilter);
		return filters;
	}

	/**
	 * And get name returns the name we want to be displayed for the plugin.
	 * 
	 */
	@Override
	public String getName() {
		return "Hashtag Popularity";
	}
}
