package kr.jnu.embedded.snssearcher;

import org.junit.Test;

import java.util.List;

import kr.jnu.embedded.snssearcher.base.App;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by KANG on 2017-12-05.
 */

public class TwitterTest2 {
    @Test
    public void testAccessToken(){

        TwitterFactory tf = new TwitterFactory(App.getTwitterAccessToken());
        Twitter twitter = tf.getInstance();

        try {
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

        } catch (TwitterException te) {

            te.printStackTrace();

            System.out.println("Failed to search tweets: " + te.getMessage());
        }
    }
}
