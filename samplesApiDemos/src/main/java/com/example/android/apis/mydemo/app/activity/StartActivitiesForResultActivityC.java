package com.example.android.apis.mydemo.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android.apis.R;

/**
 * 打开多个Activity,A -> B -> C,finished回到A,并回传数据
 */
public class StartActivitiesForResultActivityC extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_for_result_c);
    }

    public void finishedPage(View v){
        setResult(Activity.RESULT_OK, new Intent().putExtra("data", "页面C回传数据"));
        finish();
    }
}
