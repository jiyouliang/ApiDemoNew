package com.example.android.apis.mydemo.nestedscrolling;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.apis.R;

import java.util.ArrayList;
import java.util.List;

public class NestedScrollingActivity1 extends Activity {

    private TextView mTextView;
    private RecyclerView mRecycleView;
    private List<String> mDatas = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scrolling);

        initViews();
        initDatas();
    }


    private void initViews() {
        mTextView = (TextView) findViewById(R.id.tv_title);
        mTextView.setVerticalFadingEdgeEnabled(false);
        mTextView.setHorizontalFadingEdgeEnabled(false);

        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecycleView.setVerticalFadingEdgeEnabled(false);
        mRecycleView.setHorizontalFadingEdgeEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);

        //分割线
        DividerItemDecoration decor =
                new DividerItemDecoration(this, layoutManager.getOrientation());
        decor.setDrawable(getResources().getDrawable(R.drawable.common_line));
        mRecycleView.addItemDecoration(decor);


    }

    private void initDatas() {
        for (int i = 0; i < 50; i++) {
            mDatas.add("内容" + " -> " + i);
        }
        mRecycleView.setAdapter(new CommonAdapter(mDatas));
    }

    private static class CommonAdapter extends RecyclerView.Adapter<CommonViewHolder> {

        private List<String> mDatas;

        public CommonAdapter(List<String> data) {
            this.mDatas = data;
        }

        @NonNull
        @Override
        public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            CommonViewHolder holder = null;
            if (context != null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if(inflater != null){
                    View view = inflater.inflate(R.layout.ns_item_text, parent, false);
                    holder = new CommonViewHolder(view);
                }

            }
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {
            holder.tvInfo.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }


    }

    private static class CommonViewHolder extends RecyclerView.ViewHolder {
        TextView tvInfo;
        public CommonViewHolder(View itemView) {
            super(itemView);
            tvInfo = itemView.findViewById(R.id.id_info);
        }
    }


}
