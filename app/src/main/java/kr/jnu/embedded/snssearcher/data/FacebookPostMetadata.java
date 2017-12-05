package kr.jnu.embedded.snssearcher.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by KANG on 2017-12-04.
 */

public class FacebookPostMetadata {
    String FacebookPage;
    JSONArray data;
    JSONObject paging;
    JSONObject cursors;
    String next;

    public FacebookPostMetadata(JSONObject object, String pageId) {
        FacebookPage = pageId;
        try {
            data = object.getJSONArray("data");
            paging = object.getJSONObject("paging");
            cursors = paging.getJSONObject("cursors");
            next = paging.getString("next");
        }catch(JSONException je){
        }
    }

    public String getFacebookPage() {
        return FacebookPage;
    }

    public JSONArray getData() {
        return data;
    }

    public JSONObject getPaging() {
        return paging;
    }

    public JSONObject getCursors() {
        return cursors;
    }

    public String getNext() {
        return next;
    }

    @Override
    public String toString() {
        return "FacebookPostMetadata{" +
                "FacebookPage='" + FacebookPage + '\'' +
                ", data=" + data +
                '}';
    }
}
