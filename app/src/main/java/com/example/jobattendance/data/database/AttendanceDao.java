package com.example.jobattendance.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.jobattendance.data.model.Attendance;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface AttendanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAttendance(Attendance attendance);

    @Query("SELECT * FROM attendance WHERE id = (SELECT MAX(id) FROM attendance) ")
    Attendance getLastEntry();

    @Query("SELECT * FROM attendance WHERE transition == 2 ")
    Flowable<List<Attendance>> getAttendanceResults();
}
