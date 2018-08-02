package com.dmko.conferencemanagementsystem.ui.screens.conferenceparticipants;

import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BasePresenter;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BaseView;

import java.util.List;

public interface ConferenceParticipantsContract {
    interface View extends BaseView {

        void showLoading(boolean isLoading);

        void setConferenceParticipants(PagedList<BriefUserRoles> users);

        void showPickRolesDialog(long conferenceId, long userId);
    }

    interface Presenter extends BasePresenter<View> {

        boolean isCurrentUserConferenceAdmin();

        void loadConferenceParticipants(Long conferenceId, Long role);

        void updateRoles(long userId, List<Long> roles);

        void onPickRolesSelected(long userId);

        void refresh();
    }
}