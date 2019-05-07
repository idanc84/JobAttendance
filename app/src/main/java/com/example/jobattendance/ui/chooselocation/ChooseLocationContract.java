package com.example.jobattendance.ui.chooselocation;

public interface ChooseLocationContract  {

    interface View{
        void showJobAdded(String msg, boolean added );
        void enableSetLocation();
    }

    interface Presenter{
        void setJobLocation(String address, int radius);
        boolean isLocationMonitored();
        void clearMonitoring();
    }
}
