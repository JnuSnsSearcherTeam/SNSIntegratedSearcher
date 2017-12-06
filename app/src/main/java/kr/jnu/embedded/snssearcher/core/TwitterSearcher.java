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
        result = new ArrayList<Object>();
    }

    public ArrayList<Object> getTwitterSearch() {
        
        
            Query query = new Query("test");
            QueryResult results;

            do {
                results = twitter.search(query);

                List<Status> tweets = results.getTweets();
                for (Status tweet : tweets) {
        result.add(new TwitterItem(tweet.getUser().getScreenName(),
                                tweet.getUser().getBiggerProfileImageURL().toString(),
                                tweet.getCreatedAt().toString(),
                                tweet.getText()));
                    
                return result;
                }

            } while ((query = results.nextQuery()) != null);
        
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
