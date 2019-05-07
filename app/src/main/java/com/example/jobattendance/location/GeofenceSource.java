package com.example.jobattendance.location;

import io.reactivex.Single;

public interface GeofenceSource {
    public Single<Boolean> addGeoFence(String addressStr, int radius);
    public void removeFencing();
}
