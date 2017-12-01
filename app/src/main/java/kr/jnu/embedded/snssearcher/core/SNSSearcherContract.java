package kr.jnu.embedded.snssearcher.core;

import org.json.JSONObject;

import java.util.ArrayList;

import kr.jnu.embedded.snssearcher.data.FacebookPagePost;

/**
 * Created by KANG on 2017-11-22.
 */

public interface SNSSearcherContract {
    interface View{
        void setPresenter(SNSSearcherContract.Presenter presenter);
        void updateItem();
    }

    interface Presenter{
        void loadItem(LoadCompleteListner listener);

        void setView(View view);
    }

    public interface LoadCompleteListner{
        void onComplete(ArrayList<FacebookPagePost> result);
    }
}
