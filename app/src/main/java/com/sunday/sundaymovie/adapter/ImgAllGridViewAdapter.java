package com.sunday.sundaymovie.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sunday.sundaymovie.activity.PhotoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/2/28.
 * Email agentchen97@gmail.com
 */

public class ImgAllGridViewAdapter extends BaseAdapter {
    private List<String> mList;
    private Context mContext;

    public ImgAllGridViewAdapter(List<String> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT
                    , (parent.getWidth() - dip2px(mContext, 8)) / 3));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }
        Glide.with((Activity) mContext).load(mList.get(position)).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoActivity.startMe(mContext, (ArrayList<String>) mList, position);
            }
        });
        return imageView;
    }

    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
