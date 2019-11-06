package com.example.android.apis.mydemo.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.apis.R;

import java.util.List;
import java.util.TreeMap;

public class SmartPullRefreshMessageAdapter extends RecyclerView.Adapter<SmartPullRefreshMessageAdapter.ViewHolder> {

    private List<String> mData;

    /**
     * 设置数据源，刷新UI
     * @param data
     */
    public void setDataSource(List<String> data){
        this.mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_smart_pull_refresh_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = getItem(position);
        holder.tvMsg.setText(title);
    }

    public void insertToHeader(List<String> data){
        if(data == null || data.size() == 0){
            return;
        }
        mData.addAll(0, data);
        notifyItemRangeInserted(0, data.size());
//        notifyListDataSetChanged();
    }

    private String getItem(int position){
        return mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMsg;
        public ViewHolder(View itemView) {
            super(itemView);
            tvMsg = itemView.findViewById(R.id.tv_msg);
        }
    }
}
