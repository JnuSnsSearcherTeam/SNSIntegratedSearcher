package kr.jnu.embedded.snssearcher.core;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by KANG on 2017-12-01.
 */

public class FacebookPagePostFetcher {
    private static final String TAG = "FacebookPostFetcher";
    private AccessToken accessToken;
    private Callable onFetchComplete;

    private ArrayList<JSONObject> idArray = new ArrayList<>();
    private ArrayList<String> pageArray = new ArrayList<>();
    private ArrayList<JSONObject> resultArray;


    private boolean isRequestAllSent;
    private int requestId = 0;

    public FacebookPagePostFetcher(AccessToken accessToken, ArrayList<JSONObject> resultArray) {
        this.accessToken = accessToken;
        this.resultArray = resultArray;
    }

    public void onResult(Callable callable){
        this.onFetchComplete = callable;
    }

    public void start(){
        startGetPageCandidates();
    }

    private void startGetPageCandidates(){
        Log.d(TAG,"Access Token : " + accessToken);

        GraphRequest friendRequest = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        if(response.getError() != null) Log.d(TAG, response.getError().toString());
                        if(object == null) return;

                        Log.d(TAG, "Friend list:" + object.toString());
                        addFacebookId(object);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "friends");

        GraphRequestBatch batch = new GraphRequestBatch(friendRequest);
        batch.addCallback(new GraphRequestBatch.Callback() {
            @Override
            public void onBatchCompleted(GraphRequestBatch batch) {
                Log.d(TAG, "Facebook IDs: " + getFacebookIdArray());
                getPageIdFromLikes();
            }
        });
        batch.executeAsync();
    }

    private void getPageIdFromLikes(){
        ArrayList<GraphRequest> requests = new ArrayList<>();
        GraphRequest userRequest = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if(response.getError() != null) Log.d(TAG, response.getError().toString());
                        if(object == null) return;
                        Log.d(TAG,"newMeRequest Completed.");
                        try {
                            addPage(object.getJSONObject("likes").getJSONArray("data"));
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
        );
        Bundle likeParameters = new Bundle();
        likeParameters.putString("fields", "likes{id}");
        userRequest.setParameters(likeParameters);
        requests.add(userRequest);

        try {
            for (JSONObject data : getFacebookIdArray()) {
                String id = data.getString("id");
                requests.add(
                        GraphRequest.newGraphPathRequest(
                                accessToken,
                                id + "/likes"
                                , new GraphRequest.Callback() {
                                    @Override
                                    public void onCompleted(GraphResponse response) {
                                        if(response.getError() != null) Log.d(TAG, "id likes error:" + response.getError());
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
                getFeedsFromPageArray();
            }
        });
        batch.executeAsync();
    }
    private void getFeedsFromPageArray(){
        StringBuffer pages = new StringBuffer();
        int count = 0;

        for(String id : pageArray){
            pages.append(id).append(',');
            count++;
            if(pageArray.isEmpty() || count > 5){
                pages.deleteCharAt(pages.length() - 1);
                requestId++;
                sendPageFeedRequest(requestId, pages.toString());
                pages.delete(0, pages.length());
                count = 0;
            }
        }
        isRequestAllSent = true;
    }



    private void sendPageFeedRequest(final int requestId, String pids){
        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "feed?limit=5&field=message&ids=" + pids
                , new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if(response.getError() != null) Log.d(TAG, "id likes error:" + response.getError());
                        addPage(response.getJSONObject());
                        Log.d(TAG, "Pages : " + response.getJSONObject());
                        if(isRequestAllSent() && requestId == getRequestId()){
                            Log.d(TAG,"All request Completed.");
                            try {
                                onFetchComplete.call();
                            } catch(Exception e){
                            }
                        }
                    }
                }
        );
        Log.d(TAG,"Graph Path :"+ request.getGraphPath());
        Log.d(TAG,"HTTP Method :"+ request.getHttpMethod());

        request.executeAsync();
    }

    private int getRequestId() {
        return requestId;
    }

    private boolean isRequestAllSent() {
        return isRequestAllSent;
    }

    private void addFacebookId(JSONObject object){
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

    private ArrayList<JSONObject> getFacebookIdArray(){
        return this.idArray;
    }

    private ArrayList<String> getPageArray() {
        return pageArray;
    }

    private void addPage(JSONObject object) {
        try {
            this.pageArray.add(object.getString("id"));
        } catch (JSONException e){

        }
    }

    private void addPage(JSONArray objects) {
        try {
            for(int i=0; i < objects.length(); i++) {
                addPage(objects.getJSONObject(i));
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }
}
