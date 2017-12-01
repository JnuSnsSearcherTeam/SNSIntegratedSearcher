package kr.jnu.embedded.snssearcher.core;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

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
        final ArrayList<JSONObject> result = new ArrayList<>();

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
