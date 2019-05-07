package com.example.jobattendance.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.jobattendance.service.GeofenceService;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION_RECEIVE_GEOFENCE = "com.example.jobattendence.ACTION_RECEIVE_GEOFENCE";

    @Override
    public void onReceive(Context context, Intent intent) {
        GeofenceService.enqueueWork(context,intent);
    }
}
