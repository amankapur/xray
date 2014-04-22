package com.ebang.xray;

/**
 * Created by amankapur91 on 4/17/14.
 */


import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: amankapur91
 * Date: 11/20/13
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class DownloadImageAsyncTask extends AsyncTask<String, Void, byte[]> {


    private ProductItem productItem;
    private ProgressDialog progress;
    private DefaultHttpClient client;


    public DownloadImageAsyncTask(ProductItem productItem, ProgressDialog progress) {

        this.productItem = productItem;
        this.progress = progress;
        client = new DefaultHttpClient();

    }


    protected byte[] doInBackground(String... urls) {



        if (productItem.imgUrl == "null"){

            try {
                String uri = Uri.parse("http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + URLEncoder.encode(productItem.name, "UTF-8"))
                        .buildUpon()
                        .build().toString();
                JSONObject json = new JSONObject(UPCLookupAsyncTask.urlRequestString(uri));
                JSONObject t = (JSONObject) json.getJSONObject("responseData").getJSONArray("results").get(0);
                productItem.imgUrl = t.getString("url");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        doDownload(productItem.imgUrl);

        return productItem.imgBytes;


    }

    private void doDownload(String imgUrl) {
        HttpGet request = new HttpGet(productItem.imgUrl);
        byte[] imageBlob = new byte[0];
        Log.d("XRAY", "trying to downloading image : " + productItem.imgUrl);
        try{
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            int imageLength = (int)(entity.getContentLength());
            InputStream is = entity.getContent();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[imageLength];

            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();

            imageBlob = buffer.toByteArray();
            productItem.imgBytes = imageBlob;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onPostExecute(byte[] image) {

        MainActivity.productViewAdapter.notifyDataSetChanged();
        productItem.showResultView();
        progress.dismiss();

    }
}