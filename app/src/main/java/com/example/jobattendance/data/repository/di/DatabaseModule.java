package com.example.jobattendance.data.repository.di;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.example.jobattendance.data.database.AttendanceDao;
import com.example.jobattendance.data.database.AttendanceDb;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    AttendanceDb provideAttendanceDb(Context context) {
        return Room.databaseBuilder(context, AttendanceDb.class, "attendance").build();
    }

    @Provides
    @Singleton
    AttendanceDao provideAttendanceDao(AttendanceDb db){
        return db.getAttendanceDao();
    }
}
