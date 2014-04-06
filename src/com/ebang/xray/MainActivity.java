package com.ebang.xray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;



public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private ArrayAdapter<Allergy> allergyAdapter;
    private static String TAG = "XRAY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListView allergiesListView = (ListView) findViewById(R.id.allergiestListView);

        allergyAdapter = new AllergyArrayAdapter(this, Allergy.all);
        allergiesListView.setAdapter(allergyAdapter);

        Log.d(TAG, "on create called");
        populateAllergies();


        Button pictureButton = (Button) findViewById(R.id.takePicture);


        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "ONE_D_MODE" );
                startActivityForResult(intent, 0);
            }

        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "on resume called");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "on pause called");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "on destroy called");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {


        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                Log.d("XRAY", contents);
                Log.d("XRAY", format);



            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }

    }


    private void populateAllergies(){
        String[] names = new String[] {"Milk", "Eggs", "Peanuts", "Tree Nuts", "Fish", "Shellfish", "Soy", "Wheat"};
        for(String n: names){
            new Allergy(n);
        }
        allergyAdapter.notifyDataSetChanged();
    }

}
