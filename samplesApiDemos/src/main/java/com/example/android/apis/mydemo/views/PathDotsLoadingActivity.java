package com.example.android.apis.mydemo.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.android.apis.R;

/**
 * @author YouLiang.Ji
 * 仿高德地图 Button加载效果
 */
public class PathDotsLoadingActivity extends Activity implements View.OnClickListener {

    private ButtonPathLoadingView mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_dots_loading);
        initView();
    }

    private void initView() {
        mLoadingView = (ButtonPathLoadingView) findViewById(R.id.loading_view);
//        mLoadingView.setEnabled(false);

        mLoadingView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loading_view:
                if(mLoadingView.getLoadingState()){
                    mLoadingView.stopLoading();
                }else{

                    mLoadingView.startLoading();
                }
                break;
        }
    }
}
