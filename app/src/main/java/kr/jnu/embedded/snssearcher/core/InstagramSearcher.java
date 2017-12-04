package kr.jnu.embedded.snssearcher.core;


import android.app.AlertDialog;
import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import kr.jnu.embedded.snssearcher.data.InstagramMedia;

/**
 * Created by KANG on 2017-12-03.
 */

public class InstagramSearcher {
    private static final String TAG = "InstagramSearcher";
    String accessToken;

    public static String getAccessTokenUri() {
        return "https://api.instagram.com/oauth/authorize/?client_id="
                + "977850c84acd47509a925266c0d38b19"
                + "&redirect_uri=https://github.com/HardPlant&response_type=token"
                + "&scope=public_content";
    }

    private static String readUrl(String urlString) throws Exception {
        URLLoadTask urlLoadTask = new URLLoadTask();
        urlLoadTask.execute(urlString);
        return urlLoadTask.get();
    }

    public ArrayList<InstagramMedia> getMyRecentMedia(){
        return getInstagramData("https://api.instagram.com/v1/users/self/media/recent/");
    }

    public ArrayList<InstagramMedia> getHashTagMedia(String tagName){
        String url = "https://api.instagram.com/v1/tags/{tag-name}/media/recent"
                .replace("{tag-name}",tagName);
        return getInstagramData(url);
    }

    public ArrayList<InstagramMedia> getInstagramData(String Url){
        if(accessToken == null) return null;
        String dest = Url;
        try {

            String resp = readUrl(dest+"?access_token="+accessToken);

            JSONObject result = new JSONObject(resp);
            JSONArray data = result.getJSONArray("data");
            ArrayList<InstagramMedia> medias = new ArrayList<>();

            for(int i=0; i<data.length();i++){
                medias.add(new InstagramMedia(data.getJSONObject(i)));
            }
            return medias;

        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getAccessTokenFromRedirectUrl(String redirectedUrl){
        String accessToken = redirectedUrl.substring(redirectedUrl.lastIndexOf('#'));
        accessToken = accessToken.substring(accessToken.lastIndexOf('=')+1);
        return accessToken;
    }

    public boolean setAccessTokenFromLoginResponse(String accessTokenUrl){
        this.accessToken = getAccessTokenFromRedirectUrl(accessTokenUrl);
        return true;
    }

    public InstagramSearcher() {
        //setAccessTokenFromLoginResponse();
        //get_access_token();
    }

    public String getAccessToken() {
        return accessToken;
    }

}
