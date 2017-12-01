package kr.jnu.embedded.snssearcher.core;

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

import java.util.ArrayList;

/**
 * Created by KANG on 2017-11-22.
 */

public class FacebookSearcherPresenter implements SNSSearcherContract.Presenter {
    public static final String TAG = "FacebookSearcher";

    CallbackManager mCallbackManager;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;
    SNSSearcherContract.View view;
    ArrayList<JSONObject> idArray = new ArrayList<>();
    ArrayList<JSONObject> pageArray = new ArrayList<>();

    @Override
    public void setView(SNSSearcherContract.View view) {
        this.view = view;
    }

    @Override
    public void loadItem(SNSSearcherContract.LoadCompleteListner listener) {
        startGetPageCandidates(listener);
    }

    public FacebookSearcherPresenter() {
        mCallbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker(){
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        accessToken = AccessToken.getCurrentAccessToken();
        Log.d(TAG,"Access Token : " + accessToken);
    }


    public void getMe(final SNSSearcherContract.LoadCompleteListner listener) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        if(response.getError() != null) Log.d(TAG, response.getError().toString());
                        if(object == null) return;

                        Log.d(TAG, "GetMe Result:" + object.toString());
                        addFacebookId(object);
                        listener.onComplete(idArray);
                    }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void getPageIdFromLikes(final SNSSearcherContract.LoadCompleteListner listener){
        ArrayList<GraphRequest> requests = new ArrayList<>();
        GraphRequest userRequest = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if(response.getError() != null) Log.d(TAG, response.getError().toString());
                        if(object == null) return;
                        Log.d(TAG,"newMeRequest Completed.");
                        addFacebookId(object);
                    }
                }
        );
        Bundle likeParameters = new Bundle();
        likeParameters.putString("fields", "likes");
        userRequest.setParameters(likeParameters);
        requests.add(userRequest);

        try {
            for (JSONObject data : getFacebookIdArray()) {
                String id = data.getString("id");
                requests.add(
                        GraphRequest.newGraphPathRequest(
                        accessToken,
                        id + "likes"
                        , new GraphRequest.Callback() {
                            @Override
                            public void onCompleted(GraphResponse response) {
                                addPage(response.getJSONObject());
                            }
                        }
                ));
            }
        }catch(JSONException e){
            //e.printStackTrace();
        }
        GraphRequestBatch batch = new GraphRequestBatch(requests);
        batch.addCallback(new GraphRequestBatch.Callback() {
            @Override
            public void onBatchCompleted(GraphRequestBatch batch) {
                Log.d(TAG, "Page IDs: " + getPageArray());
                listener.onComplete(getPageArray());
            }
        });
        batch.executeAsync();

    }

    public void startGetPageCandidates(final SNSSearcherContract.LoadCompleteListner listener){
        Log.d(TAG,"Access Token : " + accessToken);
        GraphRequest friendRequest = GraphRequest.newMyFriendsRequest(
                accessToken,
                new GraphRequest.GraphJSONArrayCallback(){
                    public void onCompleted(JSONArray objects, GraphResponse response) {
                        if(response.getError() != null) Log.d(TAG, response.getError().toString());
                        if(objects == null) return;
                        Log.d(TAG,"newMyFriendsRequest Completed.");
                        addFacebookId(objects);
                    }
                }
        );
        Bundle idParameters = new Bundle();
        idParameters.putString("fields", "id");
        friendRequest.setParameters(idParameters);

        GraphRequestBatch batch = new GraphRequestBatch(friendRequest);
        batch.addCallback(new GraphRequestBatch.Callback() {
            @Override
            public void onBatchCompleted(GraphRequestBatch batch) {
                Log.d(TAG, "Facebook IDs: " + getFacebookIdArray());
                getPageIdFromLikes(listener);
            }
        });
        batch.executeAsync();
    }


    public void addFacebookId(JSONObject object){
        this.idArray.add(object);
    }

    public void addFacebookId(JSONArray objects){
        try {
            for(int i=0; i < objects.length(); i++) {
                this.idArray.add(objects.getJSONObject(i));
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    public ArrayList<JSONObject> getFacebookIdArray(){
        return this.idArray;
    }

    public ArrayList<JSONObject> getPageArray() {
        return pageArray;
    }

    public void addPage(JSONObject object) {
        this.pageArray.add(object);
    }

    public void addPage(JSONArray objects) {
        try {
            for(int i=0; i < objects.length(); i++) {
                this.pageArray.add(objects.getJSONObject(i));
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }
}
