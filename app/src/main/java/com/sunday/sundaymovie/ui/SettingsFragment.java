package com.sunday.sundaymovie.ui;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sunday.sundaymovie.R;
import com.sunday.sundaymovie.util.GlideCacheUtil;

/**
 * Created by agentchen on 2017/8/20.
 */

public class SettingsFragment extends PreferenceFragment {
    private static final String TAG = "SettingsFragment";
    GlideCacheUtil mCacheUtil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCacheUtil = GlideCacheUtil.getInstance();
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onStart() {
        super.onStart();
        getCacheSize();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        Log.d(TAG, "onPreferenceTreeClick: " + preference.getKey());
        switch (preference.getKey()) {
            case "pref_key_clean_cache":
                cleanCache();
                break;
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private void getCacheSize() {
        mCacheUtil.getChcheSize(getActivity(), new GlideCacheUtil.OnGottenCacheSizeListener() {
            @Override
            public void onGottenCacheSize(final String size) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Preference preference = findPreference("pref_key_clean_cache");
                        preference.setSummary(String.format("共 %s", size));
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
                        preference.setSummary("");
                    }
                });
            }
        });
    }
}
