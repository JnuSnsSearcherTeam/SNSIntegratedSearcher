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

    public FacebookPagePost(FacebookPage page, JSONObject jsonObject) {
        this.page = page;
        try {
            this.message = jsonObject.get("message").toString();
            this.createdTime = jsonObject.get("created_time").toString();
        } catch (Exception e ) {

        }
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "FacebookPagePost{" +
                "page=" + page +
                ", message='" + message + '\'' +
                '}';
    }
    public Item toFacebookItem(){
        return new Item(this.page.getName()
        ,this.page.getIconUrl()
        ,createdTime
        ,message
        ,"텍스트이미지");
    }
}
