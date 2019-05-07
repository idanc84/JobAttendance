package com.example.jobattendance.location;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;

import com.example.jobattendance.broadcast.GeofenceBroadcastReceiver;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class GeoFenceMgr implements GeofenceSource {

    private static final String TAG = "GeoFenceMgr";
    private static final String FENCING_ID = "work_fencing";

    private GeofencingClient mGeofencingClient;
    private Geocoder mGeocoder;
    private Context mCtx;

    @Inject
    public GeoFenceMgr(Context ctx, GeofencingClient geofencingClient, Geocoder geocoder ) {
        mGeofencingClient = geofencingClient;
        mGeocoder = geocoder;
        mCtx = ctx;
    }

    @Override
    public Single<Boolean> addGeoFence(String addressStr, int radius){
        Address address = decodeAddress(addressStr);

        if(address == null){
            return Single.just(false);
        }

        doAddGeoFence(address.getLatitude(),address.getLongitude(),radius );

        return Single.just(true);
    }

    @SuppressLint("MissingPermission")
    private void doAddGeoFence( double lat, double longitude, int radius ){
        Geofence geofence = new Geofence.Builder()
                .setRequestId(FENCING_ID)
                .setCircularRegion(lat, longitude, radius)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT )
                .build();

        GeofencingRequest request = new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(geofence)
                .build();

        Intent intent = new Intent(mCtx, GeofenceBroadcastReceiver.class);
        intent.setAction(GeofenceBroadcastReceiver.ACTION_RECEIVE_GEOFENCE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mCtx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mGeofencingClient.addGeofences(request, pendingIntent).addOnSuccessListener(command -> {
            int succsess = 0;
        });
    }

    private Address decodeAddress(String address){
        Address coderAddress = null;

        try {
            List<Address> addressList = mGeocoder.getFromLocationName(address,1);
            if( addressList != null && addressList.size() > 0 ){
                coderAddress = addressList.get(0);
            }

        } catch (IOException e) {

        }

        return coderAddress;
    }

    @Override
    public void removeFencing(){
        ArrayList<String> list = new ArrayList();
        list.add(FENCING_ID);
        mGeofencingClient.removeGeofences(list);
    }

}
