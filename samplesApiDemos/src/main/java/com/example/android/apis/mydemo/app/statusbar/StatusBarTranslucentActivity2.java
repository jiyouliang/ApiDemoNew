package com.example.android.apis.mydemo.app.statusbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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

public class StatusBarTranslucentActivity2 extends Activity {

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
        setContentView(R.layout.activity_status_bar_translucent2);
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
        // 如果在values/styles.xml中设置android:windowTranslucentNavigation和android:windowTranslucentStatus
        // 为true,则需要判定4.4以上在标题栏上插入view占位,否则标题栏和状态栏重合bug
       /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            //4.4以上
            //获取windowphone下的decorView
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            int count = decorView.getChildCount();
            //判断是否已经添加了statusBarView
            if (count > 0 && decorView.getChildAt(count - 1) instanceof TextView) {
                decorView.getChildAt(count - 1).setBackgroundColor(STATUS_BAR_COLOR);
            } else {
                //新建一个和状态栏高宽的view
                View statusView = createStatusBarView();
                decorView.addView(statusView);
            }
            ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            //rootview不会为状态栏留出状态栏空间
            ViewCompat.setFitsSystemWindows(rootView, true);
            rootView.setClipToPadding(true);

        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0以上
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //注意要清除 FLAG_TRANSLUCENT_STATUS flag
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(STATUS_BAR_COLOR);
            /*
             * 通过上述几行代码实现沉浸式在状态栏,当然也可以通过/values-21/styles.xml实现
             *
               <style name="MDTheme" parent="Theme.Design.Light.NoActionBar">
                    <item name="android:windowTranslucentStatus">false</item>
                    <item name="android:windowDrawsSystemBarBackgrounds">true</item>
                    <item name="android:statusBarColor">@android:color/holo_red_light</item>
               </style>
             *
             */

        }

        /*
         *
         *6.0以上:系统状态栏的字色和图标颜色为白色，当我的主题色或者图片接近白色或者为浅色的时候，
         * 状态栏上的内容就看不清了,通过如下设置解决该问题,不过设置后后可能出现标题栏和状态栏重合,此时需要参考上面4.4添加view
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            //获取windowphone下的decorView
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            int count = decorView.getChildCount();
            //判断是否已经添加了statusBarView
            if (count > 0 && decorView.getChildAt(count - 1) instanceof TextView) {
                decorView.getChildAt(count - 1).setBackgroundColor(STATUS_BAR_COLOR);
            } else {
                //新建一个和状态栏高宽的view
                View statusView = createStatusBarView();
                decorView.addView(statusView);
            }
            ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            //rootview不会为状态栏留出状态栏空间
            ViewCompat.setFitsSystemWindows(rootView, true);
            rootView.setClipToPadding(true);
        }

    }


    private View createStatusBarView() {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(this);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mRlTitleContainer.getMeasuredHeight());
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(STATUS_BAR_COLOR);
        return statusBarView;
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
