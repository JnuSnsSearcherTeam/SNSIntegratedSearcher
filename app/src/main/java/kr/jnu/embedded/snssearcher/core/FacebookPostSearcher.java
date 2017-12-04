package kr.jnu.embedded.snssearcher.core;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import kr.jnu.embedded.snssearcher.data.FacebookPage;
import kr.jnu.embedded.snssearcher.data.FacebookPagePost;

/**
 * Created by KANG on 2017-12-01.
 */

public class FacebookPostSearcher {
    private static final String TAG = "FacebookPostSearcher";
    private ArrayList<FacebookPage> pages = new ArrayList<>();
    private ArrayList<FacebookPagePost> posts = new ArrayList<>();
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
            for(JSONObject page : pageInfo) {
                String name = page.getString("name");
                String id = page.getString("id");
                String picture = page.getJSONObject("picture").getJSONObject("data").getString("url");
                pages.add(new FacebookPage(id, picture, name));
            }

            for(JSONObject object : fetchedPageResult) {
                Log.d(TAG, "Page Object: " + object);
                for (Iterator<String> itr = object.keys(); itr.hasNext(); ){
                    String key = (String)itr.next();
                    JSONObject item = (JSONObject) object.get(key);
                    JSONArray data = item.getJSONArray("data");
                    FacebookPage facebookPage = findPagebyId(key, pages);
                    if(facebookPage == null) continue;

                    for(int i=0; i<data.length(); i++){
                        posts.add(new FacebookPagePost(facebookPage, data.getJSONObject(i)));
                    }
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
        for(FacebookPagePost post : posts){
            if(!post.getMessage().contains(keyword))
                toRemove.add(post);
        }
        posts.removeAll(toRemove);
    }
}
