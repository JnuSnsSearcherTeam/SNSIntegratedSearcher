package kr.jnu.embedded.snssearcher.core;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kr.jnu.embedded.snssearcher.base.App;
import kr.jnu.embedded.snssearcher.data.TwitterItem;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;


public class TwitterSearcher {
    private static String TAG = "TwitterSearcher";
    ArrayList<Object> result;
    String keyword;
    Twitter twitter;

    public TwitterSearcher() {
        TwitterFactory tf = new TwitterFactory(App.getTwitterAccessToken());
        twitter = tf.getInstance();
    }

    public ArrayList<Object> getTwitterSearch() {
        result.add(new TwitterItem("t1","","","",""));
        result.add(new TwitterItem("t2","","","",""));
        result.add(new TwitterItem("t3","","","",""));
        result.add(new TwitterItem("t4","","","",""));
        result.add(new TwitterItem("t5","","","",""));
        /*
        *  StatusJSONImpl{createdAt=Tue Dec 05 18:00:03 KST 2017, id=937969808993374210, text='As talented as @vincey14 is. He will always struggle at test level because he doesn't give himself a chance!', source='<a href="http://twitter.com" rel="nofollow">Twitter Web Client</a>', isTruncated=false, inReplyToStatusId=-1, inReplyToUserId=-1, isFavorited=false, isRetweeted=false, favoriteCount=0, inReplyToScreenName='null', geoLocation=null, place=null, retweetCount=0, isPossiblySensitive=false, lang='en', contributorsIDs=[], retweetedStatus=null, userMentionEntities=[UserMentionEntityJSONImpl{name='James Vince', screenName='vincey14', id=281450539}], urlEntities=[], hashtagEntities=[], mediaEntities=[], symbolEntities=[], currentUserRetweetId=-1, user=UserJSONImpl{id=215041975, name='Jonny Kerr', email='null', screenName='JonnyKerr96', location='Hartley Wintney', description='Business and Law undergraduate at UWE', isContributorsEnabled=false, profileImageUrl='http://pbs.twimg.com/profile_images/905920473548161024/KPIetHwR_normal.jpg', profileImageUrlHttps='https://pbs.twimg.com/profile_images/905920473548161024/KPIetHwR_normal.jpg', isDefaultProfileImage=false, url='null', isProtected=false, followersCount=260, status=null, profileBackgroundColor='9AE4E8', profileTextColor='333333', profileLinkColor='0084B4', profileSidebarFillColor='DDFFCC', profileSidebarBorderColor='BDDCAD', profileUseBackgroundImage=true, isDefaultProfile=false, showAllInlineMedia=false, friendsCount=891, createdAt=Sat Nov 13 06:20:42 KST 2010, favouritesCount=562, utcOffset=-1, timeZone='null', profileBackgroundImageUrl='http://abs.twimg.com/images/themes/theme16/bg.gif', profileBackgroundImageUrlHttps='https://abs.twimg.com/images/themes/theme16/bg.gif', profileBackgroundTiled=false, lang='en', statusesCount=5959, isGeoEnabled=true, isVerified=false, translator=false, listedCount=3, isFollowRequestSent=false, withheldInCountries=null}, withHeldInCountries=null, quotedStatusId=-1, quotedStatus=null}
        * 을 TwitterItem()으로 바꿔서 넣으면 됩니다.
        * 사용자 사진은 url로 넣으면 바뀌어서 나와요
        * */
        return result;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
