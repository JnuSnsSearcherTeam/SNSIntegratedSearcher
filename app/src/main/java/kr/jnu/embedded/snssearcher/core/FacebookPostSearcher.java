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
    public void setParameters(ArrayList<FacebookPage> pages, ArrayList<FacebookPagePost> postArray){
        this.pages = pages;
        this.posts = postArray;
        searchString(keyword);
    }

    public ArrayList<FacebookPage> getPages() {
        return pages;
    }

    public ArrayList<FacebookPagePost> getPosts() {
        return posts;
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
