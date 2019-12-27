package com.example.android.apis.mydemo.web;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.android.apis.R;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**
 * RecyclerView平滑移动
 */
public class RecyclerViewSmoothActivity extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private RecyclerView mRecyclerView;
    private Button mBtnNextStep;
    private SmoothAdapter mAdapter;
    private List<SmoothModel> mData = new ArrayList<>();
    private RadioGroup mRadioGroup;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_GIF = 1;
    private static final int TYPE_OTHER = 2;
    private int mType = TYPE_NORMAL;
    private MySmoothLinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_smooth);
        initView();
        iniData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_View);
        mBtnNextStep = (Button) findViewById(R.id.btnNextStep);
        mBtnNextStep.setOnClickListener(this);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        mRadioGroup.setOnCheckedChangeListener(this);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void iniData() {
        mLayoutManager = new MySmoothLinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mData.clear();
        for (int i = 0; i < 10; i++) {
            SmoothModel data = new SmoothModel("在学完整个Python基础语法课程后，你将会真正迈入Python的大门，掌握利用Python解决问题的方法和思维");
            data.setType(TYPE_NORMAL);
            mData.add(data);
        }
        mAdapter = new SmoothAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextStep:
                nextStep();
                break;
        }
    }

    private void nextStep() {
        SmoothModel data = new SmoothModel();
        data.setType(mType);
        switch (mType){
            case TYPE_NORMAL:
                data.setContent("这是内容内容这是内容内容" + System.currentTimeMillis());
                break;
            case TYPE_GIF:
                data.setUrl("https://audio-temp.oss-cn-shenzhen.aliyuncs.com/tenor.gif");
                break;
            case TYPE_OTHER:
                break;
        }
        mLayoutManager.setSmoothType(MySmoothLinearLayoutManager.TYPE_SMOOTH);
        mAdapter.notifyItem(data);
        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
    }

    // RadioGroup item选中回调
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_normal:
                this.mType = TYPE_NORMAL;
                break;
            case R.id.rb_img:
                this.mType = TYPE_GIF;
                break;
            case R.id.rb_other:
                this.mType = TYPE_OTHER;
                break;
        }
    }

}
