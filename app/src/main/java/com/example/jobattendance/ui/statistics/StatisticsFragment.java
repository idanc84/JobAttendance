package com.example.jobattendance.ui.statistics;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.jobattendance.R;
import com.example.jobattendance.root.AttendanceApp;
import com.example.jobattendance.ui.base.BaseViewModel;
import com.example.jobattendance.ui.di.DaggerFragmentComponent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment implements StatisticsContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Inject
    public StatisticsPresenter mPresenter;
    private StatisticsAdapter mStatisticsAdapter;

    public StatisticsFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StatisticsFragment.
     */
    public static StatisticsFragment newInstance() {
        StatisticsFragment fragment = new StatisticsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    private void initViews(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mStatisticsAdapter = new StatisticsAdapter(getContext(),mPresenter);
        mRecyclerView.setAdapter(mStatisticsAdapter);
    }

    private void initPresenter(){
        BaseViewModel viewModel = ViewModelProviders.of(this).get(BaseViewModel.class);
        mPresenter = (StatisticsPresenter)viewModel.getPresenter();

        if( mPresenter == null ){
            DaggerFragmentComponent
                    .builder()
                    .appComponent(AttendanceApp.getAppComponent())
                    .build()
                    .inject(this);

            viewModel.setPresenter(mPresenter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, view);

        initViews();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onAttach(this);
        mPresenter.getAttendance();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onDetach();
    }

    @Override
    public void showError(String err) {
        Toast.makeText(getContext(),err,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showAttendance() {
        mStatisticsAdapter.notifyDataSetChanged();
    }
}
