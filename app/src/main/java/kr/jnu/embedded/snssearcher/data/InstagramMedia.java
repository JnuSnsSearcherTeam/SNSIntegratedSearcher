package kr.jnu.embedded.snssearcher.data;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONObject;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import kr.jnu.embedded.snssearcher.base.Item;

/**
 * Created by KANG on 2017-12-04.
 */

public class InstagramMedia implements Comparable<InstagramMedia>{
    String userName;
    String imageUri;
    String userProfile;
    Bitmap image;
    String createdTime;
    String link;
    String message;
    Date date;

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
            this.imageUri = object.getJSONObject("images").getJSONObject("low_resolution").getString("url");
            String createdTime = object.getString("created_time");
            this.createdTime = toDateFormat(createdTime);
            this.link = object.getString("link");
            this.message = object.getJSONObject("caption").getString("text");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public String toDateFormat(String time){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss");

        date = new Date(Long.parseLong(time)*1000);
        return sf.format(date);
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

    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(@NonNull InstagramMedia instagramMedia) {
        return this.getDate().compareTo(instagramMedia.getDate());
    }
}
