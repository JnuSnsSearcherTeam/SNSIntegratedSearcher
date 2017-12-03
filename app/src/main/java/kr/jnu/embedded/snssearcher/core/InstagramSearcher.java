package kr.jnu.embedded.snssearcher.core;


import android.app.AlertDialog;
import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.facebook.AccessToken;

import java.util.Arrays;

/**
 * Created by KANG on 2017-12-03.
 */

public class InstagramSearcher {
    private static final String TAG = "InstagramSearcher";
    private static final String ClientId = "OTc3ODUwYzg0YWNkNDc1MDlhOTI1MjY2YzBkMzhiMTk="; // 나중에 지울 것.
    String AccessTokenUri;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    String accessToken;
    Context context;

    private static String getClientId(){
        return Arrays.toString(Base64.decode(ClientId, Base64.DEFAULT));
    }

    public InstagramSearcher(Context context) {
        AccessTokenUri = "https://api.instagram.com/oauth/authorize/?client_id="
                + InstagramSearcher.getClientId()
                + "&redirect_uri=https://github.com/HardPlant&response_type=token";

        this.context = context;
        get_access_token();
    }

    private void get_access_token(){
        if(accessToken != null) return;

        WebView webView = new WebView(context);

        webView.loadUrl(AccessTokenUri);
        dialogBuilder = new AlertDialog.Builder(context);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.contains("#access_token=")){
                    accessToken = url.substring(url.indexOf("#access_token"),url.length());
                    dialog.cancel();
                    return true;
                }
                return false;
            }
        });

        dialogBuilder.setView(webView);
        dialogBuilder.setPositiveButton("Okay", null);
        dialog = dialogBuilder.create();
        dialog.show();

        if(accessToken != null){
            Log.d(TAG, accessToken);
            return;
        }
        return;
    }
}
