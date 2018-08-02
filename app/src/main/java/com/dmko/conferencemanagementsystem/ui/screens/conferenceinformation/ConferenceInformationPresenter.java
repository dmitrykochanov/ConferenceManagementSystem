package com.dmko.conferencemanagementsystem.ui.screens.conferenceinformation;

import com.dmko.conferencemanagementsystem.data.conferences.ConferencesRepository;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BasePresenterImpl;
import com.dmko.conferencemanagementsystem.utils.RetrofitException;
import com.dmko.conferencemanagementsystem.utils.SchedulersFacade;

public class ConferenceInformationPresenter extends BasePresenterImpl<ConferenceInformationContract.View> implements ConferenceInformationContract.Presenter {

    private SchedulersFacade schedulers;
    private ConferencesRepository conferencesRepository;

    public ConferenceInformationPresenter(SchedulersFacade schedulers, ConferencesRepository conferencesRepository) {
        this.schedulers = schedulers;
        this.conferencesRepository = conferencesRepository;
    }

    @Override
    public void loadConference(long conferenceId) {
        getView().showLoading(true);
        addDisposable(conferencesRepository.getConference(conferenceId)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(conference -> {
                    if (isViewAttached()) {
                        getView().showLoading(false);
                        getView().setConference(conference);
                    }
                }, this::handleThrowable));
    }
}
