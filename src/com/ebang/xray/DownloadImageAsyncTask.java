package com.ebang.xray;

/**
 * Created by amankapur91 on 4/17/14.
 */


import android.os.AsyncTask;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: amankapur91
 * Date: 11/20/13
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class DownloadImageAsyncTask extends AsyncTask<String, Void, byte[]> {


    ProductItem productItem;

    public DownloadImageAsyncTask(ProductItem productItem) {

        this.productItem = productItem;

    }


    protected byte[] doInBackground(String... urls) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(productItem.imgUrl);
        byte[] imageBlob = new byte[0];
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageBlob;

    }

    protected void onPostExecute(byte[] image) {
        productItem.imgBytes = image;
    }
}