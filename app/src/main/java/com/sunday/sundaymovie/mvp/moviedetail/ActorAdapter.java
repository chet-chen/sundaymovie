package com.sunday.sundaymovie.mvp.moviedetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.bean.Movie;

import java.util.List;

/**
 * Created by agentchen on 2017/2/22.
 * Email agentchen97@gmail.com
 */

class ActorAdapter extends RecyclerView.Adapter {
    private List<Movie.BasicBean.ActorsBean> mList;
    private Context mContext;
    private ItemListener mItemListener;

    ActorAdapter(List<Movie.BasicBean.ActorsBean> list, Context context, ItemListener itemListener) {
        mList = list;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_actor_recycler, parent, false);
        return new RecyclerActorItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RecyclerActorItemViewHolder) holder).bindActorsBean(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private class RecyclerActorItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Movie.BasicBean.ActorsBean mBean;
        ImageView mImageViewActorImg;
        TextView mTextViewActorName;
        TextView mTextViewActorRoleName;

        RecyclerActorItemViewHolder(View itemView) {
            super(itemView);
            mImageViewActorImg = itemView.findViewById(R.id.iv_actor_img);
            mTextViewActorName = itemView.findViewById(R.id.tv_actors_name);
            mTextViewActorRoleName = itemView.findViewById(R.id.tv_actors_role_name);
        }

        void bindActorsBean(Movie.BasicBean.ActorsBean bean) {
            mBean = bean;
            Glide.with(mContext).load(mBean.getImg())
                    .placeholder(R.drawable.img_load)
                    .into(mImageViewActorImg);
            mTextViewActorName.setText(mBean.getName());
            mTextViewActorRoleName.setText(mBean.getRoleName());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemListener.onClickActor(mBean);
        }
    }

    interface ItemListener {
        void onClickActor(Movie.BasicBean.ActorsBean actorsBean);
    }
}
