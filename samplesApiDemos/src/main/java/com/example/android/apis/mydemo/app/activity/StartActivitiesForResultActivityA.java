package com.example.android.apis.mydemo.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.android.apis.R;

/**
 * 打开多个Activity,A -> B -> C,finished回到A,并回传数据
 */
public class StartActivitiesForResultActivityA extends Activity {

    private static final int REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_for_result);
    }

    public void nextPage(View v){
        startActivityForResult(new Intent(this, StartActivitiesForResultActivityB.class), REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == REQ_CODE){
            Toast.makeText(this, data.getStringExtra("data"), Toast.LENGTH_SHORT).show();
        }
    }
}
