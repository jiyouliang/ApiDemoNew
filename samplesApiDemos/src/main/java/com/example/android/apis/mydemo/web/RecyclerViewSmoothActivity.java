package com.example.android.apis.mydemo.web;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.apis.R;

import java.util.ArrayList;
import java.util.List;


/**
 * RecyclerView平滑移动
 */
public class RecyclerViewSmoothActivity extends Activity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private Button mBtnNextStep;
    private MyAdapter mAdapter;
    private List<SmoothModel> mData = new ArrayList<>();

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
    }

    private void iniData() {
        MySmoothLinearLayoutManager layoutManager = new MySmoothLinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mData.clear();
        for (int i = 0; i < 10; i++) {
            mData.add(new SmoothModel("在学完整个Python基础语法课程后，你将会真正迈入Python的大门，掌握利用Python解决问题的方法和思维"));
        }
        mAdapter = new MyAdapter(mData);
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
        SmoothModel data = new SmoothModel("这是内容内容这是内容内容" + System.currentTimeMillis());
        mAdapter.notifyItem(data);
        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
    }

    static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private final List<SmoothModel> mData;

        MyAdapter(List<SmoothModel> data) {
            this.mData = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_smooth, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            SmoothModel data = mData.get(position);
            holder.mTvContent.setText(data.getContent());
        }

        void notifyItem(SmoothModel model) {
            this.mData.add(model);
            notifyItemInserted(mData.size());

        }

        @Override
        public int getItemCount() {
            return mData != null ? mData.size() : 0;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView mTvContent;

            ViewHolder(View itemView) {
                super(itemView);
                mTvContent = itemView.findViewById(R.id.tv_content);
            }
        }
    }
}
