package com.sunday.sundaymovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sunday.sundaymovie.widget.SquareImageView;

import java.util.List;

/**
 * Created by agentchen on 2017/8/15.
 */

public class GridPhotosAdapter extends RecyclerView.Adapter<GridPhotosAdapter.ViewHolder> {
    private List<String> mUrls;
    private Context mContext;
    private ItemListener mItemListener;

    public GridPhotosAdapter(List<String> urls, Context context, ItemListener itemListener) {
        mUrls = urls;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SquareImageView view = new SquareImageView(mContext);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindImage(mUrls.get(position));
    }

    @Override
    public int getItemCount() {
        return mUrls.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView;
            itemView.setOnClickListener(this);
        }

        void bindImage(String url) {
            Glide.with(mContext).load(url).into(mImageView);
        }

        @Override
        public void onClick(View view) {
            mItemListener.onClickPhoto(getAdapterPosition());
        }
    }

    public interface ItemListener {
        void onClickPhoto(int position);
    }
}
