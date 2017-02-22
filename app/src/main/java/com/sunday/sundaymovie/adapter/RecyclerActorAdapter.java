package com.sunday.sundaymovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.model.Movie;

import java.util.List;

/**
 * Created by agentchen on 2017/2/22.
 * Email agentchen97@gmail.com
 */

public class RecyclerActorAdapter extends RecyclerView.Adapter<RecyclerActorItemViewHolder> {
    private List<Movie.BasicBean.ActorsBean> mList;
    private Context mContext;

    public RecyclerActorAdapter(List<Movie.BasicBean.ActorsBean> list, Context context) {
        super();
        mList = list;
        mContext = context;
    }

    @Override
    public RecyclerActorItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_movie_recycler_item, parent, false);
        return new RecyclerActorItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerActorItemViewHolder holder, int position) {
        holder.mTextViewActorName.setText(mList.get(position).getName());
        holder.mTextViewActorRoleName.setText(mList.get(position).getRoleName());
        Glide.with(mContext).load(mList.get(position).getImg()).into(holder.mImageViewActorImg);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
}
