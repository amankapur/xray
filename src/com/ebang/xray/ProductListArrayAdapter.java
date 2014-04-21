package com.ebang.xray;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by amankapur91 on 4/17/14.
 */
public class ProductListArrayAdapter extends ArrayAdapter<ProductItem> {

    private Context context;

    public ProductListArrayAdapter(Context c){
        super(c, R.layout.product_row_view, ProductItem.all);

        context = c;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ProductItem p = ProductItem.all.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.product_row_view, null);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.productImageView);
        TextView descView = (TextView) rowView.findViewById(R.id.productDescView);
        Log.d("XRAY", "getting image view in getview");
        if (p.imgBitmap == null && p.imgBytes != null){
            p.imgBitmap = BitmapFactory.decodeByteArray(p.imgBytes, 0, p.imgBytes.length);
            imageView.setImageBitmap(p.imgBitmap);
            Log.d("XRAY", "setting image view in getview");
        }
        descView.setText(p.name);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v.findViewById(R.id.productDescView);
                ProductItem p = ProductItem.find("name", tv.getText().toString());
                p.showResultView();
            }
        });
        return rowView;

    }
}
