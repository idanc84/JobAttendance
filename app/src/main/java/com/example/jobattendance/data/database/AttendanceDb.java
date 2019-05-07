package com.example.jobattendance.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.example.jobattendance.data.model.Attendance;

@Database(entities = Attendance.class, version = 1)
public abstract class AttendanceDb extends RoomDatabase {
    public abstract AttendanceDao getAttendanceDao();
}
