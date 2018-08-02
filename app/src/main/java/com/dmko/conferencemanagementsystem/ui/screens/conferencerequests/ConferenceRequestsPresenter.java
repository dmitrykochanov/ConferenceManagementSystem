package com.dmko.conferencemanagementsystem.ui.screens.conferencerequests;

import com.dmko.conferencemanagementsystem.data.requests.RequestsRepository;
import com.dmko.conferencemanagementsystem.data.user.UserRepository;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BasePresenterImpl;
import com.dmko.conferencemanagementsystem.utils.SchedulersFacade;

public class ConferenceRequestsPresenter extends BasePresenterImpl<ConferenceRequestsContract.View> implements ConferenceRequestsContract.Presenter {

    private SchedulersFacade schedulers;
    private RequestsRepository requestsRepository;

    public ConferenceRequestsPresenter(SchedulersFacade schedulers, RequestsRepository requestsRepository) {
        this.schedulers = schedulers;
        this.requestsRepository = requestsRepository;
    }

    @Override
    public void loadConferenceRequests() {
        getView().showLoading(true);
        addDisposable(requestsRepository.getConferenceRequests()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(conferenceRequests -> {
                    if (isViewAttached()) {
                        getView().showLoading(false);
                        getView().setConferenceRequests(conferenceRequests);
                    }
                }, this::handleThrowable));
    }

    @Override
    public void onOpenConferenceRequestSelected(Long conferenceRequestId) {
        if (isViewAttached()) {
            getView().openConferenceRequest(conferenceRequestId);
        }
    }
}
