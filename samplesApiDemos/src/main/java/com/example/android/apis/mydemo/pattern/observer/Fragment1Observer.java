package com.example.android.apis.mydemo.pattern.observer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.apis.R;

public class Fragment1Observer extends Fragment implements Observer{

    private TextView tv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_observer, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv = view.findViewById(R.id.tv_message);
    }

    @Override
    public void notify(String msg) {
        tv.setText(msg);
    }
}
