package com.example.android.apis.mydemo.web;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.example.android.apis.R;
import com.example.android.apis.mydemo.loading.LoadingFlashView;

/**
 * WebView+native，webview显示H5网页，底部android实现评论效果
 */
public class WebViewCommentActivity extends Activity implements MyWebView.OnPageChangeListener {

    private RecyclerView mRecycleView;
    private LoadingFlashView mLoadingView;
    private final long mCurrentTime = System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_comment);
        initView();
    }

    private void initView() {
        mRecycleView = (RecyclerView) findViewById(R.id.recycleView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecycleView.setLayoutManager(layoutManager);

        mRecycleView.setAdapter(new NewsAdapter(this));
        mLoadingView = (LoadingFlashView) findViewById(R.id.loadingView);
    }

    //*****************************************自定义webview回调监听*****************************************
    @Override
    public void onPageStarted(WebView view, String url) {
        mLoadingView.showLoading();
    }

    @Override
    public void onPageProgress(WebView view, int progress) {

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        // 模拟延时
        long endTime = System.currentTimeMillis();
        float delay = 1.5F * 1000;
        if(endTime - mCurrentTime > 2 * 1000){
            delay = 0;
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingView.hideLoading();
                mLoadingView.setVisibility(View.GONE);
                mRecycleView.setVisibility(View.VISIBLE);
            }
        }, (long) delay);
    }


    @Override
    public void onPageError(WebView view, WebResourceRequest request, WebResourceError error) {

    }

    private static final Handler mHandler = new Handler();
}
