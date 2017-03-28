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
import com.sunday.sundaymovie.activity.VideoActivity;
import com.sunday.sundaymovie.model.VideoAll;
import com.sunday.sundaymovie.util.StringFormatUtil;

import java.util.List;

/**
 * Created by agentchen on 2017/3/9.
 * Email agentchen97@gmail.com
 */

public class RecyclerVideosAdapter extends RecyclerView.Adapter<RecyclerVideosAdapter.VideosViewHolder> {
    private List<VideoAll.Video> mList;
    private Context mContext;

    public RecyclerVideosAdapter(List<VideoAll.Video> list, Context context) {
        super();
        mList = list;
        mContext = context;
    }

    @Override
    public VideosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.videos_item, parent, false);
        return new VideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideosViewHolder holder, final int position) {
        final VideoAll.Video video = mList.get(position);
        holder.mTVTitle.setText(video.getTitle());
        holder.mTVLength.setText(StringFormatUtil.getTimeString(
                video.getLength() * 1000));
        Glide.with((Activity) mContext)
                .load(video.getImage())
                .placeholder(R.drawable.img_load)
                .into(holder.mImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoActivity.startMe(mContext, video.getHightUrl(), video.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class VideosViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mTVTitle;
        private TextView mTVLength;

        VideosViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_video_img);
            mTVTitle = (TextView) itemView.findViewById(R.id.item_video_title);
            mTVLength = (TextView) itemView.findViewById(R.id.item_video_length);
        }
    }
}
