package com.dmko.conferencemanagementsystem.ui.screens.submission.recyclerview;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.reviews.entities.BriefReview;
import com.dmko.conferencemanagementsystem.ui.screens.submission.SubmissionContract;
import com.dmko.conferencemanagementsystem.utils.StatusHelper;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpandableReviewViewHolder extends ChildViewHolder {

    @BindView(R.id.button_open) Button buttonOpen;
    @BindView(R.id.button_send) Button buttonSend;
    @BindView(R.id.text_review) TextView textReview;
    @BindView(R.id.view_status) View viewStatus;
    @BindView(R.id.text_reviewer_name) TextView textReviewerName;

    private SubmissionContract.Presenter presenter;
    private BriefReview review;

    public ExpandableReviewViewHolder(View itemView, SubmissionContract.Presenter presenter) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.presenter = presenter;

        buttonSend.setOnClickListener(v -> {
            presenter.submitReview(review.getId());
        });

        buttonOpen.setOnClickListener(v -> {
            presenter.onOpenReviewSelected(review.getId());
        });
    }

    public void bindReview(BriefReview review) {
        this.review = review;
        textReview.setText(review.getTitle());
        viewStatus.setBackgroundResource(StatusHelper.reviewStatusAsColor(review.getStatus()));
        buttonSend.setVisibility((presenter.isCurrentUserSubmissionReviewer() && !review.isSubmitted()) ? View.VISIBLE : View.GONE);
        buttonOpen.setVisibility((presenter.isCurrentUserConferenceAdmin() || presenter.isCurrentUserSubmissionAuthor() || presenter.isCurrentUserSubmissionReviewer()) ? View.VISIBLE : View.GONE);

        String reviewrName = review.getAuthor().getFirstName() + " " + review.getAuthor().getLastName();
        textReviewerName.setText(reviewrName);
    }
}
