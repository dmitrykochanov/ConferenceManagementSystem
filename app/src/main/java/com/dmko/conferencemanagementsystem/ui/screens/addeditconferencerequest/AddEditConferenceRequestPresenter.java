package com.dmko.conferencemanagementsystem.ui.screens.addeditconferencerequest;

import com.dmko.conferencemanagementsystem.data.requests.RequestsRepository;
import com.dmko.conferencemanagementsystem.data.requests.entities.ConferenceRequest;
import com.dmko.conferencemanagementsystem.data.user.UserRepository;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles;
import com.dmko.conferencemanagementsystem.data.user.entities.User;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BasePresenterImpl;
import com.dmko.conferencemanagementsystem.utils.SchedulersFacade;

public class AddEditConferenceRequestPresenter extends BasePresenterImpl<AddEditConferenceRequestContract.View> implements AddEditConferenceRequestContract.Presenter {

    private SchedulersFacade schedulers;
    private UserRepository userRepository;
    private RequestsRepository requestsRepository;
    private Long conferenceRequestId;

    public AddEditConferenceRequestPresenter(SchedulersFacade schedulers, UserRepository userRepository, RequestsRepository requestsRepository) {
        this.schedulers = schedulers;
        this.userRepository = userRepository;
        this.requestsRepository = requestsRepository;
    }

    @Override
    public boolean isCurrentUserGlobalAdmin() {
        return userRepository.getUser().isAdmin();
    }

    @Override
    public User getCurrentUser() {
        return userRepository.getUser();
    }

    @Override
    public void loadConferenceRequest(Long conferenceRequestId) {
        this.conferenceRequestId = conferenceRequestId;
        if (conferenceRequestId == null) {
            getView().setupForNew();
        } else {
            addDisposable(requestsRepository.getConferenceRequest(conferenceRequestId)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .subscribe(conferenceRequest -> {
                        if (isViewAttached()) {
                            getView().setupForExisting(conferenceRequest);
                        }
                    }, this::handleThrowable));
        }
    }

    @Override
    public void saveConferenceRequest(ConferenceRequest conferenceRequest) {
        if (conferenceRequestId == null) {
            conferenceRequest.setOrganizer(userRepository.getUser());
            addDisposable(requestsRepository.createConferenceRequest(conferenceRequest)
                    .subscribeOn(schedulers.io())
                    .subscribe(() -> {
                        if (isViewAttached()) {
                            getView().showConferenceRequests();
                        }
                    }, this::handleThrowable));
        } else {
            conferenceRequest.setId(conferenceRequestId);
            conferenceRequest.setOrganizer(userRepository.getUser());
            addDisposable(requestsRepository.updateConferenceRequest(conferenceRequest)
                    .subscribeOn(schedulers.io())
                    .subscribe(this::refresh, this::handleThrowable));
        }
    }

    @Override
    public void onEvaluateConferenceRequestSelected() {
        if (isViewAttached()) {
            getView().showEvaluateConferenceRequestDialog(conferenceRequestId);
        }
    }

    @Override
    public void refresh() {
        clearSubscriptions();
        loadConferenceRequest(conferenceRequestId);
    }
}
