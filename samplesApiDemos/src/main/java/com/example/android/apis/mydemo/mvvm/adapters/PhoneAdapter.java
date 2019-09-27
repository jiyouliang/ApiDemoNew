package com.example.android.apis.mydemo.mvvm.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.android.apis.R;
import com.example.android.apis.mydemo.mvvm.models.Phone;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ViewHolder> {
    private final RequestManager glide;
    private final Context mContext;
    private List<Phone> mData;

    public PhoneAdapter(Context context, List<Phone> data) {
        this.mData = data;
        glide = Glide.with(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Phone phone = mData.get(position);
        if(phone == null){
            return;
        }
        holder.tvTitle.setText(phone.getTitle());
//        glide.load(phone.getImage()).into(holder.ivLogo);
        Glide.with(mContext).load(phone.getImage().replace("jiyouliang.com", "47.106.182.74")).into(holder.ivLogo);
    }

    @Override
    public int getItemCount() {
        return mData != null? mData.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        ImageView ivLogo;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivLogo = itemView.findViewById(R.id.iv_logo);

        }
    }
}
