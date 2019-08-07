package com.example.android.apis.mydemo.app.statusbar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.apis.R;

import java.util.ArrayList;
import java.util.List;

public class StatusBarTranslucentActivity extends Activity {

    private static final String TAG = "TranslucentActivity";

    private ImageView mIvArrowLeft;
    private ImageView mIvSetting;
    private RelativeLayout mRlTitleContainer;
    private ListView mRecycleView;
    private LinearLayout mLlContainer;
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_status_bar_translucent);
        initView();
        initData();
        setStatusBar();
    }

    /**
     * 设置状态栏
     */
    private void setStatusBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            //获取windowphone下的decorView
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            int count = decorView.getChildCount();
            //判断是否已经添加了statusBarView
            if (count > 0 && decorView.getChildAt(count - 1) instanceof TextView) {
                decorView.getChildAt(count - 1).setBackgroundColor(Color.WHITE);
            } else {
                //新建一个和状态栏高宽的view
                View statusView = createStatusBarView(Color.WHITE, 1);
                decorView.addView(statusView);
            }
            ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            //rootview不会为状态栏留出状态栏空间
            ViewCompat.setFitsSystemWindows(rootView, true);
            rootView.setClipToPadding(true);
        } else {
            Toast.makeText(this, "请在4.4系统上查看效果", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        mIvArrowLeft = (ImageView) findViewById(R.id.iv_arrow_left);
        mIvSetting = (ImageView) findViewById(R.id.iv_setting);
        mRlTitleContainer = (RelativeLayout) findViewById(R.id.rl_title_container);
        mRecycleView = (ListView) findViewById(R.id.recycle_view);
        mLlContainer = (LinearLayout) findViewById(R.id.ll_container);

        mTvTitle = (TextView) findViewById(R.id.tv_title);

       /* mRecycleView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View child = mRecycleView.getChildAt(firstVisibleItem);
                if(child == null){
                    return;
                }
                String msg = String.format("onScroll, scrollY=%s",
                        child.getMeasuredHeight() * (firstVisibleItem + 1));
                Log.d(TAG, msg);
            }
        });*/
    }

    private void initData() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add("内容 > " + i);
        }
        mRecycleView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas));
    }


    private View createStatusBarView(int color, int alpha) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(this);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mRlTitleContainer.getMeasuredHeight());
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(0xFFCFF1FF);
        return statusBarView;
    }
}
