package com.example.jobattendance.ui.di;

import com.example.jobattendance.root.AppComponent;
import com.example.jobattendance.ui.base.ActivityScope;
import com.example.jobattendance.ui.chooselocation.MainFragment;
import com.example.jobattendance.ui.statistics.StatisticsFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface FragmentComponent {
    void inject(MainFragment fragment);
    void inject(StatisticsFragment fragment);
}
