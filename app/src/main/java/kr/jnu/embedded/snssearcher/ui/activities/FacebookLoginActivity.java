package kr.jnu.embedded.snssearcher.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.facebook.login.LoginManager;
import java.util.Arrays;

import kr.jnu.embedded.snssearcher.R;
import kr.jnu.embedded.snssearcher.core.InstagramLoginManager;

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
