package com.example.jobattendance.data.repository;

import com.example.jobattendance.data.model.Attendance;
import com.example.jobattendance.data.repository.preference.SharedPreferenceSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class Repository implements AttendanceDataSource, SharedPreferenceSource {

    private AttendanceDataSource mLocalSource;
    private SharedPreferenceSource mSharedPreferenceSource;

    @Inject
    public Repository(AttendanceDataSource localSource, SharedPreferenceSource sharedPreferenceSource) {
        mLocalSource = localSource;
        mSharedPreferenceSource = sharedPreferenceSource;
    }

    @Override
    public Attendance getLastEntry() {
        return mLocalSource.getLastEntry();
    }

    @Override
    public Flowable<List<Attendance>> getAttendanceReport() {
        return mLocalSource.getAttendanceReport();
    }

    @Override
    public void insertAttendance(Attendance attendance) {
        mLocalSource.insertAttendance(attendance);
    }

    @Override
    public void putData(String key, boolean val) {
        mSharedPreferenceSource.putData(key,val);
    }

    @Override
    public boolean getData(String key) {
        return mSharedPreferenceSource.getData(key);
    }
}
