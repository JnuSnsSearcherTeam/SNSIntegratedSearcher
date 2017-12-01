package kr.jnu.embedded.snssearcher.core;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.AccessTokenManager;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KANG on 2017-11-22.
 */

public class FacebookSearcher implements SNSSearcher{
    CallbackManager mCallbackManager;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;

    public FacebookSearcher() {
        mCallbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker(){
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        accessToken = AccessToken.getCurrentAccessToken();
    }

    public ArrayList<JSONObject> getPageLists(){
        final ArrayList<JSONObject> resultArray = new ArrayList<>();
        Bundle userParameters = new Bundle();
        userParameters.putString("fields", "likes");
        GraphRequest userRequest = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        resultArray.add(object);
                    }
                }
        );
        userRequest.setParameters(userParameters);

        GraphRequest friendRequest = GraphRequest.newMyFriendsRequest(
                accessToken,
                new GraphRequest.GraphJSONArrayCallback(){
                    @Override
                    public void onCompleted(JSONArray objects, GraphResponse response) {
                        for(int i=0; i < objects.length(); i++) {
                            try {
                                resultArray.add(objects.getJSONObject(i));
                            }
                            catch(JSONException e){
                                //e.printStackTrace();
                            }
                        }
                    }
                }
        );
        userRequest.setParameters(userParameters);

        GraphRequestBatch batch = new GraphRequestBatch(userRequest, friendRequest);
        batch.executeAndWait();
        return resultArray;

    }
}
