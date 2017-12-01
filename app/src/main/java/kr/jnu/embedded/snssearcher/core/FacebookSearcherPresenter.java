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
import java.util.Iterator;
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

    ArrayList<FacebookPage> pages = new ArrayList<>();
    ArrayList<FacebookPagePost> posts = new ArrayList<>();

    @Override
    public void setView(SNSSearcherContract.View view) {
        this.view = view;
    }

    @Override
    public void loadItem(final SNSSearcherContract.LoadCompleteListner listener) {
        final ArrayList<FacebookPagePost> result = new ArrayList<>();

        FacebookPagePostFetcher facebookPagePostFetcher = new FacebookPagePostFetcher(accessToken, new FacebookPagePostFetcher.OnCompleteListener() {
            @Override
            public void onComplete(JSONObject pages, ArrayList<JSONObject> postArray) {
                parsePages(pages, postArray);
                listener.onComplete(posts);
            }
        });

        facebookPagePostFetcher.start();
    }

    public void parsePages(JSONObject pageInfo, ArrayList<JSONObject> fetchedPageResult){
        try {
            String PageID;
            JSONArray pageInfoData = pageInfo.getJSONArray("data");
            for(int i=0; i<pageInfoData.length();i++){
                JSONObject info = pageInfoData.getJSONObject(i);
                String name = info.getString("name");
                String id= info.getString("id");
                String picture = info.getJSONObject("pricture").getJSONObject("data").getString("url");
                pages.add(new FacebookPage(id, picture, name));
            }

            for(JSONObject object : fetchedPageResult) {
                for (Iterator<String> itr = object.keys(); !itr.hasNext(); ){
                    String key = (String)itr.next();
                    JSONObject item = (JSONObject) object.get(key);
                    JSONArray data = item.getJSONArray("data");
                    FacebookPage facebookPage = findPagebyId(key, pages);

                    for(int i=0; i<data.length(); i++){
                        String message = data.getString(i);
                        posts.add(new FacebookPagePost(facebookPage, message));
                    }
                }
            }
        } catch(JSONException je){
            je.printStackTrace();
        }

    }
    private FacebookPage findPagebyId(String key, ArrayList<FacebookPage> pages){
        for(FacebookPage page : pages){
            if(page.getID().equals(key)) return page;
        }
        return null;
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
