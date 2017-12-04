package kr.jnu.embedded.snssearcher.core;


import android.app.AlertDialog;
import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.facebook.AccessToken;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by KANG on 2017-12-03.
 */

public class InstagramSearcher {
    private static final String TAG = "InstagramSearcher";
    private static final String ClientId = "977850c84acd47509a925266c0d38b19"; // 나중에 지울 것.
    String AccessTokenUri;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    String accessToken;
    Context context;

    private static String getClientId(){
        return ClientId;
        //return Arrays.toString(Base64.decode(ClientId, Base64.DEFAULT));
    }
    public String getMyRecentMedia(){
        if(accessToken == null) return null;
        String dest = "https://api.instagram.com/v1/users/self/media/recent/?access_token=";
        try {
            URL url = new URL(dest + accessToken);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in =connection.getInputStream();
            in.read();
            String result = in.toString();
            return result;
        } catch(Exception e){

        }
        return null;
    }

    public String getAccessTokenFromRedirectUrl(String redirectedUrl){
        String accessToken = redirectedUrl.substring(redirectedUrl.lastIndexOf('#'));
        accessToken = accessToken.substring(accessToken.lastIndexOf('=')+1);
        return accessToken;
    }
    public boolean setAccessToken(String accessToken){
        this.accessToken = accessToken;
        return true;
    }

    public InstagramSearcher(Context context) {
        AccessTokenUri = "https://api.instagram.com/oauth/authorize/?client_id="
                + InstagramSearcher.getClientId()
                + "&redirect_uri=https://github.com/HardPlant&response_type=token"
                + "&scope=public_content";

        this.context = context;
        //setAccessToken();
        //get_access_token();
    }
    private boolean setAccessToken(){
        try {
            String redirectedUrl;
            URL accessTokenUrl = new URL(AccessTokenUri);
            HttpURLConnection connection = (HttpURLConnection) accessTokenUrl.openConnection();
            InputStream in = connection.getInputStream();
            redirectedUrl = connection.getURL().toString();
            accessToken = redirectedUrl.substring(redirectedUrl.lastIndexOf('#'));
            in.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
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

    public String getAccessToken() {
        return accessToken;
    }
}
