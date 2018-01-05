
package edu.cmu.cs214.hw5.data;

import edu.cmu.cs214.hw5.core.DataPlugin;
import edu.cmu.cs214.hw5.core.Post;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * In this TwitterPhraseDataPlugin, the twitter4j external library is used in
 * order to pull all tweets containing a particular phrase, decided by the user
 * at runtime. Similar to the GitHubRepoDataPlugin, this plugin should help
 * provide a base guideline on how to create a data plugin.
 * 
 * @author Marcel Oyuela-Bonzani
 *
 */
public class TwitterPhraseDataPlugin implements DataPlugin {

  /**
   * This method does not need to be implemented in this example, so we just
   * print a message to confirm that everything is getting registered properly.
   */
  public void onRegister() {
    System.out.println("TwitterPhraseDataPlugin Registered!");
  }

  /**
   * Before we do anything, we first set query data so that, at runtime, a user
   * can input something to the "Phrase" field, and that will be later used by
   * the plugin. The first parameter is the key, and the second is our base
   * value for the key. We are looking for phrases, so we look for the user to
   * type a Phrase during runtime. This is handled by the framework, so all that
   * must be done is return the query that you want to use.
   */
  @Override
  public LinkedHashMap<String, String> getQueryData() {
    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
    map.put("phrase", "Phrase");
    return map;
  }

  /**
   * This is the bulk of the data plugin. Here, we are passed back the results
   * from the user's decisions at runtime related to the query we made above.
   * From there, we utilize the twitter4j API to do the following: 1: Create the
   * Twitter class 2: Create an empty list of posts that we can return later. 3:
   * Create a new Query class with the parameter given by the user giving us
   * something during runtime. 4: Since Twitter4J only accesses 100 posts at a
   * time, we first get 100 tweets, then convert them to a post, and finally add
   * them to the list of posts we store. 5: While doing that, we store the max
   * ID of the posts, so we can grab more. 6: We grab more posts and do the same
   * process as 4 by setting the max ID to what the max ID was from our prior
   * grab. 7: In the event of any errors, we catch them. 8: Finally, we return
   * the list of our posts.
   */
  @Override
  public List<Post> query(Map<String, String> myParams) {
    Twitter twitter = TwitterFactory.getSingleton();
    ArrayList<Post> posts = new ArrayList<Post>();
    try {
      Query query = new Query(myParams.get("phrase"));
      query.setCount(100);
      QueryResult result;
      result = twitter.search(query);
      List<Status> tweets = result.getTweets();
      long id = 0;
      for (Status tweet : tweets) {
        posts.add(new Post(tweet.getUser().getName(), tweet.getUser().getLocation(), tweet.getCreatedAt(),
            tweet.getText(), (tweet.getRetweetCount() + tweet.getFavoriteCount())));
        // System.out.println(tweet.getId());
        id = tweet.getId();
      }
      // Let's get 200 more!
      query.setMaxId(id);
      result = twitter.search(query);
      tweets = result.getTweets();
      for (Status tweet : tweets) {
        posts.add(new Post(tweet.getUser().getName(), tweet.getUser().getLocation(), tweet.getCreatedAt(),
            tweet.getText(), (tweet.getRetweetCount() + tweet.getFavoriteCount())));
        // System.out.println(tweet.getId());
      }

    } catch (TwitterException te) {
      te.printStackTrace();
      System.out.println("Failed to search tweets: " + te.getMessage());
    }
    System.out.println(posts.size());
    return posts;
  }

  /**
   * Self explanatory function that tells us this data plugin got posts from
   * twitter.
   */
  @Override
  public String getSource() {
    return "Twitter";
  }

  /**
   * This function returns the name of the data plugin.
   * 
   */
  @Override
  public String getName() {
    return "Twitter Phrases";
  }

}
