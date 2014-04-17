package com.ebang.xray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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

        UPCLookupAsyncTask task = new UPCLookupAsyncTask();

        task.execute(code);



    }
}
