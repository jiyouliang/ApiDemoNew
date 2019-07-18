package com.example.android.apis.mydemo.http;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.apis.R;
import com.example.android.apis.mydemo.util.HttpTaskClient;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpAsyncActivity extends Activity implements View.OnClickListener {

    private Button mBtn1;
    private Button mBtn2;
    private TextView mTvTitle;
    private TextView mTvContent;
    private static final int SUCCESS = 0;
    private static final int ERROR = 1;
    private MyHandler mHandler;
    private ProgressDialog mDialog;
    private static final String TAG = "OkHttpAsyncActivity";
    private static final String url = "http://47.106.182.74:8000/fmap/test/testInterface";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_async);
        initView();
    }

    private void initView() {
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvContent = (TextView) findViewById(R.id.tv_content);

        mDialog = new ProgressDialog(this);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setTitle("加载中...");

        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);

        mHandler = new MyHandler(mTvContent, mDialog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                okEnqueue();
                break;
            case R.id.btn2:
                okHttpOnThreadPool();
                break;
        }
    }

    private void okHttpOnThreadPool() {
        HttpTaskClient.getInstance().get(url, new HttpTaskClient.OnHttpResponseListener() {
            @Override
            public void onFailed(Exception e) {
                mTvContent.setText("异常：" + e.toString());
            }

            @Override
            public void onSuccess(String response) {
                mTvContent.setText("成功：" + response);
            }
        });
    }

    private void okEnqueue() {
        mDialog.show();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS).build();
        Request request = new Request.Builder()
                .url(url)//请求的url,10.0.3.2为genymotion访问本地服务器ip
                .get()
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Message msg = mHandler.obtainMessage();
                msg.what = ERROR;
                msg.obj = e.getMessage();
                mHandler.sendMessage(msg);

                Log.e(TAG, "onFailure: thread name = " + Thread.currentThread().getName());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //下面log没有显示具体Thread名称，直显示OkHttp http://id:端口/...
                Log.e(TAG, "onResponse: thread name = " + Thread.currentThread().getName());
                ResponseBody body = response.body();
                if (body != null) {
                    Message msg = mHandler.obtainMessage();
                    msg.what = ERROR;
                    msg.obj = body.string();
                    mHandler.sendMessage(msg);
                }
            }
        });
    }

    private static class MyHandler extends Handler {

        private final ProgressDialog mDialog;
        private TextView mTvContent;

        public MyHandler(TextView tv, ProgressDialog dialog) {
            this.mDialog = dialog;
            this.mTvContent = tv;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    mDialog.dismiss();
                    String content = (String) msg.obj;
                    mTvContent.setText(content);
                    break;
                case ERROR:
                    mDialog.dismiss();
                    mTvContent.setText(String.valueOf(msg.what));
                    break;
            }
        }
    }
}
