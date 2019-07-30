package com.example.android.apis.mydemo.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.android.apis.R;

public class ReboundViewActivity2 extends Activity {

    private LinearLayout mFlContainer;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebound_view2);
        initView();
        initData();
    }

    private void initData() {
        String[] values = new String[100];
        for (int i = 0; i < 100; i++) {
            values[i] = String.valueOf(i);
        }
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values));
    }

    private void initView() {
        mFlContainer = (LinearLayout) findViewById(R.id.fl_container);
        ReboundVewHelper mVewHelper = new ReboundVewHelper();
        mVewHelper.setReboundView(mFlContainer);
        mListView = (ListView) findViewById(R.id.lv);
    }
}
