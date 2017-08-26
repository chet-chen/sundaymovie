package com.sunday.sundaymovie.ui;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.util.GlideCacheUtil;

/**
 * Created by agentchen on 2017/8/20.
 */

public class SettingsFragment extends PreferenceFragment {
    private GlideCacheUtil mCacheUtil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCacheUtil = GlideCacheUtil.getInstance();
        addPreferencesFromResource(R.xml.preferences);
        getCacheSize();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        switch (preference.getKey()) {
            case "pref_key_clean_cache":
                cleanCache();
                break;
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private void getCacheSize() {
        mCacheUtil.getCacheSize(getActivity(), new GlideCacheUtil.OnGottenCacheSizeListener() {
            @Override
            public void onGottenCacheSize(final String size) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Preference preference = findPreference("pref_key_clean_cache");
                        preference.setSummary(size);
                    }
                });
            }
        });
    }

    private void cleanCache() {
        mCacheUtil.cleanDiskCache(getActivity(), new GlideCacheUtil.OnCleanCacheListener() {
            @Override
            public void onCleanCache() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Preference preference = findPreference("pref_key_clean_cache");
                        preference.setSummary("0.0 Byte");
                    }
                });
            }
        });
    }
}
