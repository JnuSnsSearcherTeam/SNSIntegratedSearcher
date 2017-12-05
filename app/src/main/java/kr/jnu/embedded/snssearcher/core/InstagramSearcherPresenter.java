package kr.jnu.embedded.snssearcher.core;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import kr.jnu.embedded.snssearcher.base.App;
import kr.jnu.embedded.snssearcher.data.InstagramMedia;

/**
 * Created by KANG on 2017-12-03.
 */

public class InstagramSearcherPresenter implements SNSSearcherContract.Presenter {
    private static final String TAG = "InstaSearcherPresenter";
    private SNSSearcherContract.View view;
    private String tag;
    private InstagramSearcher instagramSearcher;

    public InstagramSearcherPresenter() {
        instagramSearcher = new InstagramSearcher();
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public void loadItem() {
        ArrayList<Object> result = new ArrayList<>();
        Log.d(TAG, "loaditem call");

        if(!isAccessTokenSet()){
            Log.d(TAG, "AccessToken is null");
            return;
        }

        ArrayList<InstagramMedia> list = instagramSearcher.getHashTagMedia(tag);
        if(list == null){
            Log.d(TAG, "hashtag list null");
            return;
        }
        Collections.sort(list);
        result.addAll(list);

        for(Object item : result){
            App.instagramItem.add(((InstagramMedia)item).toInstagramItem());
        }

        view.updateItem();
    }

    @Override
    public void setView(SNSSearcherContract.View view) {
        this.view = view;
    }

    public boolean isAccessTokenSet(){
        if(App.instagramAccessToken == null) return false;

        return true;
    }
}
