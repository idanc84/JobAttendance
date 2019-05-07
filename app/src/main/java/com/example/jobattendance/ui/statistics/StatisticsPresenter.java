package com.example.jobattendance.ui.statistics;

import com.example.jobattendance.data.model.Attendance;
import com.example.jobattendance.data.repository.Repository;
import com.example.jobattendance.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StatisticsPresenter implements BasePresenter<StatisticsContract.View>, StatisticsContract.Presenter {

    private Repository mRepository;
    private CompositeDisposable mBag;
    private StatisticsContract.View mView;
    private List<Attendance> mAttendance;

    private static final String ERROR = "No attendance to show";

    @Inject
    public StatisticsPresenter(Repository repository) {
        mRepository = repository;
        mBag = new CompositeDisposable();
    }

    @Override
    public void onAttach(StatisticsContract.View view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        mView = null;
    }

    @Override
    public void clear() {
        mBag.clear();
    }

    @Override
    public void getAttendance() {
        if( mAttendance != null && mAttendance.size() > 0 ){
            mView.showAttendance();
        }else {
            doGetAttendance();
        }
    }

    private void doGetAttendance(){
        Disposable disposable = mRepository.getAttendanceReport()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleAttendanceResults);

        mBag.add(disposable);
    }

    private void handleAttendanceResults(List<Attendance> attendances){
        if( attendances != null && attendances.size()>0){
            mAttendance = attendances;
            if( mView != null ){
                mView.showAttendance();
            }
        }
        else{
            mView.showError(ERROR);
        }
    }

    public int getItemsSize(){
        return (mAttendance!=null)? mAttendance.size() : 0 ;
    }

    public void bindAttendanceItemView( int i, StatisticsContract.AttendanceItem view ) {
        Attendance attendance = mAttendance.get(i);

        view.bind(attendance);
    }
}
