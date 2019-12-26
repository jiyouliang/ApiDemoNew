package com.example.android.apis.mydemo.web;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.apis.R;
import com.example.android.apis.mydemo.loading.LoadingFlashView;

import java.util.ArrayList;
import java.util.List;

/**
 * WebView+native，webview显示H5网页，底部android实现评论效果
 */
public class WebViewCommentActivity extends Activity implements MyWebView.OnPageChangeListener {

    private RecyclerView mRecycleView;
    private LoadingFlashView mLoadingView;
    private final long mCurrentTime = System.currentTimeMillis();
    private EditText mEtComment;
    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_comment);
        initView();
        initData();
    }

    private void initView() {
        mRecycleView = (RecyclerView) findViewById(R.id.recycleView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecycleView.setLayoutManager(layoutManager);

        mLoadingView = (LoadingFlashView) findViewById(R.id.loadingView);
        mEtComment = (EditText) findViewById(R.id.et_comment);

        mEtComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    sendComment();
                    return true;
                }
                return false;
            }
        });
    }

    private void initData() {
        List<CommentUserModel> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CommentUserModel model = new CommentUserModel("用户名", "这是评论内容这是评论内容这是评论内容这是评论内容这是评论内容这是评论内容这是评论内容");
            data.add(model);
        }
        mAdapter = new NewsAdapter(data, this);
        mRecycleView.setAdapter(mAdapter);
    }

    /**
     * 发送评论
     */
    private void sendComment() {
        String comment = mEtComment.getText().toString();
        if(!TextUtils.isEmpty(comment)){
            CommentUserModel model = new CommentUserModel("新用户", comment);
            mAdapter.notifyItem(model);
            mEtComment.setText("");
            mRecycleView.smoothScrollToPosition(mAdapter.getItemCount());
        }
    }

    //*****************************************自定义webview回调监听*****************************************
    @Override
    public void onPageStarted(WebView view, String url) {
        mEtComment.setVisibility(View.GONE);
        mLoadingView.showLoading();
    }

    @Override
    public void onPageProgress(WebView view, int progress) {

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        // 模拟延时
        long endTime = System.currentTimeMillis();
        float delay = 1.0F * 1000;
        if (endTime - mCurrentTime > 2 * 1000) {
            delay = 0;
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingView.hideLoading();
                mLoadingView.setVisibility(View.GONE);
                mEtComment.setVisibility(View.VISIBLE);
                mRecycleView.setVisibility(View.VISIBLE);

            }
        }, (long) delay);
    }


    @Override
    public void onPageError(WebView view, WebResourceRequest request, WebResourceError error) {

    }

    private static final Handler mHandler = new Handler();

}
