package com.dmko.conferencemanagementsystem.ui.screens.navigation;

import com.dmko.conferencemanagementsystem.data.conferences.ConferencesRepository;
import com.dmko.conferencemanagementsystem.data.user.UserRepository;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BasePresenterImpl;
import com.dmko.conferencemanagementsystem.ui.screens.navigation.recyclerview.ConferenceItem;
import com.dmko.conferencemanagementsystem.utils.SchedulersFacade;

import timber.log.Timber;

public class NavigationPresenter extends BasePresenterImpl<NavigationContract.View> implements NavigationContract.Presenter {

    private SchedulersFacade schedulers;
    private UserRepository userRepository;
    private ConferencesRepository conferencesRepository;

    public NavigationPresenter(SchedulersFacade schedulers, UserRepository userRepository, ConferencesRepository conferencesRepository) {
        this.schedulers = schedulers;
        this.userRepository = userRepository;
        this.conferencesRepository = conferencesRepository;
    }


    @Override
    public void start() {
        checkIfLoggedIn();
    }

    @Override
    public void checkIfLoggedIn() {
        if (userRepository.isSignedIn()) {
            getView().showLoading(true);
            Timber.d("User signed in, loading conferences");
            addDisposable(conferencesRepository.getUserConferences(userRepository.getUser().getId())
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .subscribe(conferences -> {
                        Timber.d("Loaded %d conferences", conferences.size());
                        if (isViewAttached()) {
                            getView().showLoading(false);
                            getView().setupForSignedInUser(userRepository.getUser(), conferences);
                        }
                    }, this::handleThrowable));
        } else {
            Timber.d("User is not signed in");
            getView().setupForGuest();
        }
    }

    @Override
    public void logout() {
        userRepository.signOut();
        getView().setupForGuest();
    }

    @Override
    public void onItemClicked(ConferenceItem conferenceItem) {
        getView().openFragment(conferenceItem.getFragment());
    }
}
