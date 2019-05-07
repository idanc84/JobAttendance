package com.example.jobattendance.root;

import android.app.Application;


public class AttendanceApp extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = null;//.builder().application(this).build();
    }

    public static AppComponent getAppComponent(){
        return appComponent;
    }

}
