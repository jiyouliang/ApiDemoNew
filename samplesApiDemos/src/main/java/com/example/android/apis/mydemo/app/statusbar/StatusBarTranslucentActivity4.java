package com.example.android.apis.mydemo.app.statusbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.apis.R;

public class StatusBarTranslucentActivity4 extends Activity implements ObservableScrollView.OnObservableScrollListener {

    private static final String TAG = "StatusBarActivity4";

    private ImageView mIvArrowLeft;
    private ImageView mIvSetting;
    private RelativeLayout mRlTitleContainer;
    private ObservableScrollView mObservableScrollView;
    private LinearLayout mLlContainer;
    private TextView mTvTitle;
    private static final int STATUS_BAR_COLOR = 0xFFCFF1FF;
    private int totalDy;
    private View mStatusBarView;
    private int mHeight;
    private RelativeLayout statusBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_status_bar_translucent4);
        initView();
        setStatusBar();
    }

    private void initView() {
        mIvArrowLeft = (ImageView) findViewById(R.id.iv_arrow_left);
        mIvSetting = (ImageView) findViewById(R.id.iv_setting);
        mRlTitleContainer = (RelativeLayout) findViewById(R.id.rl_title_container);
        mObservableScrollView = (ObservableScrollView) findViewById(R.id.osv);
        mLlContainer = (LinearLayout) findViewById(R.id.ll_container);
        mTvTitle = (TextView) findViewById(R.id.tv_title);

        mRlTitleContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeight = mRlTitleContainer.getMeasuredHeight();
            }
        });

        mObservableScrollView.setOnObservableScrollListener(this);
    }

    /**
     * 设置状态栏
     */
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

           /* //获取windowphone下的decorView
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            int count = decorView.getChildCount();
            //判断是否已经添加了statusBarView
            if (count > 0 && decorView.getChildAt(count - 1) instanceof TextView) {
//                decorView.getChildAt(count - 1).setBackgroundColor(statusBarColor);
            } else {
                //新建一个和状态栏高宽的view
//                statusView = createStatusBarView(activity, titleView, statusBarColor);
                decorView.addView(statusView);
            }
            ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            //rootview不会为状态栏留出状态栏空间
            ViewCompat.setFitsSystemWindows(rootView, true);
            rootView.setClipToPadding(true);*/
        }


        //获取windowphone下的decorView
        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        int count = decorView.getChildCount();
        //判断是否已经添加了statusBarView
        if (count > 0 && decorView.getChildAt(count - 1) instanceof RelativeLayout) {
            decorView.getChildAt(count - 1).setBackgroundColor(Color.argb(0, 255, 255, 255));
        } else {
            //新建一个和状态栏高宽的view
            statusBarView = new RelativeLayout(this);
            RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight);
            statusBarView.setLayoutParams(params);
            statusBarView.setBackgroundColor(Color.argb(0, 255, 255, 255));
//            statusView = createStatusBarView(activity, titleView, statusBarColor);
            decorView.addView(statusBarView);
        }
        ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        //rootview不会为状态栏留出状态栏空间
        ViewCompat.setFitsSystemWindows(rootView, true);
        rootView.setClipToPadding(true);
    }


    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (t <= 0) {
            //顶部图处于最顶部，标题栏透明
            mRlTitleContainer.setBackgroundColor(Color.argb(0, 255, 255, 255));
//            statusBarView.setBackgroundColor(Color.argb(0, 255, 255, 255));
            getWindow().setStatusBarColor(Color.argb((int) 0, 255, 255, 255));
            mIvArrowLeft.setBackgroundResource(R.drawable.arrow_left_light);
            mIvSetting.setBackgroundResource(R.drawable.setting_light);
            mTvTitle.setTextColor(Color.WHITE);
        } else if (t > 0 && t < mHeight) {
            //滑动过程中，渐变
            float scale = (float) t / mHeight;//算出滑动距离比例
            float alpha = (255 * scale);//得到透明度
            mRlTitleContainer.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
//            statusBarView.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            getWindow().setStatusBarColor(Color.argb((int) alpha, 255, 255, 255));
            mIvArrowLeft.setBackgroundResource(R.drawable.arrow_left_light);
            mIvSetting.setBackgroundResource(R.drawable.setting_light);
            mTvTitle.setTextColor(Color.WHITE);
        } else {
            //过顶部图区域，标题栏定色
            mRlTitleContainer.setBackgroundColor(Color.argb(255, 255, 255, 255));
            getWindow().setStatusBarColor(Color.argb(255, 255, 255, 255));
//            statusBarView.setBackgroundColor(Color.argb(255, 255, 255, 255));
            mIvArrowLeft.setBackgroundResource(R.drawable.arrow_left_black);
            mIvSetting.setBackgroundResource(R.drawable.setting_black);
            mTvTitle.setTextColor(Color.BLACK);
        }
    }
}
