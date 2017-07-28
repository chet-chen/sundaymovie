package com.sunday.sundaymovie.moviedetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sunday.sundaymovie.R;

import java.util.ArrayList;

/**
 * Created by agentchen on 2017/7/24.
 */

class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_IMG = 0;
    private static final int TYPE_BTN = 1;
    private Context mContext;
    private ArrayList<String> mUrls;
    private ItemListener mListener;

    PhotoAdapter(ArrayList<String> urls, Context context, ItemListener listener) {
        mUrls = urls;
        mContext = context;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_IMG:
                View img = LayoutInflater.from(mContext).inflate(R.layout.item_movie_detail_image, parent, false);
                return new ImageViewHolder(img);
            case TYPE_BTN:
                View btn = LayoutInflater.from(mContext).inflate(R.layout.item_movie_detail_more_img, parent, false);
                return new BtnViewHolder(btn);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_IMG) {
            ((ImageViewHolder) holder).showImage(mUrls.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mUrls.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mUrls.size()) {
            return TYPE_IMG;
        } else {
            return TYPE_BTN;
        }
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;

        ImageViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView;
        }

        void showImage(String url) {
            Glide.with(mContext).load(url).placeholder(R.drawable.img_load).into(mImageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onImageClick(mUrls, getAdapterPosition());
        }
    }

    private class BtnViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        BtnViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onMoreImageClick();
        }
    }

    interface ItemListener {
        void onImageClick(ArrayList<String> urls, int position);

        void onMoreImageClick();
    }
}
