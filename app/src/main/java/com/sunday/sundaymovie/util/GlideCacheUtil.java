package com.sunday.sundaymovie.util;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by agentchen on 2017/6/13.
 * Email agentchen97@gmail.com
 */

public class GlideCacheUtil {
    private static GlideCacheUtil mInst;
    private boolean mCleaning = false;

    public static GlideCacheUtil getInstance() {
        if (mInst == null) {
            synchronized (GlideCacheUtil.class) {
                if (mInst == null) {
                    mInst = new GlideCacheUtil();
                }
            }
        }
        return mInst;
    }

    public void cleanDiskCache(final Context context, final OnCleanCacheListener listener) {
        if (mCleaning) {
            return;
        }
        mCleaning = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
                listener.onCleanCache();
                mCleaning = false;
            }
        }).start();
    }

    public void getChcheSize(final Context context, final OnGottenCacheSizeListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String cacheSize = getFormatSize(getFolderSize(new File(context.getCacheDir()
                            + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
                    listener.onGottenCacheSize(cacheSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public interface OnCleanCacheListener {
        void onCleanCache();
    }

    public interface OnGottenCacheSizeListener {
        void onGottenCacheSize(String size);
    }
}