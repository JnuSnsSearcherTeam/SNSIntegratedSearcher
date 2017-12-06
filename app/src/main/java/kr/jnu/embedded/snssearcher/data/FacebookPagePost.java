package kr.jnu.embedded.snssearcher.data;

import android.media.Image;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kr.jnu.embedded.snssearcher.base.App;
import kr.jnu.embedded.snssearcher.base.Item;

/**
 * Created by KANG on 2017-12-01.
 */

public class FacebookPagePost implements Comparable<FacebookPagePost>{
    FacebookPage page;
    String createdTime;
    String message;
    Date createdDate;

    public FacebookPagePost(FacebookPage page, JSONObject jsonObject) {
        this.page = page;
        try {
            this.message = jsonObject.get("message").toString();
            String createdTime = jsonObject.get("created_time").toString();
            this.createdTime = toDateFormat(createdTime);
            if(this.getCreatedDate() == null) Log.d("FBMSG", "NULL");
            else Log.d("FBMSG", this.getCreatedDate().toString());
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        if(message == null) return "";
        return message;
    }
    private String toDateFormat(String time){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+SSSS", Locale.KOREA);
        try {
            this.createdDate = sf.parse(time);
        } catch(Exception e){
            e.printStackTrace();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss", Locale.KOREA);
        return format.format(this.createdDate);
    }

    @Override
    public String toString() {
        return "FacebookPagePost{" +
                "page=" + page +
                ", message='" + message + '\'' +
                '}';
    }
    public Item toFacebookItem(){
        if(page == null) return null;
        return new Item(this.page.getName()
        ,this.page.getIconUrl()
        ,createdTime
        ,message
        ,"텍스트이미지");
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public int compareTo(@NonNull FacebookPagePost facebookPagePost) {
        return this.getCreatedDate().compareTo(facebookPagePost.getCreatedDate());
    }
}
