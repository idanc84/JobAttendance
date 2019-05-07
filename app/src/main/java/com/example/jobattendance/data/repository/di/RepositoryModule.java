package com.example.jobattendance.data.repository.di;
import com.example.jobattendance.data.repository.preference.SharedPreferenceSource;
import com.example.jobattendance.data.repository.preference.SharedPreferencesImp;
import com.example.jobattendance.data.repository.AttendanceDataSource;
import com.example.jobattendance.data.repository.local.AttendanceLocalSource;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public static AttendanceDataSource provideLocalSource(AttendanceLocalSource attendanceLocalSource ) {
        return attendanceLocalSource;
    };

    @Provides
    @Singleton
    public static SharedPreferenceSource provideSharedLocalSource(SharedPreferencesImp sharedLocalSource) {
        return sharedLocalSource;
    };
}
