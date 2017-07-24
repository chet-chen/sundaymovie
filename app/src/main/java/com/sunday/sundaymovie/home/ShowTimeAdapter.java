package com.sunday.sundaymovie.home;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.bean.ShowTimeMovies;
import com.sunday.sundaymovie.db.StarsTableHelper;

import java.util.List;

/**
 * Created by agentchen on 2017/3/28.
 * Email agentchen97@gmail.com
 */

public class ShowTimeAdapter extends RecyclerView.Adapter<ShowTimeAdapter.ViewHolder> {
    public static final int GROUP_SHOW_TIME = 1;
    public static final int ID_STAR = 1;
    public static final int ID_UN_STAR = 2;
    private int contextMenuPosition;
    private Context mContext;
    private List<ShowTimeMovies.MsBean> mMsBeans;
    private ItemListener mItemListener;

    public ShowTimeAdapter(Context context, List<ShowTimeMovies.MsBean> msBeans, ItemListener listener) {
        super();
        mContext = context;
        mMsBeans = msBeans;
        mItemListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_show_time_movie, parent, false);
        return new ViewHolder(view);
    }

    public void replaceData(List<ShowTimeMovies.MsBean> msBeans) {
        mMsBeans = msBeans;
        notifyDataSetChanged();
    }

    public ShowTimeMovies.MsBean getSelectedMovie() {
        return mMsBeans.get(getContextMenuPosition());
    }

    public int getContextMenuPosition() {
        return contextMenuPosition;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindMsBean(mMsBeans.get(position));
    }

    @Override
    public int getItemCount() {
        return mMsBeans.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, View.OnLongClickListener {
        private ShowTimeMovies.MsBean mMsBean;
        private ImageView mImageView;
        private TextView mTVMovieTCN, mTVMovieTEN, mTVMovieRating, mTVMovieType, mTVMovieDN, mTVMovieN;
        private RatingBar mRatingBar;

        ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_hot_movie_img);
            mTVMovieTCN = (TextView) itemView.findViewById(R.id.tv_movie_t);
            mTVMovieTEN = (TextView) itemView.findViewById(R.id.tv_movie_t_en);
            mTVMovieRating = (TextView) itemView.findViewById(R.id.tv_rating);
            mTVMovieType = (TextView) itemView.findViewById(R.id.tv_movie_type);
            mTVMovieDN = (TextView) itemView.findViewById(R.id.tv_movie_director);
            mTVMovieN = (TextView) itemView.findViewById(R.id.tv_movie_actor);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.rb_rating);
        }

        void bindMsBean(ShowTimeMovies.MsBean msBean) {
            mMsBean = msBean;
            Glide.with(mContext).load(mMsBean.getImg())
                    .placeholder(R.drawable.img_load)
                    .into(mImageView);
            mTVMovieTCN.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            mTVMovieTCN.setText(mMsBean.getTCn());
            mTVMovieTEN.setText(mMsBean.getTEn());
            mTVMovieType.setText(mMsBean.getMovieType());
            mTVMovieDN.setText(String.format("导演: %s", mMsBean.getDN()));
            mTVMovieN.setText(String.format("主演: %s, %s", mMsBean.getAN1(), mMsBean.getAN2()));
            if (msBean.getR() > 0) {
                if (mRatingBar.getVisibility() == View.GONE) {
                    mRatingBar.setVisibility(View.VISIBLE);
                }
                mTVMovieRating.setTextColor(mContext.getResources().getColor(R.color.colorTextBlack_2));
                mTVMovieRating.setText(String.valueOf(mMsBean.getR()));
                mRatingBar.setRating((float) mMsBean.getR() / 2);
            } else {
                mTVMovieRating.setTextColor(mContext.getResources().getColor(R.color.colorTextBlack_4));
                mRatingBar.setVisibility(View.GONE);
                mTVMovieRating.setText("暂无评分");
            }
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_root:
                    if (mItemListener != null) {
                        mItemListener.onClick(mMsBean.getId());
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            StarsTableHelper helper = new StarsTableHelper(mContext);
            if (helper.queryIsExist(mMsBean.getId())) {
                menu.add(GROUP_SHOW_TIME, ID_UN_STAR, Menu.NONE, "取消收藏");
            } else {
                menu.add(GROUP_SHOW_TIME, ID_STAR, Menu.NONE, "收藏");
            }
            helper.close();
        }

        @Override
        public boolean onLongClick(View v) {
            contextMenuPosition = getAdapterPosition();
            return false;
        }
    }

}
