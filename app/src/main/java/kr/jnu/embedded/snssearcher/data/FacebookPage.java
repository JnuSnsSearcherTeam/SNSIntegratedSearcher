package kr.jnu.embedded.snssearcher.data;

import android.graphics.Bitmap;
/**
 * Created by KANG on 2017-12-01.
 */

public class FacebookPage {
    Bitmap icon;
    String name;

    public FacebookPage(Bitmap icon, String name) {
        this.icon = icon;
        this.name = name;
    }
}
