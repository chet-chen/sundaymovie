package com.sunday.sundaymovie.allvideo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.bean.VideoAll;
import com.sunday.sundaymovie.util.StringFormatUtil;

import java.util.List;

/**
 * Created by agentchen on 2017/3/9.
 * Email agentchen97@gmail.com
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosViewHolder> {
    private List<VideoAll.Video> mList;
    private Context mContext;
    private ItemListener mItemListener;

    public VideosAdapter(List<VideoAll.Video> list, Context context, ItemListener listener) {
        super();
        mList = list;
        mContext = context;
        mItemListener = listener;
    }

    @Override
    public VideosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false);
        return new VideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideosViewHolder holder, final int position) {
        holder.bindVideo(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void replaceData(List<VideoAll.Video> list) {
        mList = list;
        notifyDataSetChanged();
    }

    class VideosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private VideoAll.Video mVideo;
        private ImageView mImageView;
        private TextView mTVTitle;
        private TextView mTVLength;

        VideosViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.item_video_img);
            mTVTitle = itemView.findViewById(R.id.item_video_title);
            mTVLength = itemView.findViewById(R.id.item_video_length);
        }

        void bindVideo(VideoAll.Video video) {
            mVideo = video;
            Glide.with(mContext).load(mVideo.getImage())
                    .placeholder(R.drawable.img_load)
                    .into(mImageView);
            mTVTitle.setText(mVideo.getTitle());
            mTVLength.setText(StringFormatUtil.getTimeString(
                    mVideo.getLength() * 1000));
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemListener.onClickVideo(getAdapterPosition());
        }
    }

    interface ItemListener {
        void onClickVideo(int position);
    }
}
