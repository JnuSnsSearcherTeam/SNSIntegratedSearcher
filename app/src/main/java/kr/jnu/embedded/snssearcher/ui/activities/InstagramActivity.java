package kr.jnu.embedded.snssearcher.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kr.jnu.embedded.snssearcher.R;
import kr.jnu.embedded.snssearcher.core.InstagramSearcher;
import kr.jnu.embedded.snssearcher.core.InstagramSearcherPresenter;
import kr.jnu.embedded.snssearcher.core.SNSSearcherContract;

public class InstagramActivity extends AppCompatActivity {
    InstagramSearcherPresenter presenter;
    InstagramSearcher searcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);
        //searcher = new InstagramSearcher(getApplicationContext());
    }

    public class ResultView implements SNSSearcherContract.View{

        @Override
        public void setPresenter(SNSSearcherContract.Presenter presenter) {

        }

        @Override
        public void updateItem() {

        }
    }
}
