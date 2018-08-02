package com.dmko.conferencemanagementsystem.ui.screens.submission;

import com.dmko.conferencemanagementsystem.data.submissions.entities.Submission;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BasePresenter;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BaseView;

public interface SubmissionContract {

    interface View extends BaseView {

        void setSubmission(Submission submission);

        void showReviewDialog(Long documentId, Long reviewId, Long submissionId, Long conferenceId);
    }

    interface Presenter extends BasePresenter<View> {

        boolean isCurrentUserConferenceAdmin();

        boolean isCurrentUserSubmissionAuthor();

        boolean isCurrentUserSubmissionReviewer();

        void loadSubmission(long conferenceId, long submissionId);

        void requestReview();

        void submitReview(long reviewId);

        void onOpenReviewSelected(long reviewId);

        void deleteReviewer(long reviewerId);

        void onAddReviewSelected(long documentId);

        void refresh();
    }
}
