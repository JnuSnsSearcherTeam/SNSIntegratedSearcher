package kr.jnu.embedded.snssearcher.data;

import android.media.Image;

import org.json.JSONException;
import org.json.JSONObject;

import kr.jnu.embedded.snssearcher.base.App;
import kr.jnu.embedded.snssearcher.base.Item;

/**
 * Created by KANG on 2017-12-01.
 */

public class FacebookPagePost {
    FacebookPage page;
    String message;
    String createdTime;

    public FacebookPagePost(FacebookPage page, String message, String createdTime) {
        this.page = page;
        this.message = message;
        this.createdTime = createdTime;
    }

    public FacebookPagePost(FacebookPage page, JSONObject object){
        try {
            this.page = page;
            this.createdTime = object.getString("created_time");
        } catch(JSONException je){
            je.printStackTrace();
        }
        try {
            this.message = object.getString("message");
        }catch(JSONException je){

        }
    }

    public String getMessage() {
        if(message != null)
            return message;
        return "";
    }

    @Override
    public String toString() {
        return "FacebookPagePost{" +
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
