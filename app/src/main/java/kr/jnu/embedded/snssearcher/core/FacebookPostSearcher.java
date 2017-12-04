package kr.jnu.embedded.snssearcher.core;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import kr.jnu.embedded.snssearcher.data.FacebookPage;
import kr.jnu.embedded.snssearcher.data.FacebookPagePost;
import kr.jnu.embedded.snssearcher.data.FacebookPostMetadata;

/**
 * Created by KANG on 2017-12-01.
 */

public class FacebookPostSearcher {
    private static final String TAG = "FacebookPostSearcher";
    private ArrayList<FacebookPage> pages = new ArrayList<>();
    private ArrayList<FacebookPagePost> posts = new ArrayList<>();
    private ArrayList<FacebookPostMetadata> metadatas = new ArrayList<>();
    private String keyword;

    FacebookPagePostFetcher facebookPagePostFetcher;

    public FacebookPostSearcher(String keyword) {
        setKeyword(keyword);
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public void setParameters(ArrayList<JSONObject> pages, ArrayList<JSONObject> postArray){
        parsePages(pages,postArray);
        searchString(keyword);
    }

    public ArrayList<FacebookPage> getPages() {
        return pages;
    }

    public ArrayList<FacebookPagePost> getPosts() {
        return posts;
    }

    public void parsePages(ArrayList<JSONObject> pageInfo, ArrayList<JSONObject> fetchedPageResult){
        try {
            //페이지 리스트 가져오기
            for(JSONObject page : pageInfo) {
                pages.add(new FacebookPage(page));
            }
            // /pages~로 아이디에서 한번에 가져온 포스트들
            for(JSONObject object : fetchedPageResult) {
                Log.d(TAG, "Post Object: " + object);
                for (Iterator<String> itr = object.keys(); itr.hasNext(); ){
                    String key = (String)itr.next();
                    JSONObject item = (JSONObject) object.get(key);
                    FacebookPostMetadata metadata = new FacebookPostMetadata(item, key);
                    metadatas.add(metadata);
                }
            }
            // 메타데이터들의 배열에서 포스트 생성
            for(FacebookPostMetadata metadata : metadatas){
                JSONArray data = metadata.getData();
                if(data==null) continue;
                Log.d("item from metadata: ",data.toString());
                for(int i=0; i<data.length(); i++){
                    JSONObject post = data.getJSONObject(i);
                    Log.d("Post-made item: ",post.toString());
                    FacebookPage facebookPage = findPagebyId(metadata.getFacebookPage(), pages);
                    posts.add(new FacebookPagePost(facebookPage, post));
                }
            }
        } catch(JSONException je){
            je.printStackTrace();
        }
    }

    private FacebookPage findPagebyId(String key, ArrayList<FacebookPage> pages){
        for(FacebookPage page : pages){
            if(page.getID().equals(key)) return page;
        }
        return null;
    }

    private void searchString(String keyword){
        ArrayList<FacebookPagePost> toRemove = new ArrayList<>();
        if(posts == null) return;

        for(FacebookPagePost post : posts){
            if(!post.getMessage().contains(keyword))
                toRemove.add(post);
        }
        posts.removeAll(toRemove);
    }
}
