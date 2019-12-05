package com.example.android.apis.mydemo.http;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.apis.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * okhttp获取文件长度，而不下载文件
 */
public class OkHttpGetContentLength extends Activity implements View.OnClickListener {

    private Button mBtnGetLength;
    private TextView mTvLength;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_get_content_length);
        initView();
    }

    private void initView() {
        mBtnGetLength = (Button) findViewById(R.id.btnGetLength);
        mTvLength = (TextView) findViewById(R.id.tv_length);

        mBtnGetLength.setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGetLength:
                getContentLength();
                break;
        }
    }

    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }

    private void getContentLength() {
        showProgressBar();
        new Thread() {
            @Override
            public void run() {
                super.run();
                // content-length:15966109
                String url = "http://1300399567.vod2.myqcloud.com/8b45eaadvodcq1300399567/edf26a995285890796452554457/xS1aQqrPZXMA.mp4";
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                long size = 0;
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    ResponseBody body = response.body();
                    if (body != null) {
                        size = body.contentLength();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final long finalSize = size;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressBar();
                        mTvLength.setText("content-length:" + finalSize);
                    }
                });
            }
        }.start();
    }
}
