package com.dmko.conferencemanagementsystem.ui.screens.conferencesubmissions;

import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.submissions.entities.BriefSubmission;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BasePresenter;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BaseView;

public interface ConferenceSubmissionsContract {

    interface View extends BaseView {

        void showLoading(boolean isLoading);

        void setSubmissions(PagedList<BriefSubmission> submissions);

        void openSubmission(long conferenceId, long submissionId);

        void openPickReviewerDialog(long conferenceId, long submissionId);
    }

    interface Presenter extends BasePresenter<View> {

        boolean isCurrentUserConferenceAdmin();

        void loadAllSubmissions(long conferenceId);

        void loadUserSubmissions(long conferenceId);

        void loadReviewerSubmissions(long conferenceId);

        void onOpenSubmissionSelected(long submissionId);

        void onPickReviewerSelected(long submissionId);

        void deleteReviewer(long submissionId, long reviewerId);

        void refresh();
    }
}
