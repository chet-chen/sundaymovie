package com.sunday.sundaymovie.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.sunday.sundaymovie.R;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by agentchen on 2017/2/28.
 * Email agentchen97@gmail.com
 */

public class PhotoViewPagerAdapter extends PagerAdapter {
    private List<String> mList;
    private Context mContext;

    public PhotoViewPagerAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.photo_view_pager_item, container, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.iv_view_pager_item);
        Glide.with(mContext).load(mList.get(position)).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource
                    , GlideAnimation<? super GlideDrawable> glideAnimation) {
                imageView.setImageDrawable(resource);
                new PhotoViewAttacher(imageView);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
