package com.sunday.sundaymovie.ui;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.util.GlideCacheUtil;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by agentchen on 2017/8/20.
 */

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    private Preference mCleanCache;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        findPreference();

        mCleanCache.setOnPreferenceClickListener(this);

        getCacheSize();
    }

    private void findPreference() {
        mCleanCache = findPreference("pref_key_clean_cache");
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (mCleanCache == preference) {
            cleanCache();
        }
        return true;
    }

    private void getCacheSize() {
        GlideCacheUtil.getCacheSize().subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                mCleanCache.setSummary(s);
            }
        });
    }

    private void cleanCache() {
        GlideCacheUtil.cleanDiskCache().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                mCleanCache.setSummary("0.0 Byte");
                Toast.makeText(getActivity(), "缓存清理成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
