package com.example.android.apis.mydemo.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jiyouliang.log.Logger;


/**
 * 自定义WebView
 */
public class MyWebView extends WebView {
    private static final String TAG = "MyWebView";
    private OnPageChangeListener mListener;
    private MyWebViewClient mWebclient;
    private MyWebChromeClient mWebChromeClient;

    public MyWebView(Context context) {
        this(context, null, 0);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mWebclient = new MyWebViewClient();
        mWebChromeClient = new MyWebChromeClient();

        setWebViewClient(mWebclient);
        setWebChromeClient(mWebChromeClient);
    }

    private static class MyWebViewClient extends WebViewClient {
        private OnPageChangeListener mListener;


        public void setListener(OnPageChangeListener listener){
            this.mListener  = listener;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Logger.d(TAG, "onPageStarted:url=" + url);
            if (mListener != null) {
                mListener.onPageStarted(view, url);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Logger.d(TAG, "onPageFinished:url=" + url);
            if (mListener != null) {
                mListener.onPageFinished(view, url);
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Logger.d(TAG, "onReceivedError:error=" + error);
            if (mListener != null) {
                mListener.onPageError(view, request, error);
            }
        }
    }

    private static class MyWebChromeClient extends WebChromeClient {

        private OnPageChangeListener mListener;

        public void setListener(OnPageChangeListener listener){
            this.mListener  = listener;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Logger.d(TAG, "onProgressChanged:newProgress=" + newProgress);
            if (mListener != null) {
                mListener.onPageProgress(view, newProgress);
            }
        }
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mListener = listener;
        mWebChromeClient.setListener(mListener);
        mWebclient.setListener(mListener);
    }

    public interface OnPageChangeListener {
        /**
         * webview加载开始
         *
         * @param view
         * @param url
         */
        void onPageStarted(WebView view, String url);

        /**
         * webview加载中
         *
         * @param view
         * @param progress
         */
        void onPageProgress(WebView view, int progress);

        /**
         * webview加载完成
         *
         * @param view
         * @param url
         */
        void onPageFinished(WebView view, String url);

        /**
         * webview异常
         *
         * @param view
         * @param request
         * @param error
         */
        void onPageError(WebView view, WebResourceRequest request, WebResourceError error);
    }
}