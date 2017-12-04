package kr.jnu.embedded.snssearcher.data;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by KANG on 2017-12-01.
 */

public class FacebookPage {
    String IconUrl;
    String Name;
    String ID;
    Bitmap Icon;

    public FacebookPage(String id, String iconUrl, String name) {
        ID = id;
        IconUrl = iconUrl;
        Name = name;
    }
    public FacebookPage(JSONObject object){
        try {
            ID = object.getString("id");
            Name = object.getString("name");
            IconUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
        } catch (JSONException je){
            je.printStackTrace();
        }
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        if(Name == null) return "";
        return Name;
    }

    public String getIconUrl() {
        return IconUrl;
    }

    public String getIconUrl() { return IconUrl;}

    @Override
    public String toString() {
        return "FacebookPage{" +
                "Name='" + Name + '\'' +
                ", ID='" + ID + '\'' +
                '}';
    }
}
