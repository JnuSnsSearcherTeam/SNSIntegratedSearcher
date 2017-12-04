package kr.jnu.embedded.snssearcher;

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
    public void getMyRecentMediaTest(){
        InstagramSearcher searcher = new InstagramSearcher(null);
        String accessToken =  searcher.getAccessTokenFromRedirectUrl("https://github.com/HardPlant#access_token=6659598238.977850c.d9c9676226ae471a878d34d6e177bbec");
        searcher.setAccessToken(accessToken);
        System.out.println(searcher.getMyRecentMedia());
    }
}
