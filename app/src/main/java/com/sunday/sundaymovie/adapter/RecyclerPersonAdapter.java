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
import com.sunday.sundaymovie.activity.PersonActivity;
import com.sunday.sundaymovie.model.Person;

import java.util.List;

/**
 * Created by agentchen on 2017/4/13.
 * Email agentchen97@gmail.com
 */

public class RecyclerPersonAdapter extends RecyclerView.Adapter {
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_HEADER = 2;
    private static final int TYPE_FOOT = 3;
    private List<Person.RelationPersonsBean> mList;
    private Context mContext;

    public RecyclerPersonAdapter(List<Person.RelationPersonsBean> list, Context context) {
        super();
        mList = list;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ITEM:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_movie_recycler, parent, false);
                return new RecyclerPersonAdapter.RecyclerActorItemViewHolder(view);
            case TYPE_HEADER:
                View header = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_movie_recycler_header, parent, false);
                return new RecyclerPersonAdapter.RecyclerActorItemEmptyVH(header);
            case TYPE_FOOT:
                View foot = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_movie_recycler_foow, parent, false);
                return new RecyclerPersonAdapter.RecyclerActorItemEmptyVH(foot);
            default:
                throw new RuntimeException("type is error");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            ((RecyclerPersonAdapter.RecyclerActorItemViewHolder) holder).bindActorsBean(mList.get(position - 1));
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

    private class RecyclerActorItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Person.RelationPersonsBean mBean;
        ImageView mImageViewActorImg;
        TextView mTextViewActorName;
        TextView mTextViewActorRoleName;

        RecyclerActorItemViewHolder(View itemView) {
            super(itemView);
            mImageViewActorImg = (ImageView) itemView.findViewById(R.id.iv_actor_img);
            mTextViewActorName = (TextView) itemView.findViewById(R.id.tv_actors_name);
            mTextViewActorRoleName = (TextView) itemView.findViewById(R.id.tv_actors_role_name);
        }

        void bindActorsBean(Person.RelationPersonsBean bean) {
            mBean = bean;
            Glide.with(mContext).load(mBean.getRCover())
                    .placeholder(R.drawable.img_load)
                    .into(mImageViewActorImg);
            mTextViewActorName.setText(mBean.getRNameCn());
            mTextViewActorRoleName.setText(mBean.getRelation());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            PersonActivity.startMe(mContext, mBean.getRPersonId(), mBean.getRNameCn());
        }
    }

    private class RecyclerActorItemEmptyVH extends RecyclerView.ViewHolder {
        RecyclerActorItemEmptyVH(View itemView) {
            super(itemView);
        }
    }
}
