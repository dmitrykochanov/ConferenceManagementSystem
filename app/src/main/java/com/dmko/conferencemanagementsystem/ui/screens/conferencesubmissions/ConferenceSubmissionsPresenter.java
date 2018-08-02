package com.dmko.conferencemanagementsystem.ui.screens.conferencesubmissions;

import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.submissions.SubmissionsRepository;
import com.dmko.conferencemanagementsystem.data.submissions.entities.BriefSubmission;
import com.dmko.conferencemanagementsystem.data.user.UserRepository;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BasePresenterImpl;
import com.dmko.conferencemanagementsystem.utils.SchedulersFacade;

import io.reactivex.Observable;

import static com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles.Roles.ADMIN;
import static com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles.Roles.CREATOR;

public class ConferenceSubmissionsPresenter extends BasePresenterImpl<ConferenceSubmissionsContract.View> implements ConferenceSubmissionsContract.Presenter {

    private SchedulersFacade schedulers;
    private UserRepository userRepository;
    private SubmissionsRepository submissionsRepository;
    private BriefUserRoles user;
    private long conferenceId;


    public ConferenceSubmissionsPresenter(SchedulersFacade schedulers, UserRepository userRepository, SubmissionsRepository submissionsRepository) {
        this.schedulers = schedulers;
        this.userRepository = userRepository;
        this.submissionsRepository = submissionsRepository;
    }

    @Override
    public boolean isCurrentUserConferenceAdmin() {
        if (user == null) throw new RuntimeException("User roles are not loaded yet");
        return user.getRoles().contains(CREATOR) || user.getRoles().contains(ADMIN);
    }

    @Override
    public void loadAllSubmissions(long conferenceId) {
        loadSubmissions(conferenceId, submissionsRepository.getSubmissions(conferenceId));
    }

    @Override
    public void loadUserSubmissions(long conferenceId) {
        loadSubmissions(conferenceId, submissionsRepository.getUserSubmissions(conferenceId, userRepository.getUser().getId()));
    }

    @Override
    public void loadReviewerSubmissions(long conferenceId) {
        loadSubmissions(conferenceId, submissionsRepository.getReviewerSubmissions(conferenceId, userRepository.getUser().getId()));
    }

    private void loadSubmissions(long conferenceId, Observable<PagedList<BriefSubmission>> submissionsObservable) {
        this.conferenceId = conferenceId;
        getView().showLoading(true);

        Observable<BriefUserRoles> rolesObservable = userRepository.getUserWithRoles(userRepository.getUser().getId(), conferenceId);

        addDisposable(rolesObservable
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(user -> {
                    this.user = user;
                    addDisposable(submissionsObservable
                            .subscribeOn(schedulers.io())
                            .observeOn(schedulers.ui())
                            .subscribe(submissions -> {
                                if (isViewAttached()) {
                                    getView().showLoading(false);
                                    getView().setSubmissions(submissions);
                                }
                            }, this::handleThrowable));
                }, this::handleThrowable));
    }

    @Override
    public void onOpenSubmissionSelected(long submissionId) {
        if (isViewAttached()) {
            getView().openSubmission(conferenceId, submissionId);
        }
    }

    @Override
    public void onPickReviewerSelected(long submissionId) {
        if (isViewAttached()) {
            getView().openPickReviewerDialog(conferenceId, submissionId);
        }
    }

    @Override
    public void deleteReviewer(long submissionId, long reviewerId) {
        addDisposable(userRepository.deleteReviewerFromSubmission(submissionId, reviewerId)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(this::refresh, this::handleThrowable));
    }

    @Override
    public void refresh() {
        clearSubscriptions();
        loadAllSubmissions(conferenceId);
    }
}
