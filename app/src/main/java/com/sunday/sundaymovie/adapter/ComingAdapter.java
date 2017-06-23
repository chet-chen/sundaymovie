package com.sunday.sundaymovie.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.activity.MovieDetailActivity;
import com.sunday.sundaymovie.db.StarsTableHelper;
import com.sunday.sundaymovie.model.ComingMovie;

import java.util.List;

import static com.sunday.sundaymovie.adapter.ShowTimeAdapter.ID_STAR;
import static com.sunday.sundaymovie.adapter.ShowTimeAdapter.ID_UN_STAR;

/**
 * Created by agentchen on 2017/3/29.
 * Email agentchen97@gmail.com
 */

public class ComingAdapter extends RecyclerView.Adapter<ComingAdapter.ViewHolder> {
    public static final int GROUP_COMING = 2;
    private int contextMenuPosition;
    private Context mContext;
    private List<ComingMovie> mMovies;

    public ComingAdapter(Context context, List<ComingMovie> movies) {
        super();
        mContext = context;
        mMovies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_coming_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ComingMovie movie = mMovies.get(position);
        holder.bindMovie(movie);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void notifyDataSetChanged(List<ComingMovie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    public int getContextMenuPosition() {
        return contextMenuPosition;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {
        private ComingMovie mMovie;
        private ImageView mImageView;
        private TextView mTVTitle, mTVDate, mTVType, mTVDirecto, mTVActor;

        ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_hot_movie_img);
            mTVTitle = (TextView) itemView.findViewById(R.id.tv_movie_t);
            mTVDate = (TextView) itemView.findViewById(R.id.tv_movie_date);
            mTVType = (TextView) itemView.findViewById(R.id.tv_movie_type);
            mTVDirecto = (TextView) itemView.findViewById(R.id.tv_movie_director);
            mTVActor = (TextView) itemView.findViewById(R.id.tv_movie_actor);
        }

        void bindMovie(ComingMovie movie) {
            mMovie = movie;
            Glide.with(mContext)
                    .load(mMovie.getImage())
                    .placeholder(R.drawable.img_load)
                    .into(mImageView);
            mTVTitle.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            mTVTitle.setText(mMovie.getTitle());
            mTVDate.setText(mMovie.getReleaseDate());
            mTVType.setText(mMovie.getType());
            if (!mMovie.getDirector().isEmpty()) {
                mTVDirecto.setText(String.format("导演: %s", mMovie.getDirector()));
            }
            if (!mMovie.getActor1().isEmpty()) {
                mTVActor.setText(String.format("主演: %s, %s", mMovie.getActor1(), movie.getActor2()));
            }
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            MovieDetailActivity.startMe(mContext, mMovie.getId());
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            StarsTableHelper helper = new StarsTableHelper(mContext);
            if (helper.queryIsExist(mMovie.getId())) {
                menu.add(GROUP_COMING, ID_UN_STAR, Menu.NONE, "取消收藏");
            } else {
                menu.add(GROUP_COMING, ID_STAR, Menu.NONE, "收藏");
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
