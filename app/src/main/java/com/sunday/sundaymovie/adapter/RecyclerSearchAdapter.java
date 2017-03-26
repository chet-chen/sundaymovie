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
import com.sunday.sundaymovie.activity.MovieDetailActivity;
import com.sunday.sundaymovie.model.Search;
import com.sunday.sundaymovie.model.SearchMovie;
import com.sunday.sundaymovie.model.SearchPerson;
import com.sunday.sundaymovie.model.SearchResult;

import java.util.List;

/**
 * Created by agentchen on 2017/3/23.
 * Email agentchen97@gmail.com
 */

public class RecyclerSearchAdapter extends RecyclerView.Adapter<RecyclerSearchAdapter.ViewHolder> {
    private Context mContext;
    private List<Search> mSearches;
    private int mType;

    public RecyclerSearchAdapter(Context context, List<Search> list, int type) {
        super();
        mContext = context;
        mSearches = list;
        mType = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with((Activity) mContext).load(mSearches.get(position).getCover())
                .into(holder.mImageView);
        switch (mType) {
            case SearchResult.TYPE_MOVIE:
                final SearchMovie sm = (SearchMovie) mSearches.get(position);
                holder.mTitle.setText(sm.getMovieTitle());
                holder.mType.setText(sm.getGenreTypes());
                holder.mRating.setText(String.format("%s 分", sm.getMovieRating()));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MovieDetailActivity.startMe(mContext, sm.getMovieId());
                    }
                });
                break;
            case SearchResult.TYPE_PERSON:
                SearchPerson sp = (SearchPerson) mSearches.get(position);
                holder.mTitle.setText(sp.getPersonTitle());
                holder.mType.setText(sp.getPersonFilmography());
                holder.mRating.setText(sp.getBirth());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mSearches.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTitle, mType, mRating;

        ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_search_movie);
            mTitle = (TextView) itemView.findViewById(R.id.tv_search_title);
            mType = (TextView) itemView.findViewById(R.id.tv_search_t1);
            mRating = (TextView) itemView.findViewById(R.id.tv_search_t2);
        }
    }
}
