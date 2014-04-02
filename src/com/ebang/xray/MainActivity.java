package com.ebang.xray;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */


    private ArrayAdapter<Allergy> allergyAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListView allergiesListView = (ListView) findViewById(R.id.allergiestListView);

        allergyAdapter = new AllergyArrayAdapter(this, Allergy.all);
        allergiesListView.setAdapter(allergyAdapter);

        populateAllergies();


        Button pictureButton = (Button) findViewById(R.id.takePicture);

        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void populateAllergies(){
        String[] names = new String[] {"Milk", "Eggs", "Peanuts", "Tree Nuts", "Fish", "Shellfish", "Soy", "Wheat"};
        for(String n: names){
            Allergy al = new Allergy(n);
            Allergy.all.add(al);
        }
        allergyAdapter.notifyDataSetChanged();
    }

}
