package com.ebang.xray;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by amankapur91 on 4/17/14.
 */
public class ProductItem {
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
        all.add(this);
        MainActivity.productViewAdapter.notifyDataSetChanged();
    }

    public void downloadImage(ProgressDialog progress){

        Log.d("XRAY", imgUrl);
        (new DownloadImageAsyncTask(this, progress)).execute();

    }
    public void showResultView(){

        Intent newIntent = new Intent(BaseActivity.context, ResultActivity.class);
        newIntent.putExtra("productItem", upcCode);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseActivity.context.startActivity(newIntent);
    }

    public static ProductItem find(String type, String value){
        for(ProductItem p: all){
            if (type.equals("name")){
                if (p.name.equals(value)){
                    return p;
                }
            }

            if (type.equals("upc")){
                if (p.upcCode.equals(value)){
                    return p;
                }
            }
        }
        return null;
    }

    public boolean hasAllergies(){
        for (Allergy al: allergies){
            ArrayList<Allergy> selectedAllergies = Allergy.selected();
            if (selectedAllergies.contains(al)){
                return true;
            }
        }
        return false;
    }



}
