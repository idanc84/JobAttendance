package com.example.jobattendance.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

    public static String getCurrentDate(){
        String currentDate = sdf.format(new Date());

        return currentDate;
    }

    public static long getCurrentTimeInMillis(){
        Date date = new Date();

        return date.getTime();
    }

    public static String msToString(long ms) {
        long totalSecs = ms/1000;
        long hours = (totalSecs / 3600);
        long mins = (totalSecs / 60) % 60;
        long secs = totalSecs % 60;
        String minsString = (mins == 0)
                ? "00"
                : ((mins < 10)
                ? "0" + mins
                : "" + mins);
        String secsString = (secs == 0)
                ? "00"
                : ((secs < 10)
                ? "0" + secs
                : "" + secs);
        if (hours > 0)
            return hours + ":" + minsString + ":" + secsString;
        else if (mins > 0)
            return mins + ":" + secsString;
        else return ":" + secsString;
    }

}
