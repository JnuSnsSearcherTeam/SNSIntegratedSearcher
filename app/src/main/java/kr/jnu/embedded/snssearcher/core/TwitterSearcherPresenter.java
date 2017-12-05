package kr.jnu.embedded.snssearcher.core;

import android.util.Log;

import java.util.ArrayList;

import kr.jnu.embedded.snssearcher.base.App;
import kr.jnu.embedded.snssearcher.data.TwitterItem;

/**
 * Created by KANG on 2017-12-05.
 */

public class TwitterSearcherPresenter implements SNSSearcherContract.Presenter {
    private static final String TAG = "TwitterSearcherP";
    private SNSSearcherContract.View view;
    private TwitterSearcher twitterSearcher;

    public TwitterSearcherPresenter() {
        twitterSearcher = new TwitterSearcher();
    }

    public void setKeyword(String keyword){
        twitterSearcher.setKeyword(keyword);
    }

    @Override
    public void loadItem() {
        ArrayList<Object> result = new ArrayList<>();
        Log.d(TAG, "loadItem Called");
        App.twitterItem.clear();

        result.addAll(twitterSearcher.getTwitterSearch());

        for(Object item : result){
            App.twitterItem.add(((TwitterItem)item).toTwitterItem());
        }

        view.updateItem();
    }

    @Override
    public void setView(SNSSearcherContract.View view) {
        this.view = view;
    }
}
