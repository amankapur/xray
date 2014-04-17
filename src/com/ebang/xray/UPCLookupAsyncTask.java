package com.ebang.xray;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Cory on 4/12/14.
 */
public class UPCLookupAsyncTask extends AsyncTask<String,Void, JSONObject> {


    public static final String API_BASE_URL =  "http://allergy-xray.herokuapp.com/lookup/";

    @Override
    protected JSONObject doInBackground(String... upc) {

        String upcCode = upc[0];
        String uri = Uri.parse(API_BASE_URL + upcCode)
                .buildUpon()
                .build().toString();

        try {
            JSONObject json = new JSONObject(urlRequestString(uri));
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }



    protected void onPostExecute(JSONObject result) {
        Log.d("API", result.toString());
    }




    public String urlRequestString(String url) {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.d(UPCLookupAsyncTask.class.toString(), "Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

}