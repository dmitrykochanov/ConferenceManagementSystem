package com.dmko.conferencemanagementsystem.ui.screens.conferences;


import com.dmko.conferencemanagementsystem.data.conferences.ConferencesRepository;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BasePresenterImpl;
import com.dmko.conferencemanagementsystem.utils.SchedulersFacade;

public class ConferencesPresenter extends BasePresenterImpl<ConferencesContract.View> implements ConferencesContract.Presenter {
    private SchedulersFacade schedulers;
    private ConferencesRepository conferencesRepository;

    public ConferencesPresenter(SchedulersFacade schedulers, ConferencesRepository conferencesRepository) {
        this.schedulers = schedulers;
        this.conferencesRepository = conferencesRepository;
    }

    @Override
    public void loadConferences() {
        getView().showLoading(true);
        addDisposable(conferencesRepository.getAllConferences()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(conferences -> {
                    if (isViewAttached()) {
                        getView().setConferences(conferences);
                        getView().showLoading(false);
                    }
                }, this::handleThrowable));
    }
}
