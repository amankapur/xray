package com.ebang.xray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by amankapur91 on 4/20/14.
 */
public class BaseActivity extends Activity {

    public static Context context;
    public static ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        context = getApplicationContext();

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Analyzing your product...");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
//            case R.id.showListItem:
//
//                return true;
            case R.id.showScanner:
                startScanActivity();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void startScanActivity(){
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "ONE_D_MODE" );
        startActivityForResult(intent, 0);
    }
}
