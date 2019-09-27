package com.example.android.apis.mydemo.pattern.observer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.apis.R;

import java.util.ArrayList;
import java.util.List;

public class ObserverPatternActivity extends FragmentActivity implements View.OnClickListener, Subject {

    private EditText mEtContent;
    private Button mBtnSend;
    private ViewPager mViewPager;
    // 存储所有观察者
    private List<Observer> mObserverList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer_pattern);
        initView();
        initData();
    }


    private void initView() {
        mEtContent = (EditText) findViewById(R.id.tv_content);
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mBtnSend.setOnClickListener(this);
    }
    private void initData() {
        Fragment1Observer frag1 = new Fragment1Observer();
        Fragment2Observer frag2 = new Fragment2Observer();

        List<Fragment> data = new ArrayList<>();
        data.add(frag1);
        data.add(frag2);



        FragmentManager fm = getSupportFragmentManager();
        MyAdapter adapter = new MyAdapter(fm, data);
        mViewPager.setAdapter(adapter);

        // 添加观察者
        mObserverList.clear();
        mObserverList.add(frag1);
        mObserverList.add(frag2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                if(TextUtils.isEmpty(mEtContent.getText().toString())){
                    Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                notifyObservers();
                break;
        }
    }


    @Override
    public void addObserver(Observer o) {
        mObserverList.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        mObserverList.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o : mObserverList){
            o.notify(mEtContent.getText().toString().trim());
        }
    }

    private static class MyAdapter extends FragmentPagerAdapter{
        private List<Fragment> mFragments;

        public MyAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }


}
