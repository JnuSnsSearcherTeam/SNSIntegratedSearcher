package kr.jnu.embedded.snssearcher.base;

import android.app.Application;

import com.facebook.AccessToken;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Shane on 2017. 12. 1..
 */

public class App extends Application {
    public final static List<Item> facebookItem = new ArrayList<>();
    public final static List<Item> twitterItem = new ArrayList<>();
    public final static List<Item> instagramItem = new ArrayList<>();
    public static AccessToken faceBookAccessToken;
    public static String instagramAccessToken;
    public static Configuration twitterAccessToken;
    @Override
    public void onCreate(){
        super.onCreate();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_stub) // resource or drawable
//                .showImageForEmptyUri(R.drawable.ic_empty) // resource or drawable
//                .showImageOnFail(R.drawable.ic_error) // resource or drawable
                .resetViewBeforeLoading(true)  // default
//                .delayBeforeLoading(1000)
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(options).memoryCacheSizePercentage(30).build();
        ImageLoader.getInstance().init(config);
    }

    public static Configuration getTwitterAccessToken() {
        if(twitterAccessToken == null){
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("S3G06rqjHehNwvwor6qUQAlvh")
                    .setOAuthConsumerSecret("PUIrvsYvBxH8cweMlpIWlAY6tbilXkupg9NB97IzIa1383h0VA")
                    .setOAuthAccessToken("371248481-HPdCXFq9gLtxsQOxkEgiCtP05KKxxm2jBE3fiNkl")
                    .setOAuthAccessTokenSecret("HKs41JAVeLySxGSFFiBvN6tVkEDbL5xgPVz69abBi6jTT");
            twitterAccessToken = cb.build();
        }
        return twitterAccessToken;
    }
}