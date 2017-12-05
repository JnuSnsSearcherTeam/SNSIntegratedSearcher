package kr.jnu.embedded.snssearcher.core;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import kr.jnu.embedded.snssearcher.base.App;
import kr.jnu.embedded.snssearcher.data.FacebookPage;
import kr.jnu.embedded.snssearcher.data.FacebookPagePost;
import kr.jnu.embedded.snssearcher.data.FacebookPostMetadata;

/**
 * Created by KANG on 2017-12-01.
 */

public class FacebookPagePostFetcher {
    private static final String TAG = "FacebookPostFetcher";
    private AccessToken accessToken;

    private ArrayList<JSONObject> idArray = new ArrayList<>();
    private ArrayList<String> pageArray = new ArrayList<>();

    private ArrayList<FacebookPage> pages_final = new ArrayList<>();
    private ArrayList<FacebookPagePost> posts_final = new ArrayList<>();

    private OnCompleteListener listener;

    private boolean isRequestAllSent;
    private int requestId = 0;

    public interface OnCompleteListener{
        void onComplete(ArrayList<FacebookPage> pages, ArrayList<FacebookPagePost> postArray);
    }

    public FacebookPagePostFetcher(AccessToken accessToken, OnCompleteListener listener) {
        this.accessToken = accessToken;
        this.listener = listener;
    }

    // 포스트 가져오기 시작.
    public void start(){
        startGetPageCandidates();
    }
    // 포스트 가져오기 완료
    private void complete(){
        listener.onComplete(pages_final, posts_final);
    }

    /* 1. 페이지 정보를 가져올 페이스북 ID 탐색.
     * Friend list:{"name":"강성원","id":"1493353087439584"}
     * Facebook IDs: [{"name":"강성원","id":"1493353087439584"}]
     */
    private void startGetPageCandidates(){
        Log.d(TAG,"Access Token : " + accessToken);
        if(accessToken == null){
            return;
        }

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

    /* 2. 좋아하는 페이지 정보를 가져옴.
     * Page IDs: [1906427612917982, 473895302976120, 229312883921077...]
     */
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
        likeParameters.putString("fields", "likes{id, name, picture}");
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
                getPageInfo();
                getFeedsFromPageArray();
            }
        });
        batch.executeAsync();
    }
    /* 3. 페이지 정보에서 피드를 가져옴.
    Graph Path :feed?limit=5&field=message&ids=1906427612917982,473895302976120,229312883921077,1443343142578555,1827931790805741,179511488728774
     */
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

    private void getPageInfo(){
        GraphRequestBatch batch = new GraphRequestBatch();
        for(String pid : pageArray){
            batch.add(GraphRequest.newGraphPathRequest(
                    accessToken,
                    pid + "?fields=name,picture"
                    , new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            if(response.getError() != null) {
                                Log.d(TAG, "[getPageInfo] Error occured : " + response.getError());
                                return;
                            }
                            FacebookPage page = new FacebookPage(response.getJSONObject());
                            Log.d(TAG,"Page Info : " + page);
                            pages_final.add(page);
                        }
                    }));
        }
        batch.executeAsync();
    }

    private void sendPageFeedRequest(final int requestId, final String pids){
        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "feed?limit=5&field=message&ids=" + pids
                , new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if(response.getError() != null) Log.d(TAG, "id likes error:" + response.getError());
                        Log.d(TAG, "Posts : " + response.getJSONObject());
                        parseMetadata(pids.split(","), response.getJSONObject());

                        if(isRequestAllSent() && requestId == getRequestId()){
                            Log.d(TAG,"All request Completed.");
                            complete();
                        }
                    }
                }
        );
        Log.d(TAG,"Graph Path :"+ request.getGraphPath());
        Log.d(TAG,"HTTP Method :"+ request.getHttpMethod());

        request.executeAsync();
    }
    private void parseMetadata(String[] pids, JSONObject response){
        ArrayList<FacebookPostMetadata> metadatas = new ArrayList<>();
        Log.d(TAG, "PIDS : " + pids.toString());
        try {
            for (String pid : pids) {
                Log.d(TAG, "pid: " + pid);
                JSONObject data = response.getJSONObject(pid);
                FacebookPostMetadata metadata = new FacebookPostMetadata(data, pid);
                metadatas.add(metadata);
                Log.d(TAG, "metadata: " + metadata.toString());
                JSONArray posts = metadata.getData();
                if(posts == null){
                    Log.d(TAG, "post is null ");
                    continue;
                }
                FacebookPage page = findPage(pid);
                for(int i=0; i<posts.length();i++){
                    FacebookPagePost post = new FacebookPagePost(page, posts.getJSONObject(i));
                    if(post.getCreatedDate() == null) continue;
                    posts_final.add(post);
                }
            }
        }catch(JSONException e){

        }
        Log.d(TAG, "Parsed metadata : " + metadatas);
    }

    private FacebookPage findPage(String pid) {
        for(FacebookPage page : pages_final){
            if(page.getID().equals(pid)) return page;
        }
        Log.d(TAG, "Null occured: " + pid);
        return null;
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
