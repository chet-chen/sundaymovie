package com.sunday.sundaymovie.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.activity.MovieDetailActivity;
import com.sunday.sundaymovie.model.ShowTimeMovies;

import java.util.List;

/**
 * Created by agentchen on 2017/3/28.
 * Email agentchen97@gmail.com
 */

public class ShowTimeAdapter extends RecyclerView.Adapter<ShowTimeAdapter.ViewHolder> {
    private Context mContext;
    private List<ShowTimeMovies.MsBean> mMsBeans;

    public ShowTimeAdapter(Context context, List<ShowTimeMovies.MsBean> msBeans) {
        super();
        mContext = context;
        mMsBeans = msBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_show_time_movie, parent, false);
        return new ViewHolder(view);
    }

    public void refresh(List<ShowTimeMovies.MsBean> msBeans) {
        mMsBeans = msBeans;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ShowTimeMovies.MsBean msBean = mMsBeans.get(position);
        Glide.with((Activity) mContext).load(msBean.getImg()).placeholder(R.drawable.img_load).into(holder.mImageView);
        holder.mTVMovieTCN.setText(msBean.getTCn());
        holder.mTVMovieTEN.setText(msBean.getTEn());
        holder.mTVMovieType.setText(msBean.getMovieType());
        holder.mTVMovieDN.setText(String.format("导演: %s", msBean.getDN()));
        holder.mTVMovieN.setText(String.format("主演: %s, %s", msBean.getAN1(), msBean.getAN2()));
        if (msBean.getR() > 0) {
            holder.mRatingBar.setVisibility(View.VISIBLE);
            holder.mTVMovieRating.setText(String.valueOf(msBean.getR()));
            holder.mRatingBar.setRating((float) msBean.getR() / 2);
        } else {
            holder.mRatingBar.setVisibility(View.GONE);
            holder.mTVMovieRating.setText("暂无评分");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailActivity.startMe(mContext, msBean.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMsBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTVMovieTCN, mTVMovieTEN, mTVMovieRating, mTVMovieType, mTVMovieDN, mTVMovieN;
        private RatingBar mRatingBar;

        ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_movie_img);
            mTVMovieTCN = (TextView) itemView.findViewById(R.id.tv_movie_title);
            mTVMovieTEN = (TextView) itemView.findViewById(R.id.tv_movie_t_en);
            mTVMovieRating = (TextView) itemView.findViewById(R.id.tv_rating);
            mTVMovieType = (TextView) itemView.findViewById(R.id.tv_movie_type);
            mTVMovieDN = (TextView) itemView.findViewById(R.id.tv_movie_director);
            mTVMovieN = (TextView) itemView.findViewById(R.id.tv_movie_actor);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.rb_rating);
        }
    }
}
