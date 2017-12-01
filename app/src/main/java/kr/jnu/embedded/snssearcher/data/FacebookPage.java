package kr.jnu.embedded.snssearcher.data;

import android.graphics.Bitmap;
/**
 * Created by KANG on 2017-12-01.
 */

public class FacebookPage {
    String IconUrl;
    String Name;
    Bitmap Icon;

    public FacebookPage(String iconUrl, String name) {
        IconUrl = iconUrl;
        Name = name;
    }
}
