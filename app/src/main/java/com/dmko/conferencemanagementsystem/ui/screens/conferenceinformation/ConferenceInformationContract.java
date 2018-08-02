package com.dmko.conferencemanagementsystem.ui.screens.conferenceinformation;

import com.dmko.conferencemanagementsystem.data.conferences.entities.Conference;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BasePresenter;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BaseView;

public interface ConferenceInformationContract {

    interface View extends BaseView {

        void showLoading(boolean isLoading);

        void setConference(Conference conference);
    }

    interface Presenter extends BasePresenter<View> {

        void loadConference(long conferenceId);
    }
}