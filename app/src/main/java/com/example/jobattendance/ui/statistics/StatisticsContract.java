package com.example.jobattendance.ui.statistics;

import com.example.jobattendance.data.model.Attendance;

public interface StatisticsContract {

    interface View{
       void showAttendance();
       void showError(String err);
    }

    interface Presenter{
        void getAttendance();
    }

    interface AttendanceItem{
        void bind(Attendance attendance);
    }
}
