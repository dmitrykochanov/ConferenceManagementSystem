package com.dmko.conferencemanagementsystem.ui.screens.conferencerequests;

import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.requests.entities.BriefConferenceRequest;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BasePresenter;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BaseView;

public interface ConferenceRequestsContract {

    interface View extends BaseView {

        void showLoading(boolean isLoading);

        void setConferenceRequests(PagedList<BriefConferenceRequest> conferenceRequests);

        void openConferenceRequest(Long conferenceRequestId);
    }

    interface Presenter extends BasePresenter<View> {

        void loadConferenceRequests();

        void onOpenConferenceRequestSelected(Long conferenceRequestId);
    }
}
