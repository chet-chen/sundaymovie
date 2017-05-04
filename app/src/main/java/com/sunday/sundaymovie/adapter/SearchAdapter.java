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
import com.sunday.sundaymovie.activity.MovieDetailActivity;
import com.sunday.sundaymovie.activity.PersonActivity;
import com.sunday.sundaymovie.model.Search;
import com.sunday.sundaymovie.model.SearchMovie;
import com.sunday.sundaymovie.model.SearchPerson;
import com.sunday.sundaymovie.model.SearchResult;

import java.util.List;

/**
 * Created by agentchen on 2017/3/23.
 * Email agentchen97@gmail.com
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context mContext;
    private List<Search> mSearches;
    private int mType;

    public SearchAdapter(Context context, List<Search> list, int type) {
        super();
        mContext = context;
        mSearches = list;
        mType = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bindSearchMovie(mSearches.get(position), mType);
    }

    @Override
    public int getItemCount() {
        return mSearches.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Search mSearch;
        private int type;
        private ImageView mImageView;
        private TextView mTitle, mT1, mT2;

        ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_search_movie);
            mTitle = (TextView) itemView.findViewById(R.id.tv_search_title);
            mT1 = (TextView) itemView.findViewById(R.id.tv_search_t1);
            mT2 = (TextView) itemView.findViewById(R.id.tv_search_t2);
        }

        void bindSearchMovie(Search movie, int type) {
            mSearch = movie;
            Glide.with(mContext).load(mSearch.getCover())
                    .placeholder(R.drawable.img_load)
                    .into(mImageView);
            this.type = type;
            switch (type) {
                case SearchResult.TYPE_MOVIE:
                    SearchMovie sm = (SearchMovie) mSearch;
                    mTitle.setText(sm.getMovieTitle());
                    mT1.setText(sm.getGenreTypes());
                    if (sm.getMovieRating() == null || sm.getMovieRating().isEmpty()) {
                        mT2.setText("暂无评分");
                    } else {
                        mT2.setText(String.format("%s ★", sm.getMovieRating()));
                    }
                    itemView.setOnClickListener(this);
                    break;
                case SearchResult.TYPE_PERSON:
                    SearchPerson sp = (SearchPerson) mSearch;
                    mTitle.setText(sp.getPersonTitle());
                    if (sp.getPersonFilmography() == null || sp.getPersonFilmography().isEmpty()) {
                        mT1.setVisibility(View.GONE);
                    } else {
                        if (mT1.getVisibility() == View.GONE) {
                            mT1.setVisibility(View.VISIBLE);
                        }
                        mT1.setText(sp.getPersonFilmography());
                    }
                    mT2.setText(sp.getBirth());
                    itemView.setOnClickListener(this);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            if (type == SearchResult.TYPE_MOVIE) {
                MovieDetailActivity.startMe(mContext, ((SearchMovie) mSearch).getMovieId());
            } else if (type == SearchResult.TYPE_PERSON) {
                PersonActivity.startMe(mContext, ((SearchPerson) mSearch).getPersonId(), ((SearchPerson) mSearch).getPersonTitle());
            }
        }
    }
}
