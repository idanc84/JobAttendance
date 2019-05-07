package com.example.jobattendance.ui.base;

public interface BasePresenter<V> {
    void onAttach(V view);
    void onDetach();
    void clear();
}