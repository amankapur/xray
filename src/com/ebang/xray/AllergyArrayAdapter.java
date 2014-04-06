package com.ebang.xray;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

/**
 * Created by amankapur91 on 4/1/14.
 */
public class AllergyArrayAdapter extends ArrayAdapter<Allergy> {

    private Context context;
    private ArrayList<Allergy> allergies;

    public AllergyArrayAdapter(Context c, ArrayList<Allergy> allergies) {
        super(c, R.layout.allergy_row, allergies);

        context = c;
        this.allergies = allergies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.allergy_row, null);

        Allergy allergy = allergies.get(position);

        CheckBox selectedBoxView = (CheckBox) rowView.findViewById(R.id.selectedBox);
        selectedBoxView.setText(allergy.name);
        selectedBoxView.setTextSize(30);


        selectedBoxView.setChecked(allergy.selected);

        selectedBoxView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Allergy al = Allergy.find(buttonView.getText().toString());
                if (al != null){
                    al.selected = isChecked;
                }
            }
        });
        return  rowView;
    }



}
