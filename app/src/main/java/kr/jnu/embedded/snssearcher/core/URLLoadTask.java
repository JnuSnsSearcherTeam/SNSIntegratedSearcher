package kr.jnu.embedded.snssearcher.core;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by KANG on 2017-12-04.
 */

public class URLLoadTask extends AsyncTask<String, Object, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected String doInBackground(String... urlString) {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString[0]);
            reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
Log.d ("task","finished");
            return buffer.toString();
        }catch (Exception e) {
        }finally {
        }

        return null;
    }
}
