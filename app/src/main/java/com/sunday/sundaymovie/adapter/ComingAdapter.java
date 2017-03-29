package com.sunday.sundaymovie.adapter;

import android.app.Activity;
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
import com.sunday.sundaymovie.model.ComingMovie;

import java.util.List;

/**
 * Created by agentchen on 2017/3/29.
 * Email agentchen97@gmail.com
 */

public class ComingAdapter extends RecyclerView.Adapter<ComingAdapter.ViewHolder> {
    private Context mContext;
    private List<ComingMovie> mMovies;

    public ComingAdapter(Context context, List<ComingMovie> movies) {
        super();
        mContext = context;
        mMovies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_coming_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ComingMovie movie = mMovies.get(position);
        Glide.with((Activity) mContext)
                .load(movie.getImage())
                .placeholder(R.drawable.img_load)
                .into(holder.mImageView);
        holder.mTVTitle.setText(movie.getTitle());
        holder.mTVDate.setText(movie.getReleaseDate());
        holder.mTVType.setText(movie.getType());
        holder.mTVDirecto.setText(String.format("导演: %s", movie.getDirector()));
        holder.mTVActor.setText(String.format("主演: %s, %s", movie.getActor1(), movie.getActor2()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailActivity.startMe(mContext, movie.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void refresh(List<ComingMovie> movies) {
        mMovies = movies;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTVTitle, mTVDate, mTVType, mTVDirecto, mTVActor;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_movie_img);
            mTVTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
            mTVDate = (TextView) itemView.findViewById(R.id.tv_movie_date);
            mTVType = (TextView) itemView.findViewById(R.id.tv_movie_type);
            mTVDirecto = (TextView) itemView.findViewById(R.id.tv_movie_director);
            mTVActor = (TextView) itemView.findViewById(R.id.tv_movie_actor);
        }
    }
}
