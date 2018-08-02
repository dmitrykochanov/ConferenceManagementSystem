package com.dmko.conferencemanagementsystem.ui.base.mvp;


public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    void detachView();

    void clearSubscriptions();

    void handleThrowable(Throwable throwable);
}
