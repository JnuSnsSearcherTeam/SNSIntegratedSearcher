package kr.jnu.embedded.snssearcher.data;

import android.media.Image;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import kr.jnu.embedded.snssearcher.base.App;
import kr.jnu.embedded.snssearcher.base.Item;

/**
 * Created by KANG on 2017-12-01.
 */

public class FacebookPagePost {
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
        } catch (Exception e ) {

        }
    }

    public String getMessage() {
        if(message == null) return "";
        return message;
    }
    private String toDateFormat(String time){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss+SSSS");
        try {
            this.createdDate = sf.parse(time);
        } catch(Exception e){
            e.printStackTrace();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd aa hh:mm:ss");
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
}
