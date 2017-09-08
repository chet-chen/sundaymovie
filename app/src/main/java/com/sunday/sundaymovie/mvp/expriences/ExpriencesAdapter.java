package com.sunday.sundaymovie.mvp.expriences;

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
 * Created by agentchen on 2017/4/15.
 * Email agentchen97@gmail.com
 */

class ExpriencesAdapter extends RecyclerView.Adapter<ExpriencesAdapter.ViewHolder> {
    private List<Person.ExpriencesBean> mList;
    private Context mContext;
    private ItemListener mItemListener;

    ExpriencesAdapter(List<Person.ExpriencesBean> list, Context context, ItemListener listener) {
        super();
        mList = list;
        mContext = context;
        mItemListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_expriences, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindExpriences(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Person.ExpriencesBean mBean;
        private TextView mTVExpriencesTitle, mTVExpriencesContent;
        private ImageView mImageView;

        ViewHolder(View itemView) {
            super(itemView);
            mTVExpriencesTitle = itemView.findViewById(R.id.tv_expriences_title);
            mTVExpriencesContent = itemView.findViewById(R.id.tv_expriences_content);
            mImageView = itemView.findViewById(R.id.iv_expriences_img);
        }

        void bindExpriences(Person.ExpriencesBean bean) {
            mBean = bean;
            Glide.with(mContext)
                    .load(mBean.getImg())
                    .placeholder(R.drawable.img_load)
                    .into(mImageView);
            mTVExpriencesTitle.setText(mBean.getYear() + "年 " + mBean.getTitle());
            mTVExpriencesContent.setText(mBean.getContent());
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemListener.onClickImage(getAdapterPosition());
        }
    }

    interface ItemListener {
        void onClickImage(int position);
    }
}
