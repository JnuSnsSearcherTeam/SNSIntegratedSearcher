package kr.jnu.embedded.snssearcher.core;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
            listener.onComplete(null);
            return;
        }

        result.addAll(instagramSearcher.getHashTagMedia(tag));

        listener.onComplete(result);
    }

    @Override
    public void setView(SNSSearcherContract.View view) {
        this.view = view;
    }

    public boolean isAccessTokenSet(){
        if(instagramSearcher.getAccessToken() == null) return false;

        return true;
    }
}
