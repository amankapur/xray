package com.ebang.xray;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;


public class MainActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     */

    private static String TAG = "XRAY";
    private DrawerLayout allergyDrawer;
    private ListView allergyListView, productListView;
    public static ProductListArrayAdapter productViewAdapter;

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

        productViewAdapter = new ProductListArrayAdapter(this);

        productListView = (ListView) findViewById(R.id.productsListView);
        productListView.setAdapter(productViewAdapter);

        allergyDrawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {

            }

            @Override
            public void onDrawerOpened(View view) {
                setTitle("Pick Allergens");

            }

            @Override
            public void onDrawerClosed(View view) {
                setTitle("xray");
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
                String code = intent.getStringExtra("SCAN_RESULT");
                progress.show();
                UPCLookupAsyncTask task = new UPCLookupAsyncTask();

                task.execute(code);


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




}
