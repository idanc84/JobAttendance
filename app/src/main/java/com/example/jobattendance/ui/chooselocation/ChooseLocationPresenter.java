package com.example.jobattendance.ui.chooselocation;

import com.example.jobattendance.data.repository.Constant;
import com.example.jobattendance.data.repository.Repository;
import com.example.jobattendance.location.GeoFenceMgr;
import com.example.jobattendance.location.GeofenceSource;
import com.example.jobattendance.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChooseLocationPresenter implements BasePresenter<ChooseLocationContract.View>, ChooseLocationContract.Presenter {

    public static final String MONITORING_ATTENDANCE = "Monitoring Attendance";
    private static final String FAILURE = "Failed to add location";

    private ChooseLocationContract.View mView;
    private GeofenceSource mGeoFenceMgr;
    private Repository mRepository;
    private CompositeDisposable mBag;
    private boolean mJobAdded = false;
    private String mJobAddedMsg = null;

    @Inject
    public ChooseLocationPresenter(GeofenceSource geoFenceMgr, Repository repository) {
        mGeoFenceMgr = geoFenceMgr;
        mRepository = repository;
        mBag = new CompositeDisposable();
    }

    @Override
    public void onAttach(ChooseLocationContract.View view) {
        mView = view;
        deliverLastMsg();
    }

    @Override
    public void onDetach() {
        mView = null;
    }

    private void deliverLastMsg(){
        if( mJobAddedMsg != null ){
            mView.showJobAdded(mJobAddedMsg,mJobAdded);
            mJobAddedMsg = null;
            mJobAdded = false;
        }
    }

    @Override
    public void setJobLocation(String address, int radius) {
        Disposable disposable = mGeoFenceMgr.addGeoFence(address,radius)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(aBoolean -> {mRepository.putData(Constant.IS_MONITORING_ATTENDANCE_KEY,aBoolean.booleanValue());})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleAddGeoFence);

        mBag.add(disposable);
    }

    @Override
    public boolean isLocationMonitored() {
        return mRepository.getData(Constant.IS_MONITORING_ATTENDANCE_KEY);
    }

    @Override
    public void clearMonitoring() {
        mRepository.putData(Constant.IS_MONITORING_ATTENDANCE_KEY,false);
        mGeoFenceMgr.removeFencing();

        mView.enableSetLocation();
    }

    private void handleAddGeoFence(boolean val, Throwable t){
        String res = (val == false)||( t != null) ? FAILURE :MONITORING_ATTENDANCE;

        if(mView != null){
            mView.showJobAdded(res, val);
        }else {
            mJobAddedMsg = res;
            mJobAdded = val;
        }
    }

    @Override
    public void clear(){
        mBag.clear();
    }

}
