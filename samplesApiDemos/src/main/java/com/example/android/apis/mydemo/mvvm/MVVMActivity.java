package com.example.android.apis.mydemo.mvvm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.apis.R;
import com.example.android.apis.mydemo.mvvm.adapters.PhoneAdapter;
import com.example.android.apis.mydemo.mvvm.models.Phone;
import com.example.android.apis.mydemo.mvvm.models.PhoneList;
import com.example.android.apis.mydemo.mvvm.viewmodels.MVVMActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MVVMActivity extends AppCompatActivity {

    private static final int CODE_ERROR = 0;
    private static final int CODE_SUCCESS = 1;
    private static final int CODE_LOADING = 2;

    private static final String TAG = "MVVMActivity";
    private RecyclerView mRecycleView;
    private ProgressBar mProgressBar;
    private PhoneAdapter mAdapter;

    private List<Phone> mData = new ArrayList<>();
    private MVVMActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm);
        initView();
        initData();
    }


    private void initView() {
        mRecycleView = (RecyclerView) findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration decoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        decoration.setDrawable(getResources().getDrawable(R.drawable.common_line));
        mRecycleView.addItemDecoration(decoration);
        mRecycleView.setLayoutManager(layoutManager);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void initData() {


        mAdapter = new PhoneAdapter(this, mData);
        mRecycleView.setAdapter(mAdapter);

        mViewModel = ViewModelProviders.of(this).get(MVVMActivityViewModel.class);
        mViewModel.init();

        showProgressBar();
        mViewModel.getPhoneList().observe(this, new Observer<PhoneList>() {
            @Override
            public void onChanged(@Nullable PhoneList phoneList) {
                hideProgressBar();
                mData.clear();
                mData.addAll(phoneList.getData());
                mAdapter.notifyDataSetChanged();
            }
        });

        mViewModel.getException().observe(this, new Observer<Exception>() {
            @Override
            public void onChanged(@Nullable Exception e) {
                hideProgressBar();
                Toast.makeText(MVVMActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
