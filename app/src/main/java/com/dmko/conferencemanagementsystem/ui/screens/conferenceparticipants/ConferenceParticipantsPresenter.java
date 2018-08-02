package com.dmko.conferencemanagementsystem.ui.screens.conferenceparticipants;

import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.user.UserRepository;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BasePresenterImpl;
import com.dmko.conferencemanagementsystem.utils.SchedulersFacade;

import java.util.List;

import io.reactivex.Observable;

import static com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles.Roles.ADMIN;
import static com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles.Roles.CREATOR;

public class ConferenceParticipantsPresenter extends BasePresenterImpl<ConferenceParticipantsContract.View> implements ConferenceParticipantsContract.Presenter {

    private SchedulersFacade schedulers;
    private UserRepository userRepository;
    private BriefUserRoles user;
    private long conferenceId;
    private Long lastRoleLoaded;

    public ConferenceParticipantsPresenter(SchedulersFacade schedulers, UserRepository userRepository) {
        this.schedulers = schedulers;
        this.userRepository = userRepository;
    }

    @Override
    public boolean isCurrentUserConferenceAdmin() {
        if (user == null) throw new RuntimeException("User roles are not loaded yet");
        return user.getRoles().contains(CREATOR) || user.getRoles().contains(ADMIN);
    }

    @Override
    public void loadConferenceParticipants(Long conferenceId, Long role) {
        this.conferenceId = conferenceId;
        this.lastRoleLoaded = role;
        getView().showLoading(true);
        Observable<PagedList<BriefUserRoles>> usersObservable =
                role == null ? userRepository.getUsersWithRoles(conferenceId) : userRepository.getUsersWithRoles(conferenceId, role);
        Observable<BriefUserRoles> rolesObservable = userRepository.getUserWithRoles(userRepository.getUser().getId(), conferenceId);

        addDisposable(rolesObservable
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(user -> {
                    this.user = user;
                    addDisposable(usersObservable
                            .subscribeOn(schedulers.io())
                            .observeOn(schedulers.ui())
                            .subscribe(users -> {
                                if (isViewAttached()) {
                                    getView().showLoading(false);
                                    getView().setConferenceParticipants(users);
                                }
                            }, this::handleThrowable));
                }, this::handleThrowable));
    }

    @Override
    public void updateRoles(long userId, List<Long> roles) {
        addDisposable(userRepository.updateRoles(conferenceId, userId, roles)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(this::refresh, this::handleThrowable));
    }

    @Override
    public void onPickRolesSelected(long userId) {
        if (isViewAttached()) {
            getView().showPickRolesDialog(conferenceId, userId);
        }
    }

    @Override
    public void refresh() {
        clearSubscriptions();
        loadConferenceParticipants(conferenceId, lastRoleLoaded);
    }
}
