package com.example.android.apis.mydemo.service;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.apis.R;

public class LocationProviderActivity extends Activity implements View.OnClickListener {

    private Button mBtnLocProvider;
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_provider);
        initView();
    }

    private void initView() {
        mBtnLocProvider = (Button) findViewById(R.id.btn_loc_provider);

        mBtnLocProvider.setOnClickListener(this);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_loc_provider:
                if(null != mLocationManager){
                    boolean isGPS = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    boolean isNetWork = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                    boolean isPassive = mLocationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);

                    if(isGPS){
                        Toast.makeText(this, "GPS定位", Toast.LENGTH_SHORT).show();
                    }
                    if(isNetWork){
                        Toast.makeText(this, "网络定位", Toast.LENGTH_SHORT).show();
                    }
                    if (isPassive) {
                        Toast.makeText(this, "Passive", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
