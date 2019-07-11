package com.example.android.apis.mydemo.app;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.apis.R;

public class DeviceIdActivity extends Activity implements View.OnClickListener {

    private static final int CODE_PHONE_STATE = 1;
    private Button mBtn;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_id);
        initView();
    }

    private void initView() {
        mBtn = (Button) findViewById(R.id.btn);
        mTv = (TextView) findViewById(R.id.tv);

        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                getDeviceId();
                break;
        }
    }

    private void getDeviceId() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            //未获得read_phone_state权限，动态申请
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, CODE_PHONE_STATE);
        }else{
            setContent();
        }
    }

    private void setContent() {
        TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telMgr.getDeviceId();
        System.out.println("deviceId:" + deviceId);
        mTv.setText("当前设备id:" + deviceId);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CODE_PHONE_STATE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            setContent();
        }else {
            Toast.makeText(this, "未授予权限，请重新授予", Toast.LENGTH_SHORT).show();
        }
    }
}
