package kr.jnu.embedded.snssearcher.core;


import android.app.AlertDialog;
import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by KANG on 2017-12-03.
 */

public class InstagramSearcher {
    private static final String ClientId = "OTc3ODUwYzg0YWNkNDc1MDlhOTI1MjY2YzBkMzhiMTk="; // 나중에 지울 것.
    final String AccessTokenUri= "https://api.instagram.com/oauth/authorize/?client_id=977850c84acd47509a925266c0d38b19&redirect_uri=https://github.com/HardPlant&response_type=token";
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    String accessToken;

    private static String getClientId(){
        return Base64.decode(ClientId, Base64.DEFAULT).toString();
    }
    private void get_access_token(Context context){
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
            Log.d(accessToken);
            return;
        }
        return;
    }
}
