package kr.jnu.embedded.snssearcher.ui.activities;

import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import kr.jnu.embedded.snssearcher.R;
import kr.jnu.embedded.snssearcher.core.InstagramSearcherPresenter;
import kr.jnu.embedded.snssearcher.core.SNSSearcherContract;

public class InstagramActivity extends AppCompatActivity {
    InstagramSearcherPresenter presenter;
    Button button;
    AlertDialog InstagramLoginDialog;
    private static final String TAG = "InstagramActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);
        button = findViewById(R.id.instaButton);

        presenter = new InstagramSearcherPresenter();
        final ResultView resultView = new ResultView();
        resultView.setPresenter(presenter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginDialog();
                Log.d(TAG,"다이얼로그 종료");
                resultView.updateItem();
            }
        });

    }
    private void testActivity(){
        presenter.setTag("행인두부");

    }

    private void showLoginDialog(){
        Uri uri = Uri.parse(presenter.getTokenUrl());
        WebView webView = new WebView(this);
        webView.loadUrl(uri.toString());
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, url);
                if(url.contains("access_token=")) {
                    presenter.setAccessTokeFromLoginResponse(url);
                    InstagramLoginDialog.dismiss();
                }
                return false;
            }
        });
        dialogBuilder.setView(webView);
        InstagramLoginDialog = dialogBuilder.create();
        InstagramLoginDialog.show();
    }

    public class ResultView implements SNSSearcherContract.View{
        SNSSearcherContract.Presenter presenter;
        TextView textView;

        public ResultView() {
            textView = findViewById(R.id.instaResultView);

        }

        @Override
        public void setPresenter(SNSSearcherContract.Presenter presenter) {
            this.presenter = presenter;
        }

        @Override
        public void updateItem() {
            Log.d (TAG,"updTeItem called.");
                presenter.loadItem(new SNSSearcherContract.LoadCompleteListner() {
                    @Override
                    public void onComplete(ArrayList<Object> result) {
                        Log.d (TAG,"Result: ");
                        textView.setText(result.toString());
                    }
                });
        }
    }
}
