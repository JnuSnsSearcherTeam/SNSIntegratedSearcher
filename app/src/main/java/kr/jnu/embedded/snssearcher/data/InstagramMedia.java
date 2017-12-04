package kr.jnu.embedded.snssearcher.data;

import android.graphics.Bitmap;

import org.json.JSONObject;

/**
 * Created by KANG on 2017-12-04.
 */

public class InstagramMedia {
    String userName;
    String imageUri;
    Bitmap image;
    String createdTime;
    String link;

    public InstagramMedia(String userName, String imageUri, String createdTime, String link) {
        this.userName = userName;
        this.imageUri = imageUri;
        this.createdTime = createdTime;
        this.link = link;
    }

    public InstagramMedia(JSONObject object) {
        try {
            this.userName = object.getJSONObject("user").getString("username");
            this.imageUri = object.getJSONObject("images").getJSONObject("thumbnail").getString("url");
            this.createdTime = object.getString("created_time");
            this.link = object.getString("link");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "InstagramMedia{" +
                "userName='" + userName + '\'' +
                ", imageUri='" + imageUri + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
