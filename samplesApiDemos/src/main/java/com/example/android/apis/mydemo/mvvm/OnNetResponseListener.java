package com.example.android.apis.mydemo.mvvm;

public interface OnNetResponseListener {
    void onFailed(Exception e);

    void onSuccess(PhoneData data);
}