package kr.jnu.embedded.snssearcher.core;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
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
                ArrayList<Object> result = new ArrayList<>();
                Log.d(TAG, "loadItem Called");

                result.addAll(twitterSearcher.getTwitterSearch());

                for(Object item : result){
                    App.twitterItem.add(((TwitterItem)item).toTwitterItem());
                }
                Log.d(TAG, "Twitter item loaded");
                handler.sendMessage(new Message());

            }
        }).start();
    }
    @Override
    public void setView(SNSSearcherContract.View view) {
        this.view = view;
    }
}
