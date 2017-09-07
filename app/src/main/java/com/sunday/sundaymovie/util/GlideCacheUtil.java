package com.sunday.sundaymovie.util;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.sunday.sundaymovie.base.BaseApplication;

import java.io.File;
import java.math.BigDecimal;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by agentchen on 2017/6/13.
 * Email agentchen97@gmail.com
 */

public final class GlideCacheUtil {
    private static boolean mCleaning = false;

    private GlideCacheUtil() {
    }

    public static Observable<Object> cleanDiskCache() {
        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                if (!mCleaning) {
                    mCleaning = true;
                    Glide.get(BaseApplication.getContext()).clearDiskCache();
                    e.onComplete();
                    mCleaning = false;
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<String> getCacheSize() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                String cacheSize = getFormatSize(getFolderSize(new File(
                        BaseApplication.getContext().getCacheDir() + "/" +
                                InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
                e.onNext(cacheSize);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private static long getFolderSize(File file) {
        long size = 0;
        File[] fileList = file.listFiles();
        for (File f : fileList) {
            if (f.isDirectory()) {
                size = size + getFolderSize(f);
            } else {
                size = size + f.length();
            }
        }
        return size;
    }

    private static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + " Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " TB";
    }
}