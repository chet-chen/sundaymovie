package com.sunday.sundaymovie.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

/**
 * Created by agentchen on 2017/7/31.
 */

public class PhotoModel {
    private Context mContext;
    private OnDownloadListener mListener;

    public PhotoModel(Context context) {
        mContext = context;
    }

    public void downloadImage(String url, OnDownloadListener listener) {
        mListener = listener;
        new Download().execute(url);
    }

    private class Download extends AsyncTask<String, Void, File> {
        @Override
        protected File doInBackground(String... params) {
            File file = null;
            try {
                Bitmap bitmap = Glide.with(mContext)
                        .load(params[0])
                        .asBitmap()
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                File parent = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "SundayMovie");
                parent.mkdirs();
                file = new File(parent, System.currentTimeMillis() + ".jpg");
                saveFile(file, bitmap);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return file;
        }

        @Override
        protected void onPostExecute(File file) {
            if (file.length() > 0) {
                mListener.onDownloadSuccess(file);
            } else {
                mListener.onDownloadFailed();
            }
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

    public interface OnDownloadListener {
        void onDownloadSuccess(File file);

        void onDownloadFailed();
    }
}
