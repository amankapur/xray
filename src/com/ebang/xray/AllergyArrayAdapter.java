
package com.ebang.xray;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by amankapur91 on 4/1/14.
 */
public class AllergyArrayAdapter extends ArrayAdapter<Allergy> {

    private Context context;
    private ArrayList<Allergy> allergies;

    public AllergyArrayAdapter(Context c) {
        super(c, R.layout.allergy_drawer_row, Allergy.all);

        context = c;
        this.allergies = Allergy.all;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView rowView = (TextView) inflater.inflate(R.layout.allergy_drawer_row, null);

        Allergy allergy = allergies.get(position);
        rowView.setText(allergy.name);

        rowView.setTextColor(rowView.getResources().getColor(R.color.Black));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                Allergy al = Allergy.find(tv.getText().toString());


                if (!al.selected){
                    tv.setTextColor(tv.getResources().getColor(R.color.Blue));
                    al.selected = true;
                }
                else {
                    tv.setTextColor(tv.getResources().getColor(R.color.Black));
                    al.selected = false;
                }
            }
        });
        return  rowView;
    }



}