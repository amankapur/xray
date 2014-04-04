package com.ebang.xray;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;



public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */


    private ArrayAdapter<Allergy> allergyAdapter;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;

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

//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//                Log.d("XRAY", fileUri.toString());
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//
//                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "ONE_D_MODE" );
                startActivityForResult(intent, 0);
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//
//                Bitmap image = BitmapFactory.decodeFile(fileUri.toString());
//                Reader barReader =
//
//            } else {
//                Toast.makeText(this, "Failed to saved image, try again", Toast.LENGTH_LONG).show();
//            }
//        }

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



    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type){

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }
}
