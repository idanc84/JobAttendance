package com.example.jobattendance.data.repository;

import com.example.jobattendance.data.model.Attendance;

import java.util.List;

import io.reactivex.Flowable;

public interface AttendanceDataSource {

    Flowable<List<Attendance>> getAttendanceReport();

    void insertAttendance( Attendance attendance );

    Attendance getLastEntry();
}
