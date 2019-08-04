package com.example.android.apis.mydemo.app;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.apis.R;

import java.util.ArrayList;
import java.util.List;

public class RuntimePermissionActivity extends Activity implements View.OnClickListener {

    private static final int REQ_CODE = 1;
    private Button mBtn;
    /**
     * 需要的权限
     */
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime_permission);
        initView();
    }

    private void initView() {
        mBtn = (Button) findViewById(R.id.btn);

        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                requestPermissions();
                break;
        }
    }

    /**
     * 获取未授权权限
     */
    private List<String> checkPermissions() {
        List<String> permissions = new ArrayList<>();
        for(String permission : PERMISSIONS){
            if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                continue;
            }
            // 存储未授权权限
            permissions.add(permission);
        }
        return permissions;
    }

    /**
     * 一次申请多个权限
     *
     * @return
     */
    private void requestPermissions() {
        List<String> permissions = checkPermissions();
        if (permissions == null || permissions.size() == 0) {
            Toast.makeText(this, "已获取所有权限", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] pers = new String[permissions.size()];
        permissions.toArray(pers);

        // 调用系统api申请权限
        requestPermissions(pers, REQ_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_CODE) {
            boolean result = true;
            //只要有一个未授权，就认为失败
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    result = false;
                    break;
                }
            }
            String info = String.format("授权%s", result ? "成功" : "失败");
            Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        }
    }
}
