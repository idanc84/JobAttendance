package com.example.jobattendance.location;

import android.content.Context;
import android.location.Geocoder;

import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GeoFenceModule {

    @Singleton
    @Provides
    public static GeofencingClient provideGeoFencingClient(Context ctx){
        return LocationServices.getGeofencingClient(ctx);
    }

    @Singleton
    @Provides
    public static Geocoder provideGeoCoder(Context ctx){
        return new Geocoder(ctx);
    }

    @Provides
    @Singleton
    public static GeofenceSource provideGeofenceSource(GeoFenceMgr geoFenceMgr) {
        return geoFenceMgr;
    }
}
