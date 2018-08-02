package com.dmko.conferencemanagementsystem.ui.screens.rolepicker;

import com.dmko.conferencemanagementsystem.data.user.UserRepository;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BasePresenterImpl;
import com.dmko.conferencemanagementsystem.utils.SchedulersFacade;

import java.util.List;

import timber.log.Timber;

public class RolePickerPresenter extends BasePresenterImpl<RolePickerContract.View> implements RolePickerContract.Presenter {

    private SchedulersFacade schedulers;
    private UserRepository userRepository;
    private long conferenceId;
    private long userId;
    private List<Long> roles;

    public RolePickerPresenter(SchedulersFacade schedulers, UserRepository userRepository) {
        this.schedulers = schedulers;
        this.userRepository = userRepository;
    }

    @Override
    public void loadUser(long conferenceId, long userId) {
        Timber.d("Loading user for conferenceId = %d, userId = %d", conferenceId, userId);
        this.conferenceId = conferenceId;
        this.userId = userId;
        getView().showLoading(true);
        addDisposable(userRepository.getUserWithRoles(userId, conferenceId)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(user -> {
                    if (isViewAttached()) {
                        this.roles = user.getRoles();
                        getView().showLoading(false);
                        getView().setUser(user);
                    }
                }, this::handleThrowable));
    }

    @Override
    public void addRole(Long role) {
        if (!roles.contains(role)) {
            Timber.d("Role %d added, current roles: %s", role, roles);
            roles.add(role);
        }
    }

    @Override
    public void deleteRole(Long role) {
        Timber.d("Role %d removed, current roles: %s", role, roles);
        roles.remove(role);
    }

    @Override
    public void saveRoles() {
        addDisposable(userRepository.updateRoles(conferenceId, userId, roles)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(() -> {
                    if (isViewAttached()) {
                        getView().cancelDialog();
                    }
                }, this::handleThrowable));
    }
}
