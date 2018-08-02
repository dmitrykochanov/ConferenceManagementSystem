package com.dmko.conferencemanagementsystem.ui.screens.conferences;


import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.conferences.entities.BriefConference;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BasePresenter;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BaseView;

public interface ConferencesContract {
    interface View extends BaseView {
        void setConferences(PagedList<BriefConference> conferences);

        void showLoading(boolean isLoading);
    }

    interface Presenter extends BasePresenter<ConferencesContract.View> {
        void loadConferences();
    }
}
