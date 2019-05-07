package com.example.jobattendance.service;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;


import com.example.jobattendance.data.model.Attendance;
import com.example.jobattendance.data.repository.Repository;
import com.example.jobattendance.root.AttendanceApp;
import com.example.jobattendance.utils.TimeUtils;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import javax.inject.Inject;

public class GeofenceService extends JobIntentService {

    private static final String TAG = "GeofenceService";
    private static final int JOB_ID = 1;

    @Inject
    Repository mRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        AttendanceApp.getAppComponent().inject(this);
    }

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, GeofenceService.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            int err = geofencingEvent.getErrorCode();
            Log.e(TAG, "Error id " + err);
            return;
        }

        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            Attendance newAttendance = new Attendance();
            newAttendance.setStartTime(TimeUtils.getCurrentTimeInMillis());
            newAttendance.setDate(TimeUtils.getCurrentDate());
            newAttendance.setTransition(Geofence.GEOFENCE_TRANSITION_ENTER);

            mRepository.insertAttendance(newAttendance);

        }else if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT){
            Attendance attendance = mRepository.getLastEntry();

            attendance.setTransition(Geofence.GEOFENCE_TRANSITION_EXIT);
            attendance.setEndTime(TimeUtils.getCurrentTimeInMillis());
            attendance.setTotalTime((int)(attendance.getEndTime() - attendance.getStartTime()));

            mRepository.insertAttendance(attendance);
        }
    }
}
