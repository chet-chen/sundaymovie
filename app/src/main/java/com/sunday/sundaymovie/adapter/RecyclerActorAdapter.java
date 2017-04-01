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
import com.sunday.sundaymovie.model.Movie;

import java.util.List;

/**
 * Created by agentchen on 2017/2/22.
 * Email agentchen97@gmail.com
 */

public class RecyclerActorAdapter extends RecyclerView.Adapter {
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_HEADER = 2;
    private static final int TYPE_FOOT = 3;
    private List<Movie.BasicBean.ActorsBean> mList;
    private Context mContext;

    public RecyclerActorAdapter(List<Movie.BasicBean.ActorsBean> list, Context context) {
        super();
        mList = list;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ITEM:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.activity_movie_recycler_item, parent, false);
                return new RecyclerActorItemViewHolder(view);
            case TYPE_HEADER:
                View header = LayoutInflater.from(mContext)
                        .inflate(R.layout.activity_movie_recycler_header_item, parent, false);
                return new RecyclerActorItemEmptyVH(header);
            case TYPE_FOOT:
                View foot = LayoutInflater.from(mContext)
                        .inflate(R.layout.activity_movie_recycler_foow_item, parent, false);
                return new RecyclerActorItemEmptyVH(foot);
            default:
                throw new RuntimeException("type is error");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            ((RecyclerActorItemViewHolder) holder).bindActorsBean(mList.get(position - 1));
        }
    }

    @Override
    public int getItemCount() {
        return getBasicItemCount() + 2;
    }

    private int getBasicItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        if (position == mList.size() + 1) {
            return TYPE_FOOT;
        }
        return TYPE_ITEM;
    }

    private class RecyclerActorItemViewHolder extends RecyclerView.ViewHolder {
        private Movie.BasicBean.ActorsBean mBean;
        ImageView mImageViewActorImg;
        TextView mTextViewActorName;
        TextView mTextViewActorRoleName;

        RecyclerActorItemViewHolder(View itemView) {
            super(itemView);
            mImageViewActorImg = (ImageView) itemView.findViewById(R.id.iv_actor_img);
            mTextViewActorName = (TextView) itemView.findViewById(R.id.tv_actors_name);
            mTextViewActorRoleName = (TextView) itemView.findViewById(R.id.tv_actors_role_name);
        }

        void bindActorsBean(Movie.BasicBean.ActorsBean bean) {
            mBean = bean;
            Glide.with(mContext).load(mBean.getImg())
                    .placeholder(R.drawable.img_load)
                    .into(mImageViewActorImg);
            mTextViewActorName.setText(mBean.getName());
            mTextViewActorRoleName.setText(mBean.getRoleName());
        }
    }

    private class RecyclerActorItemEmptyVH extends RecyclerView.ViewHolder {
        RecyclerActorItemEmptyVH(View itemView) {
            super(itemView);
        }
    }
}
