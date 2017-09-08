package com.sunday.sundaymovie.mvp.person;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.bean.Person;

import java.util.List;

/**
 * Created by agentchen on 2017/4/13.
 * Email agentchen97@gmail.com
 */

class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    private List<Person.RelationPersonsBean> mList;
    private Context mContext;

    PersonAdapter(List<Person.RelationPersonsBean> list, Context context) {
        super();
        mList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_actor_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindActorsBean(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Person.RelationPersonsBean mBean;
        private ImageView mImageViewActorImg;
        private TextView mTextViewActorName;
        private TextView mTextViewActorRoleName;

        ViewHolder(View itemView) {
            super(itemView);
            mImageViewActorImg = itemView.findViewById(R.id.iv_actor_img);
            mTextViewActorName = itemView.findViewById(R.id.tv_actors_name);
            mTextViewActorRoleName = itemView.findViewById(R.id.tv_actors_role_name);
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
            PersonActivity.startMe(mContext, mBean.getRPersonId());
        }
    }


}
