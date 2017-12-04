package kr.jnu.embedded.snssearcher.data;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.sql.Date;
import java.sql.Timestamp;

import kr.jnu.embedded.snssearcher.base.Item;

/**
 * Created by KANG on 2017-12-04.
 */

public class InstagramMedia {
    String userName;
    String imageUri;
    String userProfile;
    Bitmap image;
    String createdTime;
    String link;
    String message;

    public InstagramMedia(String userName, String imageUri, String createdTime, String link) {
        this.userName = userName;
        this.userProfile = userProfile;
        this.imageUri = imageUri;
        this.createdTime = createdTime;
        this.link = link;
        this.message = message;
    }

    public InstagramMedia(JSONObject object) {
        try {
            this.userName = object.getJSONObject("user").getString("full_name");
            this.userProfile = object.getJSONObject("user").getString("profile_picture");
            this.imageUri = object.getJSONObject("images").getJSONObject("thumbnail").getString("url");
            String time = object.getString("created_time");
            Timestamp timestamp = new Timestamp(Integer.parseInt(createdTime));
            Date date = new Date(timestamp.getTime());
            this.createdTime = date.toString();
            this.link = object.getString("link");
            this.message = object.getJSONObject("caption").getString("text");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Item toInstagramItem(){
        return new Item(userName,userProfile,createdTime, message,imageUri);
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
