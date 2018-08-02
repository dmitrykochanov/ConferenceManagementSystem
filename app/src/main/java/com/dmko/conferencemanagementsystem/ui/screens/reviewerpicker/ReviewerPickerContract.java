package com.dmko.conferencemanagementsystem.ui.screens.reviewerpicker;

import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.user.entities.BriefUser;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BasePresenter;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BaseView;

public interface ReviewerPickerContract {

    interface View extends BaseView {

        void setReviewers(PagedList<BriefUser> reviewers);

        void cancelDialog();
    }

    interface Presenter extends BasePresenter<View> {

        void loadReviewers(long conferenceId, long submissionId);

        void addReviewer(long reviewerId);

        void deleteReviewer(long reviewerId);

        void onCancelDialogSelected();

        void saveReviewers();
    }
}
