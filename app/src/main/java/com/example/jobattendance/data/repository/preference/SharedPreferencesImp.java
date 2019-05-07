package com.example.jobattendance.data.repository.preference;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class SharedPreferencesImp implements SharedPreferenceSource {

    SharedPreferences mSharedPreferences;

    @Inject
    public SharedPreferencesImp(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    @Override
    public void putData(String key, boolean val) {
        mSharedPreferences.edit().putBoolean(key,val).apply();
    }

    @Override
    public boolean getData(String key) {
        return mSharedPreferences.getBoolean(key,false);
    }
}
