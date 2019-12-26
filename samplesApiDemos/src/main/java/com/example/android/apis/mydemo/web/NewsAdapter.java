package com.example.android.apis.mydemo.web;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.apis.R;

/**
 * RecyclerView 新闻item adapter
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    /**
     * 头部WebView类型
     */
    private static final int TYPE_WEB_VIEW = 0;

    /**
     * 评论类型
     */
    private static final int TYPE_COMMENT = 1;
    private final MyWebView.OnPageChangeListener mListener;

    public NewsAdapter(MyWebView.OnPageChangeListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int type = getItemViewType(viewType);
        View itemView = null;
        switch (type){
            case TYPE_WEB_VIEW:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_webview, parent, false);

                break;
            case TYPE_COMMENT:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_comment, parent, false);
                break;
        }
        ViewHolder viewHolder = new ViewHolder(itemView, type, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if(viewType == TYPE_WEB_VIEW){
            holder.mWebView.loadUrl("http://jiyouliang.com/demo/toutiao/index.html");
        }else{

        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_WEB_VIEW;
        }else{
            return TYPE_COMMENT;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        MyWebView mWebView;

        public ViewHolder(View itemView, int viewType, MyWebView.OnPageChangeListener listener) {
            super(itemView);
            if(viewType == TYPE_WEB_VIEW){
                mWebView = itemView.findViewById(R.id.item_webView);
                mWebView.getSettings().setAppCacheEnabled(true);
                mWebView.setOnPageChangeListener(listener);

            }else if(viewType == TYPE_COMMENT){

            }

        }

    }
}
