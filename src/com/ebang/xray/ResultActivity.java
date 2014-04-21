package com.ebang.xray;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by amankapur91 on 4/6/14.
 */
public class ResultActivity extends BaseActivity {

    private ProductItem productItem;
    private ImageView imageView, iconView;
    private TextView nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        Intent i = getIntent();

        productItem = ProductItem.find("upc", i.getStringExtra("productItem"));


        imageView = (ImageView) findViewById(R.id.resultImage);
        nameView = (TextView) findViewById(R.id.resultName);
        iconView = (ImageView) findViewById(R.id.resultIcon);

        imageView.setImageBitmap(productItem.imgBitmap);
        nameView.setText(productItem.name);

        if (productItem.hasAllergies()){
            iconView.setImageDrawable(getResources().getDrawable(R.drawable.ic_no));
        }
        else {
            iconView.setImageDrawable(getResources().getDrawable(R.drawable.ic_yes));
        }


    }
}
