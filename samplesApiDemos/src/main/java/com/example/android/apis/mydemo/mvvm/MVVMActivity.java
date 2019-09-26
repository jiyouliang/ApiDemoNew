package com.example.android.apis.mydemo.mvvm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.android.apis.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MVVMActivity extends Activity implements OnNetResponseListener {

    private ProgressDialog mProgressDialog;
    private static final int CODE_ERROR = 0;
    private static final int CODE_SUCCESS = 1;
    private static final int CODE_LOADING = 2;
    private ImageView mIvLogo;
    private TextView mTvTitle;
    private TextView mTvInfo;
    private TextView mTvDesc;
    private TextView mTvPrice;

    private static final String TAG = "MVVMActivity";
    private MyHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm);
        initView();
    }

    private void initView() {
        mHandler = new MyHandler(this);
        mProgressDialog = new ProgressDialog(this);
        mIvLogo = (ImageView) findViewById(R.id.iv_logo);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvInfo = (TextView) findViewById(R.id.tv_info);
        mTvDesc = (TextView) findViewById(R.id.tv_desc);
        mTvPrice = (TextView) findViewById(R.id.tv_price);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestNetData();
            }
        });
    }

    public void requestNetData() {
        showProgressBar();
        showProgressBar();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS).build();

        Request request = new Request.Builder()
                .url("http://47.106.182.74:8000/fmap/test/testProduct")//请求的url,10.0.3.2为genymotion访问本地服务器ip
                .get()
                .build();

        //创建/Call
        Call call = okHttpClient.newCall(request);
        //加入队列 异步操作
        call.enqueue(new Callback() {
            //请求错误回调方法
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = mHandler.obtainMessage();
                msg.obj = e;
                msg.what = CODE_ERROR;
                mHandler.sendMessage(msg);
            }


            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    PhoneData phoneData = JSON.parseObject(body.string(), PhoneData.class);
                    Message msg = mHandler.obtainMessage();
                    msg.obj = phoneData;
                    msg.what = CODE_SUCCESS;
                    mHandler.sendMessage(msg);
                }
            }
        });

    }


    private void showProgressBar() {
        mProgressDialog.show();
    }

    private void hideProgressBar() {
        mProgressDialog.dismiss();
    }

    @Override
    public void onFailed(Exception e) {
        hideProgressBar();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(PhoneData data) {
        hideProgressBar();
        if(data.getCode() != 0){
            Toast.makeText(this, ""+data.getMsg(), Toast.LENGTH_SHORT).show();
            return;
        }
        if (data.getData() == null) {
            return;
        }
        PhoneData.DataBean phone = data.getData();
        mTvTitle.setText(TextUtils.isEmpty(phone.getTitle()) ? "" : phone.getTitle());
        if (phone.getInfo_list() != null && phone.getInfo_list().size() > 0) {
            List<String> info_list = phone.getInfo_list();
            StringBuffer sb = new StringBuffer();
            for (String value : info_list) {
                sb.append(value).append(",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            mTvInfo.setText(sb.toString());
        }

        if(!TextUtils.isEmpty(phone.getPrice())){
            mTvPrice.setText(String.format("¥ %s", phone.getPrice()));
        }

        if(!TextUtils.isEmpty(phone.getDesc())){
            mTvDesc.setText(phone.getDesc());
        }
        // 图片
        if(!TextUtils.isEmpty(phone.getImg_url())){
            Glide.with(this).load(phone.getImg_url()).into(mIvLogo);
        }
    }

    private static class MyHandler extends Handler{

        private OnNetResponseListener mListener;

        public MyHandler(OnNetResponseListener listener) {
            super();
            this.mListener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CODE_LOADING:
                    break;
                case CODE_SUCCESS:
                    mListener.onSuccess((PhoneData) msg.obj);
                    break;
                case CODE_ERROR:
                    mListener.onFailed((Exception) msg.obj);
                    break;
            }
        }
    }


}
