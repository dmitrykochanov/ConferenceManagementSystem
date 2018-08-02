package com.dmko.conferencemanagementsystem.ui.screens.signin;


import com.dmko.conferencemanagementsystem.data.user.UserRepository;
import com.dmko.conferencemanagementsystem.data.user.entities.SignInForm;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BasePresenterImpl;
import com.dmko.conferencemanagementsystem.utils.SchedulersFacade;

public class SignInPresenter extends BasePresenterImpl<SignInContract.View> implements SignInContract.Presenter {
    private UserRepository userRepository;
    private SchedulersFacade schedulers;

    public SignInPresenter(SchedulersFacade schedulers, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.schedulers = schedulers;
    }

    @Override
    public void signIn(SignInForm form) {
        getView().showLoading(true);
        addDisposable(userRepository.signIn(form)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(() -> {
                    if (isViewAttached()) {
                        getView().showLoading(false);
                        getView().showMainActivity();
                    }
                }, this::handleThrowable));
    }
}
