package com.ebang.xray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private static String TAG = "XRAY";
    private DrawerLayout allergyDrawer;
    private ListView allergyListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        allergyDrawer = (DrawerLayout) findViewById(R.id.allergy_drawer_layout);
        allergyListView = (ListView) findViewById(R.id.allergiestListView);

        allergyDrawer.openDrawer(Gravity.LEFT);
        setTitle("Pick Allergens");

        Log.d(TAG, "on create called");
        populateAllergies();

        allergyListView.setAdapter(new AllergyArrayAdapter(this));

        int width = getResources().getDisplayMetrics().widthPixels/2;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) allergyListView.getLayoutParams();
        params.width = width;
        allergyListView.setLayoutParams(params);



        allergyDrawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {
                if (!Allergy.anySelected()){
                    Toast.makeText(getBaseContext(), R.string.no_allergies, Toast.LENGTH_SHORT).show();
                    allergyDrawer.openDrawer(Gravity.LEFT);
                }

            }

            @Override
            public void onDrawerOpened(View view) {
                setTitle("Pick Allergens");

            }

            @Override
            public void onDrawerClosed(View view) {
                setTitle("Scan Product Barcode");
                if (Allergy.anySelected()){
                    startScanActivity();
                }
            }

            @Override
            public void onDrawerStateChanged(int i) {

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

                Log.d(TAG, contents);
                Log.d(TAG, format);

                Intent newIntent = new Intent(this, ResultActivity.class);
                newIntent.putExtra("code", contents);
                newIntent.putExtra("type", format);
                startActivity(newIntent);

            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }

    }


    private void populateAllergies(){
        String[] names = new String[] {"Milk", "Eggs", "Peanuts", "Tree Nuts", "Fish", "Shellfish", "Soy", "Wheat", "Nightshades"};
        for(String n: names){
            new Allergy(n);
        }

    }

    private void startScanActivity(){
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "ONE_D_MODE" );
        startActivityForResult(intent, 0);
    }



}
