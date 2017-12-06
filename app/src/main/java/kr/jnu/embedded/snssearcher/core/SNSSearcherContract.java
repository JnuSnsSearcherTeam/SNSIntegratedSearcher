package kr.jnu.embedded.snssearcher.core;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by KANG on 2017-11-22.
 */

public interface SNSSearcherContract {
    interface View{
        void setPresenter(SNSSearcherContract.Presenter presenter);
        void updateItem();
    }

    interface Presenter{
        void loadItem();

        void setView(View view);
    }

}
