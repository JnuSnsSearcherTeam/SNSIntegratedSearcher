package kr.jnu.embedded.snssearcher.core;

/**
 * Created by KANG on 2017-12-03.
 */

public class InstagramSearcherPresenter implements SNSSearcherContract.Presenter {
    SNSSearcherContract.View view;

    @Override
    public void loadItem(SNSSearcherContract.LoadCompleteListner listener) {

    }

    @Override
    public void setView(SNSSearcherContract.View view) {
        this.view = view;
    }
}
