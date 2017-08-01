package com.sunday.sundaymovie.star;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.bean.StarMovie;

import java.util.List;

/**
 * Created by agentchen on 2017/6/13.
 * Email agentchen97@gmail.com
 */

class StarsMovieAdapter extends RecyclerView.Adapter<StarsMovieAdapter.ViewHolder> {
    private Context mContext;
    private List<StarMovie> mList;
    private ItemListener mItemListener;

    StarsMovieAdapter(Context context, List<StarMovie> list, ItemListener listener) {
        super();
        mList = list;
        mContext = context;
        mItemListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.iten_stars_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindStarsMovie(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void replaceData(List<StarMovie> list) {
        mList = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private StarMovie mStarMovie;
        private ImageView mImageView;
        private TextView mTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_movie_img);
            mTextView = itemView.findViewById(R.id.tv_movie_name);
        }

        void bindStarsMovie(StarMovie starMovie) {
            mStarMovie = starMovie;
            Glide.with(mContext).load(mStarMovie.getImg())
                    .placeholder(R.drawable.img_load)
                    .into(mImageView);
            mTextView.setText(mStarMovie.getName());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemListener.onClick(getAdapterPosition());
        }
    }

    interface ItemListener {
        void onClick(int position);
    }
}
