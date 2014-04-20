package com.ebang.xray;

import android.content.Intent;
import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by amankapur91 on 4/17/14.
 */
public class ProductItem implements Serializable{
    public String name;
    public String imgUrl;
    public Bitmap imgBitmap;
    public byte[] imgBytes;
    public String upcCode;

    public static ArrayList<ProductItem> all = new ArrayList<ProductItem>();

    public ArrayList<Allergy> allergies = new ArrayList<Allergy>();

    public ProductItem(String name, String imgUrl, String upcCode){
        this.name = name;
        this.imgUrl = imgUrl;
        if (imgUrl != null){
            (new DownloadImageAsyncTask(this)).execute();
        }
        all.add(this);
        MainActivity.productViewAdapter.notifyDataSetChanged();
    }

    public void showResultView(){
        ArrayList<ProductItem> tList = new ArrayList<ProductItem>();
        tList.add(this);

        Intent newIntent = new Intent(BaseActivity.context, ResultActivity.class);
        newIntent.putExtra("productItem", tList);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseActivity.context.startActivity(newIntent);
    }

    public static ProductItem find(String name){
        for(ProductItem p: all){
            if (p.name.equals(name)){
                return p;
            }
        }
        return null;
    }


}
