package kr.jnu.embedded.snssearcher.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.media.Image;

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
import java.util.concurrent.Callable;

import kr.jnu.embedded.snssearcher.data.FacebookPage;
import kr.jnu.embedded.snssearcher.data.FacebookPagePost;

/**
 * Created by KANG on 2017-11-22.
 */

public class FacebookSearcherPresenter implements SNSSearcherContract.Presenter {
    public static final String TAG = "FacebookSearcher";

    private AccessToken accessToken;

    private SNSSearcherContract.View view;

    @Override
    public void setView(SNSSearcherContract.View view) {
        this.view = view;
    }

    @Override
    public void loadItem(final SNSSearcherContract.LoadCompleteListner listener) {
        final ArrayList<FacebookPagePost> result = new ArrayList<>();

        FacebookPagePostFetcher facebookPagePostFetcher
                = new FacebookPagePostFetcher(accessToken, result);

        facebookPagePostFetcher.onResult(new Callable() {
            @Override
            public Object call() throws Exception {
                listener.onComplete(result);
                return true;
            }
        });

        facebookPagePostFetcher.start();
    }

    public void parsePages(JSONObject pageInfo, JSONObject fetchedPageResult){
        try {
            ArrayList<FacebookPage> pages;
            ArrayList<FacebookPagePost> posts;

            String PageID;
            JSONArray pageInfoData = pageInfo.getJSONArray("data");
            for(int i=0; i<pageInfoData.length();i++){
                JSONObject info = pageInfoData.getJSONObject(i);
                String name = info.getString("name");
                String picture = info.getJSONObject("pricture").getJSONObject("data").getString("url");

                FacebookPage page = new FacebookPage(picture, name);
            }

            Image icon;
            String name;
            String message;

        } catch(JSONException je){
            je.printStackTrace();
        }

    }


    public FacebookSearcherPresenter() {
        CallbackManager mCallbackManager;
        AccessTokenTracker accessTokenTracker;

        mCallbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken = currentAccessToken;
            }
        };
        accessTokenTracker.startTracking();
        accessToken = AccessToken.getCurrentAccessToken();
    }


}
