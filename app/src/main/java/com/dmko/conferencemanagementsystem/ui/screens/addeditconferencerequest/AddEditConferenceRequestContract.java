package com.dmko.conferencemanagementsystem.ui.screens.addeditconferencerequest;

import com.dmko.conferencemanagementsystem.data.requests.entities.ConferenceRequest;
import com.dmko.conferencemanagementsystem.data.user.entities.User;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BasePresenter;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BaseView;

public interface AddEditConferenceRequestContract {

    interface View extends BaseView {

        void setupForExisting(ConferenceRequest conferenceRequest);

        void setupForNew();

        void showEvaluateConferenceRequestDialog(long conferenceRequestId);

        void showConferenceRequests();
    }

    interface Presenter extends BasePresenter<View> {

        boolean isCurrentUserGlobalAdmin();

        User getCurrentUser();

        void loadConferenceRequest(Long conferenceRequestId);

        void saveConferenceRequest(ConferenceRequest conferenceRequest);

        void onEvaluateConferenceRequestSelected();

        void refresh();
    }
}
