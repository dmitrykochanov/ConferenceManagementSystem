package com.dmko.conferencemanagementsystem.ui.screens.conferencecomment;

import com.dmko.conferencemanagementsystem.data.requests.entities.ConferenceRequestComment;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BasePresenter;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BaseView;

public interface ConferenceCommentContract {

    interface View extends BaseView {

        void cancelDialog();
    }

    interface Presenter extends BasePresenter<View> {

        void setConferenceRequestId(long conferenceRequestId);

        void addComment(ConferenceRequestComment conferenceRequestComment);
    }
}
