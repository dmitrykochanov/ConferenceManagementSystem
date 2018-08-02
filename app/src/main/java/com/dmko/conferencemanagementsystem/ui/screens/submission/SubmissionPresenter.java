package com.dmko.conferencemanagementsystem.ui.screens.submission;

import com.dmko.conferencemanagementsystem.data.reviews.ReviewsRepository;
import com.dmko.conferencemanagementsystem.data.submissions.SubmissionsRepository;
import com.dmko.conferencemanagementsystem.data.submissions.entities.Submission;
import com.dmko.conferencemanagementsystem.data.user.UserRepository;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUser;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BasePresenterImpl;
import com.dmko.conferencemanagementsystem.utils.SchedulersFacade;

import static com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles.Roles.ADMIN;
import static com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles.Roles.CREATOR;

public class SubmissionPresenter extends BasePresenterImpl<SubmissionContract.View> implements SubmissionContract.Presenter {

    private SchedulersFacade schedulers;
    private UserRepository userRepository;
    private SubmissionsRepository submissionsRepository;
    private ReviewsRepository reviewsRepository;
    private BriefUserRoles user;
    private Submission submission;
    private long submissionId;
    private Long conferenceId;

    public SubmissionPresenter(SchedulersFacade schedulers, UserRepository userRepository, SubmissionsRepository submissionsRepository, ReviewsRepository reviewsRepository) {
        this.schedulers = schedulers;
        this.userRepository = userRepository;
        this.submissionsRepository = submissionsRepository;
        this.reviewsRepository = reviewsRepository;
    }

    @Override
    public boolean isCurrentUserConferenceAdmin() {
        if (user == null) throw new RuntimeException("User roles are not loaded yet");
        return user.getRoles().contains(CREATOR) || user.getRoles().contains(ADMIN);
    }

    @Override
    public boolean isCurrentUserSubmissionAuthor() {
        return submission.getAuthor().getId() == userRepository.getUser().getId();
    }

    @Override
    public boolean isCurrentUserSubmissionReviewer() {
        long userId = userRepository.getUser().getId();
        for (BriefUser reviewer : submission.getReviewers()) {
            if (reviewer.getId() == userId) return true;
        }
        return false;
    }

    @Override
    public void loadSubmission(long conferenceId, long submissionId) {
        this.submissionId = submissionId;
        this.conferenceId = conferenceId;
        addDisposable(userRepository.getUserWithRoles(userRepository.getUser().getId(), conferenceId)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(user -> {
                    this.user = user;
                    addDisposable(submissionsRepository.getSubmission(submissionId)
                            .subscribeOn(schedulers.io())
                            .observeOn(schedulers.ui())
                            .subscribe(submission -> {
                                if (isViewAttached()) {
                                    this.submission = submission;
                                    getView().setSubmission(submission);
                                }
                            }, this::handleThrowable));
                }));
    }

    @Override
    public void requestReview() {
        addDisposable(submissionsRepository.setSubmissionReviewable(submissionId)
                .subscribeOn(schedulers.io())
                .subscribe(this::refresh, this::handleThrowable));
    }

    @Override
    public void submitReview(long reviewId) {
        addDisposable(reviewsRepository.submitReview(reviewId)
                .subscribeOn(schedulers.io())
                .subscribe(this::refresh, this::handleThrowable));
    }

    @Override
    public void onOpenReviewSelected(long reviewId) {
        if (isViewAttached()) {
            getView().showReviewDialog(null, reviewId, submissionId, conferenceId);
        }
    }

    @Override
    public void deleteReviewer(long reviewerId) {
        addDisposable(userRepository.deleteReviewerFromSubmission(submissionId, reviewerId)
                .subscribeOn(schedulers.io())
                .subscribe(this::refresh, this::handleThrowable));
    }

    @Override
    public void onAddReviewSelected(long documentId) {
        if (isViewAttached()) {
            getView().showReviewDialog(documentId, null, submissionId, conferenceId);
        }
    }

    @Override
    public void refresh() {
        clearSubscriptions();
        loadSubmission(conferenceId, submissionId);
    }
}
