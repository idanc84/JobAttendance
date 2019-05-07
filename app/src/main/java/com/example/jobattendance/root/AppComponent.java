package com.example.jobattendance.root;

import android.content.Context;

import com.example.jobattendance.data.repository.Repository;
import com.example.jobattendance.data.repository.di.DatabaseModule;
import com.example.jobattendance.data.repository.di.RepositoryModule;
import com.example.jobattendance.data.repository.di.SharedPreferencesModule;
import com.example.jobattendance.location.GeoFenceModule;
import com.example.jobattendance.location.GeofenceSource;
import com.example.jobattendance.service.GeofenceService;

import javax.inject.Singleton;
import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {RepositoryModule.class, DatabaseModule.class,GeoFenceModule.class,SharedPreferencesModule.class})
public interface AppComponent {
    Repository provideRepository();
    GeofenceSource provideGeoFenceMgr();

    void inject(GeofenceService service);

    @Component.Builder
    interface Builder {
        AppComponent build();

        @BindsInstance
        Builder application(Context application);
    }
}
