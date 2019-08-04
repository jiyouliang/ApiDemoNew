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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.apis.R;
import com.example.android.apis.mydemo.views.RecyclerViewReboundActivity;

/**
 * NestedScrolling+RecycleView实现阻尼效果
 */
public class NestedScrollWithRecycleReboundActivity extends Activity {

    private RecyclerView mRecycleView;
    private DampLayout mDlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll_with_recycle_rebound);
        initView();
        initData();
    }

    private void initView() {
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);
        mDlView = (DampLayout) findViewById(R.id.dl_view);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);

        //设置分割线
        DividerItemDecoration decor =
                new DividerItemDecoration(this, mLayoutManager.getOrientation());
        decor.setDrawable(getResources().getDrawable(R.drawable.common_line));
    }


    private void initData() {
        mRecycleView.setAdapter(new MyAdapter());
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == 0) {
                View iv = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(R.layout.rebound_recycle_item_image, parent, false);
                return new MyViewHolder(iv, viewType);
            } else {
                View tv = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(R.layout.rebound_recycle_item_tv, parent, false);
                return new MyViewHolder(tv, viewType);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            if (position > 0) {
                holder.tv.setText(String.format("内容 %s", position));
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 0;
            } else {
                return 1;
            }
        }

        @Override
        public int getItemCount() {
            return 50;
        }
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tv;

        public MyViewHolder(View itemView, int viewType) {
            super(itemView);
            if(viewType == 0){
                iv = itemView.findViewById(R.id.iv_rebound);
            }else{
                tv = itemView.findViewById(R.id.tv_rebound);
            }
        }
    }


}
