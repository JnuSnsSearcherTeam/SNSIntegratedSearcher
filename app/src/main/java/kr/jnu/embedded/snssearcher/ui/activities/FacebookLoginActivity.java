package kr.jnu.embedded.snssearcher.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.jnu.embedded.snssearcher.R;
import kr.jnu.embedded.snssearcher.base.App;
import kr.jnu.embedded.snssearcher.core.FacebookSearcherPresenter;
import kr.jnu.embedded.snssearcher.core.SNSSearcherContract;
import kr.jnu.embedded.snssearcher.data.FacebookPagePost;

public class FacebookLoginActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    LoginButton loginButton;
    ResultView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        resultView = new ResultView();
        Button button = findViewById(R.id.refreshButton);

        FacebookSearcherPresenter facebookSearcher = new FacebookSearcherPresenter();
        resultView.setPresenter(facebookSearcher);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email, user_likes, user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                ((App)getApplicationContext()).setFaceBookAccessToken(loginResult.getAccessToken());
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
        LoginManager.getInstance().logInWithReadPermissions(this,
                Arrays.asList("email, user_likes, user_friends"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultView.updateItem();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode, data);
    }

    public class ResultView implements SNSSearcherContract.View{
        SNSSearcherContract.Presenter presenter;
        TextView textView;

        public ResultView() {
            textView = (TextView) findViewById(R.id.resultView);
        }

        @Override
        public void setPresenter(SNSSearcherContract.Presenter presenter) {
            this.presenter = presenter;
        }

        @Override
        public void updateItem() {
            presenter.loadItem(new SNSSearcherContract.LoadCompleteListner() {
                @Override
                public void onComplete(ArrayList<FacebookPagePost> result) {
                    textView.setText(result.toString());
                }
            });
        }
    }
}
