package com.example.android.apis.mydemo.app.statusbar;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.apis.R;
import com.example.android.apis.mydemo.util.StatusBarUtils;

public class StatusBarTranslucentActivity3 extends Activity {

    private ImageView mIvArrowLeft;
    private ImageView mIvSetting;
    private RelativeLayout mRlTitleContainer;
    private RecyclerView mRecycleView;
    private LinearLayout mLlContainer;
    private TextView mTvTitle;
    private static final int STATUS_BAR_COLOR = 0xFFCFF1FF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_status_bar_translucent3);
        initView();
        initData();
        setStatusBar();
    }

    private void initView() {
        mIvArrowLeft = (ImageView) findViewById(R.id.iv_arrow_left);
        mIvSetting = (ImageView) findViewById(R.id.iv_setting);
        mRlTitleContainer = (RelativeLayout) findViewById(R.id.rl_title_container);
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);
        mLlContainer = (LinearLayout) findViewById(R.id.ll_container);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);

        //设置分割线
        DividerItemDecoration decor =
                new DividerItemDecoration(this, mLayoutManager.getOrientation());
        decor.setDrawable(getResources().getDrawable(R.drawable.common_line));

        mRecycleView.addItemDecoration(decor);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
    }

    private void initData() {
        mRecycleView.setAdapter(new MyAdapter());
    }

    /**
     * 设置状态栏
     */
    private void setStatusBar() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            StatusBarUtils.getInstance().setStatusBarAboveLOLLIPOP(this, mTvTitle, STATUS_BAR_COLOR);
        }
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
            if (viewType == 0) {
                iv = itemView.findViewById(R.id.iv_rebound);
            } else {
                tv = itemView.findViewById(R.id.tv_rebound);
            }
        }
    }

}
