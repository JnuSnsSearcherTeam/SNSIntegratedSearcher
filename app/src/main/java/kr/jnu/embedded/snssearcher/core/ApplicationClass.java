package kr.jnu.embedded.snssearcher.core;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by KANG on 2017-12-01.
 */

public class ApplicationClass extends Application {
    @Override
    public void onCreate() { //모든 액티비티/리시버/서비스 생성 전
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
