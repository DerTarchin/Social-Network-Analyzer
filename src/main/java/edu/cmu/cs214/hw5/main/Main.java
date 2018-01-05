package edu.cmu.cs214.hw5.main;

import edu.cmu.cs214.hw5.analysis.HashtagPopularityAnalyzer;
import edu.cmu.cs214.hw5.analysis.PhraseMoodAnalysis;
import edu.cmu.cs214.hw5.analysis.TwoUserMoodComparisonAnalysis;
import edu.cmu.cs214.hw5.analysis.VisualizerDemo;
import edu.cmu.cs214.hw5.core.AnalysisPlugin;
import edu.cmu.cs214.hw5.core.DataPlugin;
import edu.cmu.cs214.hw5.core.Framework;
import edu.cmu.cs214.hw5.data.GitHubRepoDataPlugin;
import edu.cmu.cs214.hw5.data.TwitterPhraseDataPlugin;
import edu.cmu.cs214.hw5.gui.AnalyzerGUI;

/**
 * Main class for the JMH SNAnalyzer framework
 * 
 * @author Hizal, Jordan, Marcel
 *
 */
public class Main {
  /**
   * initialize/register any plugins
   * start the GUI and framework
   * 
   * @param args arguments
   */
  public static void main(String[] args) {
    // initialize framework and gui
    Framework framework = new Framework();
    AnalyzerGUI gui = new AnalyzerGUI(framework);

    // initialize plugins to register
    DataPlugin gitHubRepo = new GitHubRepoDataPlugin();
    DataPlugin twitterPhraseData = new TwitterPhraseDataPlugin();
    AnalysisPlugin visDemo = new VisualizerDemo();
    AnalysisPlugin mood = new PhraseMoodAnalysis();
    AnalysisPlugin twoMood = new TwoUserMoodComparisonAnalysis();
    AnalysisPlugin hashtagPopularity = new HashtagPopularityAnalyzer();

    // register plugins
    framework.registerDataPlugin(gitHubRepo);
    framework.registerDataPlugin(twitterPhraseData);
    // Not displaying this for now.
    framework.registerAnalysisPlugin(visDemo);
    framework.registerAnalysisPlugin(twoMood);
    framework.registerAnalysisPlugin(mood);
    framework.registerAnalysisPlugin(hashtagPopularity);
    framework.addDataSourceForAnalysisPlugin(twoMood, gitHubRepo);
    framework.addDataSourceForAnalysisPlugin(mood, gitHubRepo);
    framework.addDataSourceForAnalysisPlugin(hashtagPopularity, twitterPhraseData);
    framework.addDataSourceForAnalysisPlugin(hashtagPopularity, gitHubRepo);

    // display GUI
    gui.display();
  }

}
