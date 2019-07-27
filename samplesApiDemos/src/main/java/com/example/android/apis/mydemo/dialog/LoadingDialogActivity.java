package com.example.android.apis.mydemo.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.apis.R;

public class LoadingDialogActivity extends Activity implements View.OnClickListener {

    private Button mBtnShow;
    private Button mBtnHide;
    private LoadingDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_dialog);
        initView();
    }


    private void initView() {
        mBtnShow = (Button) findViewById(R.id.btn_show);
        mBtnHide = (Button) findViewById(R.id.btn_hide);

        mBtnShow.setOnClickListener(this);
        mBtnHide.setOnClickListener(this);

        mDialog = new LoadingDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show:
                mDialog.show();
                break;
            case R.id.btn_hide:
                mDialog.dismiss();
                break;
        }
    }
}
