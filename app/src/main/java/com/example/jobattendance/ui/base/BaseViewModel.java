package com.example.jobattendance.ui.base;

import android.arch.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {

    private BasePresenter mPresenter;

    public void setPresenter( BasePresenter p )
    {
        mPresenter = p;
    }

    public BasePresenter getPresenter()
    {
        return mPresenter;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mPresenter.clear();
        mPresenter = null;
    }
}
