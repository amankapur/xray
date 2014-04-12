package com.ebang.xray;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import com.parse.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cory on 4/12/14.
 */
public class UPCLookupAsyncTask extends AsyncTask<String,Void, JSONObject> {


    public static final String APP_KEY = "/9dUE1VQuQI/";
    public static final String APP_AUTH = "Nw97Z3l7b4Na2Bh5";
    public static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    public static final String field_names = "description,brand,nutrition,ingredients,manufacturer";
    public static final String API_BASE_URL =  "http://digit-eyes.com/gtin/v2_0/";

    @Override
    protected JSONObject doInBackground(String... upc) {

        String upcCode = upc[0];
        String uri = Uri.parse(API_BASE_URL)
                .buildUpon()
                .appendQueryParameter("app_key", APP_KEY)
                .appendQueryParameter("upc_code", upcCode)
                .appendQueryParameter("language","en")
                .appendQueryParameter("signature",getAuthToken(upcCode))
                .appendQueryParameter("field_names", field_names)
                .build().toString();

        try {
            JSONObject json = new JSONObject(urlRequestString(uri));
            Log.d("API", json.toString());
            Log.d("API", json.getString("description"));
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }



    protected void onPostExecute(JSONObject result) {
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

    public String getAuthToken(String upcCode){
        SecretKeySpec signingKey = new SecretKeySpec(APP_AUTH.getBytes(), HMAC_SHA1_ALGORITHM);


        Mac mac = null;
        try {
            mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return Base64.encodeBase64String(mac.doFinal(upcCode.getBytes()));

    }
}