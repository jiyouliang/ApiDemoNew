package com.example.android.apis.mydemo.http;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.android.apis.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpPostActivity extends Activity implements View.OnClickListener {

    private Button mBtn;
    private TextView mTv;
    private ProgressDialog mDialog;
    private Handler mHandler;
    private static final int CODE_ERROR = -1;
    private static final int CODE_SUCCESS = 0;
    private static final int CODE_SHOW = 2;
    private static final int CODE_HIDE = 3;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_post);
        initView();
    }

    private void initView() {
        mBtn = (Button) findViewById(R.id.btn);
        mTv = (TextView) findViewById(R.id.tv);

        mBtn.setOnClickListener(this);
        mDialog = new ProgressDialog(this);
        mEditText = (EditText)findViewById(R.id.edit);
        mHandler = new MyHandler(this, mDialog, mTv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                final String mobile = mEditText.getText().toString().trim();
                if(TextUtils.isEmpty(mobile)){
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                Message msg = mHandler.obtainMessage();
                msg.what = CODE_SHOW;
                mHandler.sendMessage(msg);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        JSONObject json = new JSONObject();
                        json.put("mobile", mobile);
                        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                .connectTimeout(10, TimeUnit.SECONDS)
                                .writeTimeout(10, TimeUnit.SECONDS)
                                .readTimeout(20, TimeUnit.SECONDS).build();

                        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8")
                                , json.toJSONString());
                        Request request = new Request.Builder()
                                .url("http://47.106.182.74:8000/fmap/sms/sendSms")//请求的url,10.0.3.2为genymotion访问本地服务器ip
                                .post(requestBody)
                                .build();

                        //创建/Call
                        Call call = okHttpClient.newCall(request);
                        //加入队列 异步操作
                        call.enqueue(new Callback() {
                            //请求错误回调方法
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Message msg = mHandler.obtainMessage();
                                msg.what = CODE_ERROR;
                                msg.obj = e.getMessage();
                                mHandler.sendMessage(msg);
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                ResponseBody body = response.body();
                                if(body != null){
                                    Message msg = mHandler.obtainMessage();
                                    msg.what = CODE_SUCCESS;
                                    msg.obj = body.string();
                                    mHandler.sendMessage(msg);
                                }
                            }
                        });

                    }
                }).start();
                break;
        }
    }

    private static class MyHandler extends Handler {
        private final Context context;
        private final TextView mTextView;
        private ProgressDialog mDialog;

        public MyHandler(Context context, ProgressDialog dialog, TextView tv) {
            this.mDialog = dialog;
            this.context = context;
            this.mTextView = tv;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mDialog.dismiss();
            switch (msg.what) {
                case CODE_SUCCESS:
                    Toast.makeText(context, "请求成功", Toast.LENGTH_SHORT).show();
                    String json = (String) msg.obj;
                    mTextView.setText("服务器返回:"+json);
                    break;
                case CODE_ERROR:
                    Toast.makeText(context, "请求失败:" + msg.obj, Toast.LENGTH_SHORT).show();
                    mTextView.setText("请求失败:"+msg.obj);
                    break;
                case CODE_SHOW:
                    mDialog.show();
                    break;
            }
        }
    }
}
