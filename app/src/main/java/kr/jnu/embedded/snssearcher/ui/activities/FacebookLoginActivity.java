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
import kr.jnu.embedded.snssearcher.core.InstagramLoginManager;
import kr.jnu.embedded.snssearcher.core.InstagramSearcherPresenter;
import kr.jnu.embedded.snssearcher.core.SNSSearcherContract;
import kr.jnu.embedded.snssearcher.data.FacebookPagePost;

public class FacebookLoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        Button facebookButton = findViewById(R.id.facebookButton);
        Button instagramButton = findViewById(R.id.instagramButton);

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebookLogin();
            }
        });
        instagramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instaLogin();
            }
        });

    }
    void facebookLogin(){
        LoginManager.getInstance().logInWithReadPermissions(this,
                Arrays.asList("email, user_likes, user_friends"));
    }
    void instaLogin(){
        InstagramLoginManager loginManager = InstagramLoginManager.getInstance(this);
        loginManager.showLoginDialog();
    }

    @Override
    /*페이스북 관련 액티비티에 필요*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
