package com.example.jobattendance.ui.statistics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jobattendance.R;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.frame_container,StatisticsFragment.newInstance(),"statistics").commit();
        }
    }
}
