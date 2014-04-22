package com.ebang.xray;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     */

    private static String TAG = "XRAY";
    private DrawerLayout allergyDrawer;
    private ListView allergyListView, productListView;
    public static ProductListArrayAdapter productViewAdapter;
    public static String KEY_EULA_ACCEPTED = "yo mama";

    private GestureDetectorCompat detector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if(!prefs.getBoolean(KEY_EULA_ACCEPTED, false)) {
            showEula();
            // Determine if EULA was accepted this time
            prefs.edit().putBoolean(KEY_EULA_ACCEPTED, true).commit();
        }

        allergyDrawer = (DrawerLayout) findViewById(R.id.allergy_drawer_layout);
        allergyListView = (ListView) findViewById(R.id.allergiestListView);

        allergyDrawer.openDrawer(Gravity.LEFT);
        setTitle("Pick Allergens");

        detector = new GestureDetectorCompat(this, new AllergyDrawerGestureListener());
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

        productListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });
        allergyDrawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {
                if (!Allergy.anySelected()){
                    Toast.makeText(context, "Please select at least one allergen", Toast.LENGTH_SHORT);
                    allergyDrawer.openDrawer(Gravity.LEFT);
                }

            }

            @Override
            public void onDrawerOpened(View view) {
                setTitle("Pick Allergens");

            }

            @Override
            public void onDrawerClosed(View view) {
                setTitle("xray");
                if (Allergy.anySelected() && ProductItem.all.isEmpty()){
                    startScanActivity();
                }
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });



    }

    private void showEula() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("DISCLAIMER");
        alert.setMessage("While we work to ensure that product information is correct, on occasion manufacturers may alter their ingredient lists. Actual product packaging and materials may contain more and/or different information than that shown on our Web site. We recommend that you do not solely rely on the information presented and that you always read labels, warnings, and directions before using or consuming a product.");
        alert.setView(productListView);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                System.exit(0);
            }
        });
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {

            }
      });

        alert.show();
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

                ProductItem p = ProductItem.find("upc", code);
                if (p == null){
                    UPCLookupAsyncTask task = new UPCLookupAsyncTask(progress);
                    task.execute(code);
                }
                else {
                    p.showResultView();
                }


            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }

    }


    private void populateAllergies(){
        String[] names = new String[] { "Gluten", "Peanuts", "Dairy", "Eggs", "Fish", "Wheat", "Corn",  "MSG","Nightshades", "Shellfish", "Soy", "Tree Nuts", "Fiber", "Vitamins"};
        for(String n: names){
            if (Allergy.find(n) == null){
                new Allergy(n);
            }
        }

    }

    private class AllergyDrawerGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,float velocityX, float velocityY) {
            try{
                if(e1.getX() - e2.getX() < 300 && Math.abs(velocityX) > 600) {
                    allergyDrawer.openDrawer(Gravity.LEFT);
                }
                return true;
            }
            catch (NullPointerException e){
                return  false;
            }
        }
    }


}
