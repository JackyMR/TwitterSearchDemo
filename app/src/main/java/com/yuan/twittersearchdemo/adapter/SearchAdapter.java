package com.yuan.twittersearchdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yuan.twittersearchdemo.R;
import com.yuan.twittersearchdemo.model.Status;

import java.util.List;

/**
 * Created by Yuan on 15/12/23.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    private Context mContext;

    private List<Status> mData;

    private OnItemClickListener listener;

    public SearchAdapter(@NonNull Context ctx, @NonNull List<Status> list) {
        this.mContext = ctx;
        this.mData = list;
    }

    public void setOnItemViewClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_list_search, parent, false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> listener.onItemClick(holder.itemView,position));
        Status entity = mData.get(position);
        holder.text_username.setText(entity.user.name);
        holder.text_content.setText(entity.text);
        Picasso.with(mContext)
                .load(entity.user.profile_image_url)
                .placeholder(R.mipmap.icon_twitter)
                .into(holder.img_portrait);
    }

    @Override
    public int getItemCount() {
        return (mData != null) ? mData.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public View itemView;

        public TextView text_username;

        public TextView text_content;

        public ImageView img_portrait;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            text_username = (TextView) itemView.findViewById(R.id.text_username);
            text_content = (TextView) itemView.findViewById(R.id.text_content);
            img_portrait = (ImageView) itemView.findViewById(R.id.img_portrait);
        }
    }
}
