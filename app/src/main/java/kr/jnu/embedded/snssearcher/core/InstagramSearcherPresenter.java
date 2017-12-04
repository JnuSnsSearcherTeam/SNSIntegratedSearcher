package kr.jnu.embedded.snssearcher.core;

import android.content.Context;

/**
 * Created by KANG on 2017-12-03.
 */

public class InstagramSearcherPresenter implements SNSSearcherContract.Presenter {
    SNSSearcherContract.View view;
    String tag;
    InstagramSearcher instagramSearcher;

    public InstagramSearcherPresenter(Context context) {
        instagramSearcher = new InstagramSearcher(context);
    }

    public void setTag(String tag) {
        this.tag = tag;
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
