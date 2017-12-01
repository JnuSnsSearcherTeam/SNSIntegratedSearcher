package kr.jnu.embedded.snssearcher.data;

import android.graphics.Bitmap;
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

    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public Bitmap getIcon() {
        return Icon;
    }
}
