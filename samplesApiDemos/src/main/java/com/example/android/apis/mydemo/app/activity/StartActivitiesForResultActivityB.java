package com.example.android.apis.mydemo.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android.apis.R;

/**
 * 打开多个Activity,A -> B -> C,finished回到A,并回传数据
 */
public class StartActivitiesForResultActivityB extends Activity {

    private static final int REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_for_result_b);
    }

    public void showPrePage(View v) {
        setResult(Activity.RESULT_OK, new Intent().putExtra("data", "页面B回传数据"));
        finish();
    }

    public void showNextPage(View v) {
        startActivity(new Intent(this, StartActivitiesForResultActivityC.class));
//        startActivityForResult(new Intent(this, StartActivitiesForResultActivityC.class), REQ_CODE);
        finish();
    }
}
