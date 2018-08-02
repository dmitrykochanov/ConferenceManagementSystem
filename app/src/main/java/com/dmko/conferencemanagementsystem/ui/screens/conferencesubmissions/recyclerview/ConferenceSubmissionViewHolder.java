package com.dmko.conferencemanagementsystem.ui.screens.conferencesubmissions.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.submissions.entities.BriefSubmission;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUser;
import com.dmko.conferencemanagementsystem.ui.screens.conferencesubmissions.ConferenceSubmissionsContract;
import com.dmko.conferencemanagementsystem.utils.StatusHelper;
import com.robertlevonyan.views.chip.Chip;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConferenceSubmissionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_submission_title) TextView textSubmissionTitle;
    @BindView(R.id.layout_authors) LinearLayout layoutAuthors;
    @BindView(R.id.layout_reviewers) LinearLayout layoutReviewers;
    @BindView(R.id.view_status) View viewStatus;

    private ConferenceSubmissionsContract.Presenter presenter;
    private BriefSubmission submission;

    public ConferenceSubmissionViewHolder(View itemView, ConferenceSubmissionsContract.Presenter presenter) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.presenter = presenter;

        itemView.setOnClickListener(v -> {
            presenter.onOpenSubmissionSelected(submission.getId());
        });
    }

    public void bindSubmission(BriefSubmission submission) {
        this.submission = submission;
        submission.setAuthor(new BriefUser(1, "creator@google.com", "Дмитрий", "Кочанов", "creator@google.com", true));
        textSubmissionTitle.setText(submission.getTitle());
        viewStatus.setBackgroundResource(StatusHelper.submissionStatusAsColor(submission.getStatus()));

        String authorName = String.format("%s %s", submission.getAuthor().getFirstName(), submission.getAuthor().getLastName());
        Chip authorChip = new Chip(layoutAuthors.getContext());
        authorChip.setChipText(authorName);
        layoutAuthors.addView(authorChip);

        for (BriefUser reviewer : submission.getReviewers()) {
            String reviewerName = String.format("%s %s", reviewer.getFirstName(), reviewer.getLastName());
            Chip reviewerChip = new Chip(layoutAuthors.getContext());
            reviewerChip.setChipText(reviewerName);
            reviewerChip.setClosable(presenter.isCurrentUserConferenceAdmin());
            layoutReviewers.addView(reviewerChip);

            reviewerChip.setOnCloseClickListener(v -> {
                presenter.deleteReviewer(submission.getId(), reviewer.getId());
            });
        }

        if (presenter.isCurrentUserConferenceAdmin()) {
            ImageButton pickRolesButton = new ImageButton(textSubmissionTitle.getContext());
            pickRolesButton.setImageDrawable(textSubmissionTitle.getContext().getResources().getDrawable(R.drawable.round_add_black_24));
            pickRolesButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            layoutReviewers.addView(pickRolesButton);

            pickRolesButton.setOnClickListener(v -> {
                presenter.onPickReviewerSelected(submission.getId());
            });
        }
    }
}
