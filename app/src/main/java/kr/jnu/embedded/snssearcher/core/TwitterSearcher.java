package kr.jnu.embedded.snssearcher.core;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kr.jnu.embedded.snssearcher.base.App;
import kr.jnu.embedded.snssearcher.data.TwitterItem;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;


public class TwitterSearcher {
    private static String TAG = "TwitterSearcher";
    ArrayList<Object> result;
    String keyword;
    Twitter twitter;

    public TwitterSearcher() {
        TwitterFactory tf = new TwitterFactory(App.getTwitterAccessToken());
        twitter = tf.getInstance();
    }

    public ArrayList<Object> getTwitterSearch() {
        /*
        if(keyword.equals("") || keyword == null) return new ArrayList<>();
        try {
            Query query = new Query("test");
            QueryResult result;

            do {
                result = twitter.search(query);

                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    Log.d(TAG, tweet.toString());
                }

            } while ((query = result.nextQuery()) != null);

        } catch (TwitterException te) {

            te.printStackTrace();

            System.out.println("Failed to search tweets: " + te.getMessage());
        }
        return result;
        */
        return null;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
