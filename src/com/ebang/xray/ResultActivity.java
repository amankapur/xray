package com.ebang.xray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by amankapur91 on 4/6/14.
 */
public class ResultActivity extends Activity {

    private String code;
    private String codeType;

    private String TAG = "RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        Intent intent = getIntent();
        code = intent.getStringExtra("code");
        codeType = intent.getStringExtra("type");

        Parse.initialize(this, "f0zwTm7O0dYBWQ3liRpqABtiKE7wqKq1MGFw1bV1", "cIKlPXX1KSGhahJUILNC2X45Usl3SuEciFnvdLNR");

        ParseObject testObject = new ParseObject("a");
        testObject.put("code", code);
        testObject.put("type", codeType);
        testObject.saveInBackground();

        this.finish();



    }
}
