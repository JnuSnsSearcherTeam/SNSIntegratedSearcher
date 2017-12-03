package kr.jnu.embedded.snssearcher.data;

import android.media.Image;

import kr.jnu.embedded.snssearcher.base.App;
import kr.jnu.embedded.snssearcher.base.Item;

/**
 * Created by KANG on 2017-12-01.
 */

public class FacebookPagePost {
    FacebookPage page;
    String message;

    public FacebookPagePost(FacebookPage page, String message) {
        this.page = page;
        this.message = message;
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
        ,"아이콘"
        ,"날짜"
        ,"텍스트"
        ,"텍스트이미지");
    }
}
