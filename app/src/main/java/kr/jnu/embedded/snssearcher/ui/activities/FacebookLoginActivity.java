package kr.jnu.embedded.snssearcher.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import kr.jnu.embedded.snssearcher.R;
import kr.jnu.embedded.snssearcher.core.FacebookSearcherPresenter;
import kr.jnu.embedded.snssearcher.core.SNSSearcherContract;

public class FacebookLoginActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    LoginButton loginButton;
    ResultView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        resultView = (ResultView) findViewById(R.id.resultView);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                FacebookSearcherPresenter facebookSearcher = new FacebookSearcherPresenter();
                resultView.setPresenter(facebookSearcher);
                resultView.updateItem();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode, data);
    }

    public class ResultView extends AppCompatTextView implements SNSSearcherContract.View{
        SNSSearcherContract.Presenter presenter;

        public ResultView(Context context) {
            super(context);
        }

        @Override
        public void setPresenter(SNSSearcherContract.Presenter presenter) {
            this.presenter = presenter;
        }

        @Override
        public void updateItem() {
            List<JSONObject> result = presenter.loadItem();
            this.setText(result.toString());
        }
    }
}
