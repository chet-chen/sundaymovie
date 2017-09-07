package com.sunday.sundaymovie.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by agentchen on 2017/7/31.
 */

public class PhotoModel {
    private Context mContext;

    public PhotoModel(Context context) {
        mContext = context;
    }

    public Observable<File> downloadImage(final String url) {
        return Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<File> e) throws Exception {
                File file;
                Bitmap bitmap = Glide.with(mContext)
                        .load(url)
                        .asBitmap()
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                File parent = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "SundayMovie");
                parent.mkdirs();
                file = new File(parent, System.currentTimeMillis() + ".jpg");
                saveFile(file, bitmap);
                if (file.length() > 0) {
                    e.onNext(file);
                } else {
                    e.onError(new Exception("Download failed"));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private void saveFile(File file, Bitmap bitmap) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
