package com.dmko.conferencemanagementsystem.ui.screens.navigation;

import android.support.v4.app.Fragment;

import com.dmko.conferencemanagementsystem.data.conferences.entities.BriefConference;
import com.dmko.conferencemanagementsystem.data.user.entities.User;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BasePresenter;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BaseView;
import com.dmko.conferencemanagementsystem.ui.screens.navigation.recyclerview.ConferenceItem;

import java.util.List;

public interface NavigationContract {
    interface View extends BaseView {

        void setupForGuest();

        void setupForSignedInUser(User user, List<BriefConference> conferences);

        void openFragment(Fragment fragment);

        void showLoading(boolean isLoading);
    }

    interface Presenter extends BasePresenter<View> {

        void start();

        void checkIfLoggedIn();

        void logout();

        void onItemClicked(ConferenceItem conferenceItem);
    }
}