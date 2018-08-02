package com.dmko.conferencemanagementsystem.ui.screens.signup;


import com.dmko.conferencemanagementsystem.data.user.entities.SignUpForm;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BasePresenter;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BaseView;

public interface SignUpContract {
    interface View extends BaseView {

        void showLoading(boolean isLoading);

        void showMainActivity();
    }

    interface Presenter extends BasePresenter<SignUpContract.View> {
        void signUp(SignUpForm form);
    }
}
