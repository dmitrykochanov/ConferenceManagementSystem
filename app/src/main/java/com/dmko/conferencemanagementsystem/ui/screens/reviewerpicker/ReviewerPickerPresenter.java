package com.dmko.conferencemanagementsystem.ui.screens.reviewerpicker;

import com.dmko.conferencemanagementsystem.data.submissions.SubmissionsRepository;
import com.dmko.conferencemanagementsystem.data.user.UserRepository;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BasePresenterImpl;
import com.dmko.conferencemanagementsystem.utils.SchedulersFacade;

import java.util.ArrayList;
import java.util.List;

public class ReviewerPickerPresenter extends BasePresenterImpl<ReviewerPickerContract.View> implements ReviewerPickerContract.Presenter {

    private SchedulersFacade schedulers;
    private UserRepository userRepository;
    private SubmissionsRepository submissionsRepository;
    private long submissionId;
    private List<Long> reviewersToAdd = new ArrayList<>();

    public ReviewerPickerPresenter(SchedulersFacade schedulers, UserRepository userRepository, SubmissionsRepository submissionsRepository) {
        this.schedulers = schedulers;
        this.userRepository = userRepository;
        this.submissionsRepository = submissionsRepository;
    }

    @Override
    public void loadReviewers(long conferenceId, long submissionId) {
        this.submissionId = submissionId;
        addDisposable(userRepository.getReviewers(conferenceId)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(reviewers -> {
                    if (isViewAttached()) {
                        getView().setReviewers(reviewers);
                    }
                }, this::handleThrowable));
    }

    @Override
    public void addReviewer(long reviewerId) {
        if (!reviewersToAdd.contains(reviewerId)) {
            reviewersToAdd.add(reviewerId);
        }
    }

    @Override
    public void deleteReviewer(long reviewerId) {
        reviewersToAdd.remove(reviewerId);
    }

    @Override
    public void onCancelDialogSelected() {
        if (isViewAttached()) {
            getView().cancelDialog();
        }
    }

    @Override
    public void saveReviewers() {
        addDisposable(userRepository.addReviewerToSubmission(submissionId, reviewersToAdd)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(() -> {
                    if (isViewAttached()) {
                        getView().cancelDialog();
                    }
                }, this::handleThrowable));
    }
}
