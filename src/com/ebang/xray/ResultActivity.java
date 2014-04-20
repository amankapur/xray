package com.ebang.xray;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by amankapur91 on 4/6/14.
 */
public class ResultActivity extends BaseActivity {

    private ProductItem productItem;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        Intent i = getIntent();
        ArrayList ar = i.getStringArrayListExtra("productItem");
        if (ar != null){
            productItem = (ProductItem) ar.get(0);
        }

    }
}
