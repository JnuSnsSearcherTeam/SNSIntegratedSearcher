package kr.jnu.embedded.snssearcher.core;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import kr.jnu.embedded.snssearcher.base.App;

/**
 * Created by KANG on 2017-12-04.
 */

public class InstagramLoginManager {
        private static final String TAG = "LoginManager";
        private static InstagramLoginManager instance;
        private Context context;
        private AlertDialog InstagramLoginDialog;

        private InstagramLoginManager(Context context) {
            this.context = context;
        }
        public static InstagramLoginManager getInstance(Context context){
            if(instance == null) instance = new InstagramLoginManager(context);
            return instance;
        }

        public void showLoginDialog(){
            Uri uri = Uri.parse(InstagramSearcher.getAccessTokenUri());
            WebView webView = new WebView(context);
            webView.loadUrl(uri.toString());
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.d(TAG, url);
                    if(url.contains("access_token=")) {
                        String accessToken = InstagramSearcher.getAccessTokenFromRedirectUrl(url);
                        ((App)context.getApplicationContext()).setInstagramAccessToken(accessToken);

                        InstagramLoginDialog.dismiss();
                    }
                    return false;
                }
            });
            dialogBuilder.setView(webView);
            InstagramLoginDialog = dialogBuilder.create();
            InstagramLoginDialog.show();
        }

}
