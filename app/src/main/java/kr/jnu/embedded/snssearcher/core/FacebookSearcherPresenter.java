package kr.jnu.embedded.snssearcher.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.Callable;

import kr.jnu.embedded.snssearcher.base.App;
import kr.jnu.embedded.snssearcher.base.Item;
import kr.jnu.embedded.snssearcher.data.FacebookPage;
import kr.jnu.embedded.snssearcher.data.FacebookPagePost;

/**
 * Created by KANG on 2017-11-22.
 */

public class FacebookSearcherPresenter implements SNSSearcherContract.Presenter {
    public static final String TAG = "FacebookSearcher";
    FacebookPostSearcher facebookPostSearcher;
    ArrayList<Object> resultPost;
    String keyword;

    private AccessToken accessToken;

    private SNSSearcherContract.View view;

    public FacebookSearcherPresenter() {
        AccessTokenTracker accessTokenTracker;

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken = currentAccessToken;
            }
        };
        accessTokenTracker.startTracking();
        accessToken = AccessToken.getCurrentAccessToken();
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void setView(SNSSearcherContract.View view) {
        this.view = view;
    }

    @Override
    public void loadItem() {
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                view.updateItem();
                return true;
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                fetchProcess(handler);
            }
        }).start();
    }

    public void fetchProcess(final Handler handler){

        final FacebookPagePostFetcher facebookPagePostFetcher = new FacebookPagePostFetcher(accessToken
                , new FacebookPagePostFetcher.OnCompleteListener() {
            @Override
            public void onComplete(ArrayList<FacebookPage> pages, ArrayList<FacebookPagePost> postArray) {
                resultPost = new ArrayList<Object>();
                facebookPostSearcher = new FacebookPostSearcher(keyword);
                facebookPostSearcher.setParameters(pages, postArray);

                //Log.d(TAG, facebookPostSearcher.getPages().toString());
                //Log.d(TAG, facebookPostSearcher.getPosts().toString());
                ArrayList<FacebookPagePost> list = facebookPostSearcher.getPosts();

                if(list == null) return;

                Collections.sort(list, Collections.<FacebookPagePost>reverseOrder());
                resultPost.addAll(list);

                for(Object item : resultPost){
                //    Log.d(TAG, "Item Added: " + item.toString());
                    Item toAdd = ((FacebookPagePost)item).toFacebookItem();
                    if(toAdd != null) App.facebookItem.add(toAdd);
                }

                handler.sendMessage(new Message());
            }
        });

        facebookPagePostFetcher.start();
        Log.d(TAG, "Page Post Fetch started.");
    }
}
