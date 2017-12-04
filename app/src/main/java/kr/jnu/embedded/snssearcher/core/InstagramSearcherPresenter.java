package kr.jnu.embedded.snssearcher.core;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KANG on 2017-12-03.
 */

public class InstagramSearcherPresenter implements SNSSearcherContract.Presenter {
    private static final String TAG = "InstaSearcherPresenter";
    SNSSearcherContract.View view;
    String tag;
    InstagramSearcher instagramSearcher;
    String loginResponse;

    public InstagramSearcherPresenter() {
        instagramSearcher = new InstagramSearcher();
    }
    public String getTokenUrl(){
        return instagramSearcher.getAccessTokenUri();
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setAccessTokeFromLoginResponse(String response){
        loginResponse = response;
        if(loginResponse != null)
            instagramSearcher.setAccessTokenFromLoginResponse(loginResponse);
    }
    @Override
    public void loadItem(SNSSearcherContract.LoadCompleteListner listener) {
        ArrayList<Object> result = new ArrayList<>();
        Log.d(TAG, "loaditem call");

        if(instagramSearcher.getAccessToken() == null){
            Log.d(TAG, "AccessToken is null");
            return;
        }
        Log.d(TAG,instagramSearcher.getMyRecentMedia().toString());
        if(instagramSearcher.getMyRecentMedia() == null){
            Log.d(TAG, "getMyRecentMedia is null.");
            return;
        }

        result.addAll(instagramSearcher.getMyRecentMedia());

        listener.onComplete(result);
    }

    @Override
    public void setView(SNSSearcherContract.View view) {
        this.view = view;
    }
}
