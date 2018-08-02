package com.dmko.conferencemanagementsystem.ui.screens.signin;


import com.dmko.conferencemanagementsystem.data.user.entities.SignInForm;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BasePresenter;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BaseView;

public interface SignInContract {
    interface View extends BaseView {

        void showLoading(boolean isLoading);

        void showMainActivity();

        void showIncorrectEmailOrPasswordError();
    }

    interface Presenter extends BasePresenter<SignInContract.View> {
        void signIn(SignInForm form);
    }
}
