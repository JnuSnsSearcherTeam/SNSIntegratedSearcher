package kr.jnu.embedded.snssearcher.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.media.Image;
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
import java.util.Iterator;
import java.util.concurrent.Callable;

import kr.jnu.embedded.snssearcher.data.FacebookPage;
import kr.jnu.embedded.snssearcher.data.FacebookPagePost;

/**
 * Created by KANG on 2017-11-22.
 */

public class FacebookSearcherPresenter implements SNSSearcherContract.Presenter {
    public static final String TAG = "FacebookSearcher";
    FacebookPostSearcher facebookPostSearcher;
    SNSSearcherContract.LoadCompleteListner listener;
    ArrayList<FacebookPagePost> resultPost;

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

    @Override
    public void setView(SNSSearcherContract.View view) {
        this.view = view;
    }

    @Override
    public void loadItem(final SNSSearcherContract.LoadCompleteListner listener) {
        this.listener = listener;
        fetchProcess();
    }
    public void fetchProcess(){

        FacebookPagePostFetcher facebookPagePostFetcher = new FacebookPagePostFetcher(accessToken
                , new FacebookPagePostFetcher.OnCompleteListener() {
            @Override
            public void onComplete(ArrayList<JSONObject> pages, ArrayList<JSONObject> postArray) {
                facebookPostSearcher = new FacebookPostSearcher("안녕하세요.");
                facebookPostSearcher.setParameters(pages, postArray);
                Log.d(TAG, facebookPostSearcher.getPages().toString());
                Log.d(TAG, facebookPostSearcher.getPosts().toString());
                listener.onComplete(facebookPostSearcher.getPosts());
            }
        });

        facebookPagePostFetcher.start();
        Log.d(TAG, "Page Post Fetch started.");
    }
}
