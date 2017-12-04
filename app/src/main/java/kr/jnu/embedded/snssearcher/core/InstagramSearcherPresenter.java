package kr.jnu.embedded.snssearcher.core;

import android.content.Context;

/**
 * Created by KANG on 2017-12-03.
 */

public class InstagramSearcherPresenter implements SNSSearcherContract.Presenter {
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
        listener.onComplete(null);
        instagramSearcher.getHashTagMedia(tag);
    }

    @Override
    public void setView(SNSSearcherContract.View view) {
        this.view = view;
    }
}
