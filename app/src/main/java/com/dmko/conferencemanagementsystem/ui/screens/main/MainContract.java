package com.dmko.conferencemanagementsystem.ui.screens.main;


import com.dmko.conferencemanagementsystem.ui.base.mvp.BasePresenter;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BaseView;

public interface MainContract {
    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<MainContract.View> {
    }
}
