package com.dmko.conferencemanagementsystem.ui.screens.addeditreview;

import com.dmko.conferencemanagementsystem.data.reviews.ReviewsRepository;
import com.dmko.conferencemanagementsystem.data.reviews.entities.Review;
import com.dmko.conferencemanagementsystem.data.user.UserRepository;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BasePresenterImpl;
import com.dmko.conferencemanagementsystem.utils.SchedulersFacade;

public class AddEditReviewPresenter extends BasePresenterImpl<AddEditReviewContract.View> implements AddEditReviewContract.Presenter {

    private SchedulersFacade schedulers;
    private UserRepository userRepository;
    private ReviewsRepository reviewsRepository;
    private Review review;
    private Long documentId;
    private Long reviewId;
    private Long submissionId;
    private Long conferenceId;

    public AddEditReviewPresenter(SchedulersFacade schedulers, UserRepository userRepository, ReviewsRepository reviewsRepository) {
        this.schedulers = schedulers;
        this.userRepository = userRepository;
        this.reviewsRepository = reviewsRepository;
    }

    @Override
    public void loadReview(Long documentId, Long reviewId, long submissionId, long conferenceId) {
        if (documentId == null && reviewId == null) {
            throw new IllegalArgumentException("both arguments can't be null");
        }
        this.submissionId = submissionId;
        this.conferenceId = conferenceId;
        if (reviewId == null) {
            this.documentId = documentId;
            getView().setupForNew();
        } else {
            addDisposable(reviewsRepository.getReview(reviewId)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .subscribe(review -> {
                        if (isViewAttached()) {
                            this.review = review;
                            getView().setupForExisting(review);
                        }
                    }, this::handleThrowable));
        }
    }

    @Override
    public void saveReview(String evaluation, int status) {
        if (documentId != null) {
            Review review = new Review();
            review.setEvaluation(evaluation);
            review.setStatus(status);
            review.setAuthor(userRepository.getUser());
            addDisposable(reviewsRepository.createReview(documentId, review)
                    .subscribeOn(schedulers.io())
                    .subscribe(() -> {
                        if (isViewAttached()) {
                            getView().openSubmission(conferenceId, submissionId);
                        }
                    }, this::handleThrowable));
        } else {
            if (review == null) return;

            review.setEvaluation(evaluation);
            review.setStatus(status);
            addDisposable(reviewsRepository.updateReview(review)
                    .subscribeOn(schedulers.io())
                    .subscribe(() -> {
                        if (isViewAttached()) {
                            getView().openSubmission(conferenceId, submissionId);
                        }
                    }, this::handleThrowable));
        }
    }

    @Override
    public void onCancelSelected() {
        if (isViewAttached()) {
            getView().openSubmission(conferenceId, submissionId);
        }
    }
}
