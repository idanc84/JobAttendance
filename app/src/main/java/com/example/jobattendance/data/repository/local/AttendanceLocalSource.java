package com.example.jobattendance.data.repository.local;

import com.example.jobattendance.data.database.AttendanceDao;
import com.example.jobattendance.data.model.Attendance;
import com.example.jobattendance.data.repository.AttendanceDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class AttendanceLocalSource implements AttendanceDataSource {

    private AttendanceDao mAttendanceDao;

    @Inject
    public AttendanceLocalSource( AttendanceDao attendanceDao ) {
        mAttendanceDao = attendanceDao;
    }

    @Override
    public Attendance getLastEntry() {
        return mAttendanceDao.getLastEntry();
    }

    @Override
    public Flowable<List<Attendance>> getAttendanceReport() {
        return mAttendanceDao.getAttendanceResults();
    }

    @Override
    public void insertAttendance(Attendance attendance) {
        mAttendanceDao.insertAttendance(attendance);
    }
}
