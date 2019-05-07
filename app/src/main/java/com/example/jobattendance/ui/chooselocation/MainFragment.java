package com.example.jobattendance.ui.chooselocation;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobattendance.R;
import com.example.jobattendance.location.GeoFenceMgr;
import com.example.jobattendance.root.AttendanceApp;
import com.example.jobattendance.ui.base.BaseViewModel;
import com.example.jobattendance.ui.di.DaggerFragmentComponent;
import com.example.jobattendance.ui.statistics.StatisticsActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements ChooseLocationContract.View {

    @BindView(R.id.textView_headline)
    TextView mHeadLine;
    @BindView(R.id.editText_address)
    EditText mAddressText;
    @BindView(R.id.textView_radius)
    TextView mRadiusText;
    @BindView(R.id.seekBar)
    SeekBar mSeekBar;
    @BindView(R.id.button_add_location)
    Button mAddLocationBtn;
    @BindView(R.id.add_location_container)
    LinearLayout mContainer;
    @BindView(R.id.btn_clear)
    Button mClear;
    @BindView(R.id.btn_open_stat)
    Button mOpenStatistics;

    @Inject
    public ChooseLocationPresenter mPresenter;

    public MainFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    GeoFenceMgr locationMgr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        initPresenter();
    }

    private void initPresenter(){
        BaseViewModel viewModel = ViewModelProviders.of(this).get(BaseViewModel.class);
        mPresenter = (ChooseLocationPresenter)viewModel.getPresenter();

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
        View view = inflater.inflate(R.layout.fragment_main_1, container, false);
        ButterKnife.bind(this,view);
        initViews();

        return view;
    }

    @Override
    public void enableSetLocation() {
        doEnableSetLocation();
    }

    private void doEnableSetLocation(){
        mHeadLine.setText(R.string.add_job_location);
        mContainer.setVisibility(View.VISIBLE);
        initSeekBar();
        initAddLocation();
    }

    private void initViews(){
        if(mPresenter.isLocationMonitored()){
            mContainer.setVisibility(View.INVISIBLE);
            mHeadLine.setText(ChooseLocationPresenter.MONITORING_ATTENDANCE);
        }else {
            doEnableSetLocation();
        }

        mClear.setOnClickListener(v -> {
            mPresenter.clearMonitoring();
        });

        mOpenStatistics.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),StatisticsActivity.class);
            startActivity(intent);
        });
    }

    private void initAddLocation(){
        mAddLocationBtn.setOnClickListener( v -> {
            try{
                String radius = mRadiusText.getText().toString();
                String address = mAddressText.getText().toString();

                if( radius == null || address == null ){
                    Toast.makeText(getContext(),"Please insert all fields",Toast.LENGTH_LONG).show();
                }
                else {
                    mPresenter.setJobLocation(address,Integer.parseInt(radius));
                }
            }catch (Exception e){
                Toast.makeText(getContext(),"Please insert a valid value", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void showJobAdded(String res, boolean added) {
        if(added){
            mContainer.setVisibility(View.INVISIBLE);
            mHeadLine.setText(res);
        }

        Toast.makeText(getContext(),res,Toast.LENGTH_LONG).show();
    }

    private void initSeekBar(){
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mRadiusText.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onAttach(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onDetach();
    }



    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private void checkPermission(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //debug...
            }
            else{
                //close app
            }
        }
    }

}
