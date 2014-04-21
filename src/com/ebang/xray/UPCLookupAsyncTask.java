package com.ebang.xray;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Cory on 4/12/14.
 */
public class UPCLookupAsyncTask extends AsyncTask<String,Void, JSONObject> {


    public static final String API_BASE_URL =  "http://allergy-xray.herokuapp.com/lookup/";
    private String upcCode;
    private ProgressDialog progress;

    public UPCLookupAsyncTask(ProgressDialog progress){
        super();
        this.progress = progress;

    }

    @Override
    protected JSONObject doInBackground(String... upc) {

        upcCode = upc[0];
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

    @Override
    protected void onPreExecute(){

        progress.show();

    }

    protected void onPostExecute(JSONObject result) {

        if (result == null){
            Toast.makeText(BaseActivity.context, "Error to create product", Toast.LENGTH_LONG).show();
            return;
        }

        ProductItem p = createProductItem(result);
        p.downloadImage(progress);
        p.upcCode = upcCode;
        Log.d("XRAY", upcCode);

        if (p == null){
            Toast.makeText(BaseActivity.context, "Error to create product", Toast.LENGTH_LONG).show();
        }


    }

    private ProductItem createProductItem(JSONObject result) {
        try {
            JSONObject product = result.getJSONObject("product");
            String desc = product.getString("description");
            String upcCode = product.getString("upc_code");
            String imgUrl = product.getString("image");
            ProductItem p = new ProductItem(desc, imgUrl, upcCode);

            Log.d("XRAY", "allergies from API : " + result.getJSONArray("allergens").toString());
            p.allergies = getAllergies(result.getJSONArray("allergens"));
            Log.d("XRAY", "ALLERGY COUNT IS : " + p.allergies.size());
            return p;
        } catch (JSONException e) {
            Log.d("XRAY", "FAILED TO CREATE PRODUCT");
            e.printStackTrace();
        }
        return null;
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

    private ArrayList<Allergy> getAllergies(JSONArray jArray) throws JSONException {
        ArrayList<Allergy> listdata = new ArrayList<Allergy>();
        if (jArray != null) {
            for (int i=0;i<jArray.length();i++){
                listdata.add(Allergy.find(jArray.get(i).toString()));
            }
        }
        return listdata;

    }

}