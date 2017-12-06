package kr.jnu.embedded.snssearcher.core;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kr.jnu.embedded.snssearcher.data.TwitterItem;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by KANG on 2017-12-05.
 */

public class TwitterSearcherTask extends AsyncTask<String, Object, ArrayList<Object>>{
    private static final String TAG = "TwitterTask";
    private String keyword;
    private Twitter twitter;

    public TwitterSearcherTask(Twitter twitter) {
        this.twitter = twitter;
    }

    @Override
    protected ArrayList<Object> doInBackground(String... strings) {
        ArrayList<TwitterItem> result = new ArrayList<>();
        ArrayList<Object> returnArray = new ArrayList<>();

        Query query = new Query(strings[0]);
        QueryResult results;
        int count = 0;
        try {
            do{
                results = twitter.search(query);

                List<twitter4j.Status> tweets = results.getTweets();
                for (twitter4j.Status tweet : tweets){
                    Log.d(TAG, "Tweet: " + tweet.toString());
                    result.add(new TwitterItem(tweet.getUser().getScreenName(),
                            tweet.getUser().getBiggerProfileImageURL(),
                            tweet.getCreatedAt().toString(),
                            tweet.getText()
                            ,""));

                    count++;
                    Log.d(TAG, "Count : " + count);
                    if(count > 14){
                        returnArray.addAll(result);
                        return returnArray;
                    }
                }

            } while((query = results.nextQuery()) != null);

        }
        catch(TwitterException e){
            e.printStackTrace();
        }
        returnArray.addAll(result);
        return returnArray;
    }
}
