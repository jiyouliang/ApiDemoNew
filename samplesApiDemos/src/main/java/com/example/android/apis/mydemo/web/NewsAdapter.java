package com.example.android.apis.mydemo.web;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.apis.R;

import java.util.List;

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
    private final List<CommentUserModel> mData;

    public NewsAdapter(List<CommentUserModel> data, MyWebView.OnPageChangeListener listener) {
        this.mListener = listener;
        this.mData = data;
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
            CommentUserModel data = mData.get(position - 1);
            holder.mTvUserName.setText(data.getUserName());
            holder.mTvComment.setText(data.getComment());
        }
    }


    @Override
    public int getItemCount() {
        return mData != null ? mData.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_WEB_VIEW;
        }else{
            return TYPE_COMMENT;
        }
    }

    /**
     * 更新单条数据
     * @param model
     */
    public void notifyItem(CommentUserModel model){
        mData.add(model);
        notifyItemInserted(mData.size());
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        MyWebView mWebView;
        TextView mTvUserName;
        TextView mTvComment;

        public ViewHolder(View itemView, int viewType, MyWebView.OnPageChangeListener listener) {
            super(itemView);
            if(viewType == TYPE_WEB_VIEW){
                mWebView = itemView.findViewById(R.id.item_webView);
                mWebView.getSettings().setAppCacheEnabled(true);
                mWebView.setOnPageChangeListener(listener);

            }else if(viewType == TYPE_COMMENT){
                mTvComment = itemView.findViewById(R.id.tv_comment_content);
                mTvUserName = itemView.findViewById(R.id.tv_username);
            }

        }

    }
}
