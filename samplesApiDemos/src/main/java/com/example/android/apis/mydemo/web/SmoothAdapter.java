package com.example.android.apis.mydemo.web;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.apis.R;

import java.util.List;

public class SmoothAdapter extends RecyclerView.Adapter<SmoothAdapter.ViewHolder> {
    private final List<SmoothModel> mData;

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_GIF = 1;
    private static final int TYPE_OTHER = 2;
    private int mViewType = TYPE_NORMAL;
    private ViewGroup mContext;

    SmoothAdapter(List<SmoothModel> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent;
        View view = null;
        switch (viewType) {
            case TYPE_NORMAL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_smooth_normal, parent, false);
                break;
            case TYPE_GIF:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_smooth_gif, parent, false);
                break;
            case TYPE_OTHER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_smooth_other, parent, false);
                break;
        }
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SmoothModel data = mData.get(position);
        int viewType = getItemViewType(position);
        if (viewType == TYPE_NORMAL) {
            holder.mTvContent.setText(data.getContent());
        } else if (viewType == TYPE_GIF) {
            String url = data.getUrl();
            Glide.with(mContext).load(url).into(holder.mIvGif);
        }else{
            // TYPE_OTHER
        }
    }

    void notifyItem(SmoothModel model) {
        this.mData.add(model);
        notifyItemInserted(mData.size());

    }

    @Override
    public int getItemViewType(int position) {
        // 赶紧数据字段返回type
        return mData.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public void setViewType(int viewType) {
        this.mViewType = viewType;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvContent;
        ImageView mIvGif;

        ViewHolder(View itemView, int viewType) {
            super(itemView);
            switch (viewType) {
                case TYPE_NORMAL:
                    mTvContent = itemView.findViewById(R.id.tv_content);
                    break;
                case TYPE_GIF:
                    mIvGif = itemView.findViewById(R.id.iv_smooth);
                    break;
                case TYPE_OTHER:
                    break;
            }

        }
    }
}
