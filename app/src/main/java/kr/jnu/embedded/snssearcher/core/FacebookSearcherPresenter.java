package kr.jnu.embedded.snssearcher.core;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import kr.jnu.embedded.snssearcher.data.FacebookPagePost;

/**
 * Created by KANG on 2017-11-22.
 */

public class FacebookSearcherPresenter implements SNSSearcherContract.Presenter {
    public static final String TAG = "FacebookSearcher";

    private AccessToken accessToken;

    private SNSSearcherContract.View view;

    @Override
    public void setView(SNSSearcherContract.View view) {
        this.view = view;
    }

    @Override
    public void loadItem(final SNSSearcherContract.LoadCompleteListner listener) {
        final ArrayList<FacebookPagePost> result = new ArrayList<>();

        FacebookPagePostFetcher facebookPagePostFetcher
                = new FacebookPagePostFetcher(accessToken, result);

        facebookPagePostFetcher.onResult(new Callable() {
            @Override
            public Object call() throws Exception {
                listener.onComplete(result);
                return true;
            }
        });

        facebookPagePostFetcher.start();
    }

    public FacebookSearcherPresenter() {
        CallbackManager mCallbackManager;
        AccessTokenTracker accessTokenTracker;

        mCallbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken = currentAccessToken;
            }
        };
        accessTokenTracker.startTracking();
        accessToken = AccessToken.getCurrentAccessToken();
    }


}
