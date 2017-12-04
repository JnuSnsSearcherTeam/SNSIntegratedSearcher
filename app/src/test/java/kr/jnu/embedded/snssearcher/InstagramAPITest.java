package kr.jnu.embedded.snssearcher;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import kr.jnu.embedded.snssearcher.core.InstagramSearcher;

/**
 * Created by KANG on 2017-12-03.
 */

public class InstagramAPITest {
    @Test
    public void ParserConnectTest(){
        InstagramSearcher searcher = new InstagramSearcher(null);
        String result =
                searcher.getAccessTokenFromRedirectUrl("https://github.com/HardPlant#access_token=6659598238.977850c.d9c9676226ae471a878d34d6e177bbec");
        assertEquals(result, "6659598238.977850c.d9c9676226ae471a878d34d6e177bbec");
    }
    @Test
    public void unicodeTest(){
        String result = "{\"pagination\": {}, \"data\": [{\"id\": \"1662123452910654737_6659598238\", \"user\": {\"id\": \"6659598238\", \"full_name\": \"\\uac15\\uc131\\uc6d0\", \"profile_picture\": \"https://scontent.cdninstagram.com/t51.2885-19/s150x150/24127232_150484258921083_7059038351684272128_n.jpg\", \"username\": \"seongweon6846\"}, \"images\": {\"thumbnail\": {\"width\": 150, \"height\": 150, \"url\": \"https://scontent.cdninstagram.com/t51.2885-15/s150x150/e35/c0.29.320.320/24127025_863252110503032_2230202343004045312_n.jpg\"}, \"low_resolution\": {\"width\": 320, \"height\": 379, \"url\": \"https://scontent.cdninstagram.com/t51.2885-15/e35/24127025_863252110503032_2230202343004045312_n.jpg\"}, \"standard_resolution\": {\"width\": 320, \"height\": 379, \"url\": \"https://scontent.cdninstagram.com/t51.2885-15/e35/24127025_863252110503032_2230202343004045312_n.jpg\"}}, \"created_time\": \"1512360577\", \"caption\": {\"id\": \"17910968611001096\", \"text\": \"\\ud14c\\uc2a4\\ud2b8. \\ud589\\uc778\\ub450\\ubd80 \\uba39\\uace0\\uc2ed\\ub2e4\", \"created_time\": \"1512360577\", \"from\": {\"id\": \"6659598238\", \"full_name\": \"\\uac15\\uc131\\uc6d0\", \"profile_picture\": \"https://scontent.cdninstagram.com/t51.2885-19/s150x150/24127232_150484258921083_7059038351684272128_n.jpg\", \"username\": \"seongweon6846\"}}, \"user_has_liked\": false, \"likes\": {\"count\": 0}, \"tags\": [], \"filter\": \"Normal\", \"comments\": {\"count\": 0}, \"type\": \"image\", \"link\": \"https://www.instagram.com/p/BcRDHlfldUR/\", \"location\": null, \"attribution\": null, \"users_in_photo\": []}], \"meta\": {\"code\": 200}}";
        String resultWithOutData = "{\"pagination\": {}, \"data\": [], \"meta\": {\"code\": 200}}";
        try {
            JSONObject result1 = new JSONObject(result);
            JSONObject result2 = new JSONObject(resultWithOutData);
            assertNotEquals(result1, null);
            assertNotEquals(result2, null);
            System.out.println(result1);
            System.out.println(result2);
        } catch(JSONException e){
            e.printStackTrace();
        }
    }
    @Test
    public void getMyRecentMediaTest(){
        InstagramSearcher searcher = new InstagramSearcher(null);
        String accessToken =  searcher.getAccessTokenFromRedirectUrl("https://github.com/HardPlant#access_token=6659598238.977850c.d9c9676226ae471a878d34d6e177bbec");
        searcher.setAccessToken(accessToken);
        System.out.println(searcher.getMyRecentMedia());
    }
}
