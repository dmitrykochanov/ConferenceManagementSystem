package com.dmko.conferencemanagementsystem.ui.screens.conferencecomment;

import com.dmko.conferencemanagementsystem.data.requests.RequestsRepository;
import com.dmko.conferencemanagementsystem.data.requests.entities.ConferenceRequestComment;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BasePresenterImpl;
import com.dmko.conferencemanagementsystem.utils.SchedulersFacade;

public class ConferenceCommentPresenter extends BasePresenterImpl<ConferenceCommentContract.View> implements ConferenceCommentContract.Presenter {

    private SchedulersFacade schedulers;
    private RequestsRepository requestsRepository;
    private long conferenceRequestId;

    public ConferenceCommentPresenter(SchedulersFacade schedulers, RequestsRepository requestsRepository) {
        this.schedulers = schedulers;
        this.requestsRepository = requestsRepository;
    }

    @Override
    public void setConferenceRequestId(long conferenceRequestId) {
        this.conferenceRequestId = conferenceRequestId;
    }

    @Override
    public void addComment(ConferenceRequestComment conferenceRequestComment) {
        addDisposable(requestsRepository.addComment(conferenceRequestId, conferenceRequestComment)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(() -> {
                    if (isViewAttached()) {
                        getView().cancelDialog();
                    }
                }, this::handleThrowable));
    }
}
