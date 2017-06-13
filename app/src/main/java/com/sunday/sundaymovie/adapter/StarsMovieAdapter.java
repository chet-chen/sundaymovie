package com.sunday.sundaymovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.activity.MovieDetailActivity;
import com.sunday.sundaymovie.model.StarsMovie;

import java.util.List;

/**
 * Created by agentchen on 2017/6/13.
 * Email agentchen97@gmail.com
 */

public class StarsMovieAdapter extends RecyclerView.Adapter<StarsMovieAdapter.ViewHolder> {
    private Context mContext;
    private List<StarsMovie> mList;

    public StarsMovieAdapter(Context context, List<StarsMovie> list) {
        super();
        mList = list;
        mContext = context;
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

    public void refresh(List<StarsMovie> list) {
        mList = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private StarsMovie mStarsMovie;
        private ImageView mImageView;
        private TextView mTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_movie_img);
            mTextView = (TextView) itemView.findViewById(R.id.tv_movie_name);
        }

        void bindStarsMovie(StarsMovie starsMovie) {
            mStarsMovie = starsMovie;
            Glide.with(mContext).load(mStarsMovie.getImg())
                    .placeholder(R.drawable.img_load)
                    .into(mImageView);
            mTextView.setText(mStarsMovie.getName());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            MovieDetailActivity.startMe(mContext, mStarsMovie.getId());
        }
    }
}
