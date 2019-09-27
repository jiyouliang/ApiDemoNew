package com.example.android.apis.mydemo.mvvm.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.example.android.apis.mydemo.mvvm.models.Phone;
import com.example.android.apis.mydemo.mvvm.models.PhoneList;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * model层业务：从服务器获取数据，该层只需要关注获取数据
 */
public class PhoneRepsitory {
    private static final String TAG = "PhoneRepsitory";
    private static PhoneRepsitory instance;
    private MutableLiveData<PhoneList> mData = new MutableLiveData<>();

    private PhoneRepsitory() {
    }

    public static PhoneRepsitory getInstance() {
        if (instance == null) {
            synchronized (PhoneRepsitory.class) {
                instance = new PhoneRepsitory();
            }
        }
        return instance;
    }


    /**
     * 获取网络数据
     * @param listener
     */
    public void getPhoneData(final OnResponseListener listener){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS).build();
        Request request = new Request.Builder()
                .url("http://47.106.182.74/demo/phones.json")
                .get()
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                listener.onFailed(e);
                Log.d(TAG, "onFailure,"+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody body = response.body();

                if(body == null){
                    listener.onFailed(new NullPointerException("Service error,Phone data is null."));
                    return;
                }
                String string = body.string();
                Log.d(TAG, "onResponse,"+ string);
                Log.d(TAG, string);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                PhoneList phone = JSONObject.parseObject(string, PhoneList.class);
                listener.onSuccess(phone);
            }
        });
    }


    /**
     * 网络回调
     */
    public interface OnResponseListener{
        /**
         * 成功回调
         * @param phoneList
         */
        void onSuccess(PhoneList phoneList);

        /**
         * 失败回调
         * @param e
         */
        void onFailed(Exception e);
    }
}
