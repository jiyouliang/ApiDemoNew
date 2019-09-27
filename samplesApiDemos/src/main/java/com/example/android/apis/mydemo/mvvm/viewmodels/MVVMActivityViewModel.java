package com.example.android.apis.mydemo.mvvm.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.android.apis.mydemo.mvvm.models.Phone;
import com.example.android.apis.mydemo.mvvm.models.PhoneList;
import com.example.android.apis.mydemo.mvvm.repositories.PhoneRepsitory;

public class MVVMActivityViewModel extends ViewModel {

    private static final String TAG = "MVVMActivityViewModel";

    private MutableLiveData<PhoneList> mPhoneList;
    private MutableLiveData<Exception> mNetError;

    public void init(){
        mPhoneList = new MutableLiveData<>();

    }


    public LiveData<PhoneList> getPhoneList(){
        PhoneRepsitory.getInstance().getPhoneData(new PhoneRepsitory.OnResponseListener() {
            @Override
            public void onSuccess(PhoneList phoneList) {
                Log.d(TAG, phoneList.toString());
                mPhoneList.postValue(phoneList);
            }

            @Override
            public void onFailed(Exception e) {
                Log.d(TAG, e.getMessage());
                mNetError.postValue(e);
            }
        });
        return mPhoneList;
    }

    public LiveData<Exception> getException(){
        return mNetError;
    }


}
