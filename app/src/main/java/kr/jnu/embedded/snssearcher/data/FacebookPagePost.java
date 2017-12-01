package kr.jnu.embedded.snssearcher.data;

import android.media.Image;

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

    @Override
    public String toString() {
        return "FacebookPagePost{" +
                "page=" + page +
                ", message='" + message + '\'' +
                '}';
    }
}
