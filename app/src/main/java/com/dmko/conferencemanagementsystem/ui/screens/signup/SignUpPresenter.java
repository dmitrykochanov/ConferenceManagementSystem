package com.dmko.conferencemanagementsystem.ui.screens.signup;


import com.dmko.conferencemanagementsystem.data.user.UserRepository;
import com.dmko.conferencemanagementsystem.data.user.entities.SignUpForm;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BasePresenterImpl;
import com.dmko.conferencemanagementsystem.utils.SchedulersFacade;

public class SignUpPresenter extends BasePresenterImpl<SignUpContract.View> implements SignUpContract.Presenter {
    private SchedulersFacade schedulers;
    private UserRepository userRepository;

    public SignUpPresenter(SchedulersFacade schedulers, UserRepository userRepository) {
        this.schedulers = schedulers;
        this.userRepository = userRepository;
    }

    @Override
    public void signUp(SignUpForm form) {
        getView().showLoading(true);
        addDisposable(userRepository.signUp(form)
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
