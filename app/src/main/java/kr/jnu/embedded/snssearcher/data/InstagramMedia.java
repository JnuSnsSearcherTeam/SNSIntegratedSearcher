package kr.jnu.embedded.snssearcher.data;

import android.graphics.Bitmap;

import org.json.JSONObject;

import kr.jnu.embedded.snssearcher.base.Item;

/**
 * Created by KANG on 2017-12-04.
 */

public class InstagramMedia {
    String userName;
    String imageUri;
    Bitmap image;
    String createdTime;
    String link;
    String message;

    public InstagramMedia(String userName, String imageUri, String createdTime, String link) {
        this.userName = userName;
        this.imageUri = imageUri;
        this.createdTime = createdTime;
        this.link = link;
        this.message = message;
    }

    public InstagramMedia(JSONObject object) {
        try {
            this.userName = object.getJSONObject("user").getString("username");
            this.imageUri = object.getJSONObject("images").getJSONObject("thumbnail").getString("url");
            this.createdTime = object.getString("created_time");
            this.link = object.getString("link");
            this.message = object.getJSONObject("caption").getString("text");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Item toInstagramItem(){
        return new Item(userName,"", createdTime, message,imageUri);
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
