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
import com.sunday.sundaymovie.model.Person;

import java.util.List;

/**
 * Created by agentchen on 2017/4/15.
 * Email agentchen97@gmail.com
 */

public class ExpriencesAdapter extends RecyclerView.Adapter<ExpriencesAdapter.ViewHolder> {
    private List<Person.ExpriencesBean> mList;
    private Context mContext;

    public ExpriencesAdapter(List<Person.ExpriencesBean> list, Context context) {
        super();
        mList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_expriences, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindExprience(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private Person.ExpriencesBean mBean;
        private TextView mTVExpriencesTitle, mTVExpriencesContent;
        private ImageView mImageView;

        ViewHolder(View itemView) {
            super(itemView);
            mTVExpriencesTitle = (TextView) itemView.findViewById(R.id.tv_expriences_title);
            mTVExpriencesContent = (TextView) itemView.findViewById(R.id.tv_expriences_content);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_expriences_img);
        }

        void bindExprience(Person.ExpriencesBean bean) {
            mBean = bean;
            Glide.with(mContext)
                    .load(mBean.getImg())
                    .placeholder(R.drawable.img_load)
                    .into(mImageView);
            mTVExpriencesTitle.setText(String.format("%d年 %s", mBean.getYear(), mBean.getTitle()));
            mTVExpriencesContent.setText(mBean.getContent());
        }
    }
}
