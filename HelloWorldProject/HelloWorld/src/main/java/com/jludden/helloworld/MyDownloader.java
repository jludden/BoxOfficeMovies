package com.jludden.helloworld;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Jason on 7/14/13.
 * In the background, will connect to a website and read a string from it
 */
 class MyDownloader extends AsyncTask<String, String, String> {
        public MyDownloader(){
           super();
        }

    /**
     * doInBackground - main method that gets called by parent thread
     *
     * @param args
     *  args[0]= url of website to go to
     * @return a String to display
     */
        protected String doInBackground(String... args) {
            Log.d("jludden", "started background task");
            String output=readStringFromWebsite(args[0]);
            return output;
        }

    /**
     * readFromWebsite - reads from a website
     *
     * @param url      - url of website to get string from
     * @return a string - pulled from the website
     */
       protected String readStringFromWebsite(String url)  {
        HttpClient httpclient = new DefaultHttpClient();

        HttpGet httppost = new HttpGet(url);

        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity ht = response.getEntity();
            BufferedHttpEntity buf = new BufferedHttpEntity(ht);
            InputStream is = buf.getContent();

            BufferedReader r = new BufferedReader(new InputStreamReader(is));

            StringBuilder total = new StringBuilder();
            String line;

            while ((line = r.readLine()) != null) {
                total.append(line + "\n");
            }
            return total.toString().trim();
        } catch (ClientProtocolException e) {
            Log.e("jludden","Client Protocol error",e);
        } catch (IOException e) {
            Log.e("jludden","IO error",e);
        }
            return "Error loading movies";
        }
}
