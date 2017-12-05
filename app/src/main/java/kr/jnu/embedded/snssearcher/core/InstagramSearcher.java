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

import kr.jnu.embedded.snssearcher.base.App;
import kr.jnu.embedded.snssearcher.data.InstagramMedia;

/**
 * Created by KANG on 2017-12-03.
 */

public class InstagramSearcher {
    private static final String TAG = "InstagramSearcher";

    private static String readUrl(String urlString) throws Exception {
        URLLoadTask urlLoadTask = new URLLoadTask();
        urlLoadTask.execute(urlString);
        return urlLoadTask.get();
    }

    public InstagramSearcher() {
    }

    public ArrayList<InstagramMedia> getMyRecentMedia(){
        return getInstagramData("https://api.instagram.com/v1/users/self/media/recent/");
    }

    public ArrayList<InstagramMedia> getHashTagMedia(String tagName){
        Log.d(TAG, "getHashTagMedia called");
        String url = "https://api.instagram.com/v1/tags/{tag-name}/media/recent"
                .replace("{tag-name}",tagName);
        return getInstagramData(url);
    }

    public ArrayList<InstagramMedia> getInstagramData(String Url){
        Log.d(TAG, "getInstagramData called");
        if(App.instagramAccessToken == null){
            Log.d(TAG, "AccessToken is null called");
            return null;
        }
        String dest = Url;
        try {

            String resp = readUrl(dest+"?access_token="+App.instagramAccessToken);

            JSONObject result = new JSONObject(resp);
            Log.d(TAG, "result: " + result);

            JSONArray data = result.getJSONArray("data");
            ArrayList<InstagramMedia> medias = new ArrayList<>();

            for(int i=0; i<data.length();i++){
                medias.add(new InstagramMedia(data.getJSONObject(i)));
            }
            Log.d(TAG, "medias: " + medias);
            return medias;

        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
