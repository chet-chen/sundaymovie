package com.sunday.sundaymovie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunday.sundaymovie.R;

/**
 * Created by agentchen on 2017/2/22.
 * Email agentchen97@gmail.com
 */

public class RecyclerActorItemViewHolder extends RecyclerView.ViewHolder {
    public ImageView mImageViewActorImg;
    public TextView mTextViewActorName;
    public TextView mTextViewActorRoleName;

    public RecyclerActorItemViewHolder(View itemView) {
        super(itemView);
        mImageViewActorImg = (ImageView) itemView.findViewById(R.id.iv_actor_img);
        mTextViewActorName = (TextView) itemView.findViewById(R.id.tv_actors_name);
        mTextViewActorRoleName = (TextView) itemView.findViewById(R.id.tv_actors_role_name);
    }
}
