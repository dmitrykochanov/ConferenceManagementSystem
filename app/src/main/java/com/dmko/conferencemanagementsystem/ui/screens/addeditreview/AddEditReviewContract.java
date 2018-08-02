package com.dmko.conferencemanagementsystem.ui.screens.addeditreview;

import com.dmko.conferencemanagementsystem.data.reviews.entities.Review;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BasePresenter;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BaseView;

public interface AddEditReviewContract {

    interface View extends BaseView {

        void setupForExisting(Review review);

        void setupForNew();

        void openSubmission(long conferenceId, long submissionId);
    }

    interface Presenter extends BasePresenter<View> {

        void loadReview(Long documentId, Long reviewId, long submissionId, long conferenceId);

        void saveReview(String evaluation, int status);

        void onCancelSelected();
    }
}
