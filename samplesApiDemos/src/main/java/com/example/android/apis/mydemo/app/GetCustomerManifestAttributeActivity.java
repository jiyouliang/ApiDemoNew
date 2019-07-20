package com.example.android.apis.mydemo.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.apis.R;

/**
 * 获取AndroidManifest自定义属性
 * 文档：https://developer.android.com/guide/topics/manifest/manifest-intro
 */
public class GetCustomerManifestAttributeActivity extends Activity implements View.OnClickListener {

    private Button mBtn1;
    private Button mBtn2;
    private TextView mTvTitle;
    private TextView mTvContent;
    private PackageManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_customer_manifest_attribute);
        initView();
    }

    private void initView() {
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvContent = (TextView) findViewById(R.id.tv_content);

        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);

        pm = getPackageManager();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                getApplicationAttribute();
                break;
            case R.id.btn2:
                getActivityAttribute();
                break;
        }
    }

    private void getActivityAttribute() {
        mTvContent.setText("");
        ComponentName cn = new ComponentName(this, GetCustomerManifestAttributeActivity.class);
        try {
            ActivityInfo info = pm.getActivityInfo(cn, PackageManager.GET_META_DATA);
            if (info != null && info.metaData != null) {
                String metaData = info.metaData.getString("mydemo_activity_mate_info");
                mTvContent.setText("获取成功mydemo_activity_mate_info=" + metaData);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getApplicationAttribute() {
        mTvContent.setText("");
        try {
            ApplicationInfo appInfo = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null && appInfo.metaData != null) {
                String mylabel = appInfo.metaData.getString("myapplication_mate_info");
                mTvContent.setText("获取成功mate myapplication_mate_info=" + mylabel);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
