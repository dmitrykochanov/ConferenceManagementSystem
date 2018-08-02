package com.dmko.conferencemanagementsystem.ui.screens.submission;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.reviews.entities.BriefReview;
import com.dmko.conferencemanagementsystem.data.submissions.entities.Document;
import com.dmko.conferencemanagementsystem.data.submissions.entities.Submission;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUser;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BaseFragment;
import com.dmko.conferencemanagementsystem.ui.screens.addeditreview.AddEditReviewFragment;
import com.dmko.conferencemanagementsystem.ui.screens.navigation.NavigationCallback;
import com.dmko.conferencemanagementsystem.ui.screens.reviewerpicker.ReviewerPickerDialogFragment;
import com.dmko.conferencemanagementsystem.ui.screens.submission.recyclerview.ExpandableDocument;
import com.dmko.conferencemanagementsystem.ui.screens.submission.recyclerview.ExpandableDocumentAdapter;
import com.dmko.conferencemanagementsystem.ui.screens.submission.recyclerview.ExpandableReview;
import com.robertlevonyan.views.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.dmko.conferencemanagementsystem.utils.StatusHelper.submissionStatusAsString;

public class SubmissionFragment extends BaseFragment implements SubmissionContract.View {

    private static final String ARG_SUBMISSION_ID = "submission_id";
    private static final String ARG_CONFERENCE_ID = "conference_id";

    @BindView(R.id.input_title) TextInputLayout inputTitle;
    @BindView(R.id.input_status) TextInputLayout inputStatus;
    @BindView(R.id.button_request_review) Button buttonRequestReview;
    @BindView(R.id.layout_authors) LinearLayout layoutAuthors;
    @BindView(R.id.layout_reviewers) LinearLayout layoutReviewers;
    @BindView(R.id.recycler_documents) RecyclerView recyclerDocuments;

    @Inject SubmissionContract.Presenter presenter;

    private Unbinder unbinder;
    private NavigationCallback navigationCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationCallback) {
            navigationCallback = (NavigationCallback) context;
        } else {
            throw new IllegalArgumentException("activity must implement NavigationCallback");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getControllerComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submission, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);

        recyclerDocuments.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() == null) {
            throw new IllegalArgumentException("arguments = null");
        }
        long conferenceId = getArguments().getLong(ARG_CONFERENCE_ID);
        long submissionId = getArguments().getLong(ARG_SUBMISSION_ID);
        presenter.loadSubmission(conferenceId, submissionId);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        unbinder.unbind();
    }

    @OnClick(R.id.button_request_review)
    public void onButtonRequestReviewClicked() {
        presenter.requestReview();
    }

    @Override
    public void setSubmission(Submission submission) {
        inputTitle.getEditText().setText(submission.getTitle());
        inputStatus.getEditText().setText(submissionStatusAsString(submission.getStatus(), getContext()));
        buttonRequestReview.setVisibility(presenter.isCurrentUserSubmissionAuthor() ? View.VISIBLE : View.GONE);
        if (submission.isReviewable()) {
            buttonRequestReview.setEnabled(false);
            buttonRequestReview.setText(R.string.button_request_review_disabled);
        }
        setupChips(submission);
        setSubmissionToRecyclerView(submission);
    }

    @Override
    public void showReviewDialog(Long documentId, Long reviewId, Long submissionId, Long conferenceId) {
        AddEditReviewFragment fragment = AddEditReviewFragment.newInstance(documentId, reviewId, submissionId, conferenceId);
        navigationCallback.showFragment(fragment);
    }

    private void setupChips(Submission submission) {
        layoutAuthors.removeAllViews();
        layoutReviewers.removeAllViews();
        Chip authorChip = new Chip(getContext());
        String authorName = String.format("%s %s", submission.getAuthor().getFirstName(), submission.getAuthor().getLastName());
        authorChip.setChipText(authorName);
        layoutAuthors.addView(authorChip);

        for (BriefUser reviewer : submission.getReviewers()) {
            Chip reviewerChip = new Chip(getContext());
            reviewerChip.setClosable(presenter.isCurrentUserConferenceAdmin());
            reviewerChip.setOnCloseClickListener(v -> {
                presenter.deleteReviewer(reviewer.getId());
            });

            String reviewerName = String.format("%s %s", reviewer.getFirstName(), reviewer.getLastName());
            reviewerChip.setChipText(reviewerName);

            layoutReviewers.addView(reviewerChip);
        }

        if (presenter.isCurrentUserConferenceAdmin()) {
            ImageButton addReviewersButton = new ImageButton(getContext());
            addReviewersButton.setImageDrawable(getContext().getResources().getDrawable(R.drawable.round_add_black_24));
            addReviewersButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            layoutReviewers.addView(addReviewersButton);

            addReviewersButton.setOnClickListener(v -> {
                ReviewerPickerDialogFragment dialog = ReviewerPickerDialogFragment.newInstance(submission.getConferenceId(), submission.getId());
                dialog.setOnDismissListener(() -> {
                    presenter.refresh();
                });
                dialog.show(getFragmentManager(), "dialog");
            });
        }
    }

    private void setSubmissionToRecyclerView(Submission submission) {
        List<ExpandableDocument> expandableDocuments = new ArrayList<>(submission.getDocuments().size());
        for (Document document : submission.getDocuments()) {
            List<ExpandableReview> expandableReviews = new ArrayList<>(document.getReviews().size());
            for (BriefReview review : document.getReviews()) {
                ExpandableReview expandableReview = new ExpandableReview(review);
                expandableReviews.add(expandableReview);
            }
            ExpandableDocument expandableDocument = new ExpandableDocument(document, expandableReviews);
            expandableDocuments.add(expandableDocument);
        }

        ExpandableDocumentAdapter adapter = new ExpandableDocumentAdapter(expandableDocuments, presenter);
        recyclerDocuments.setAdapter(adapter);
    }

    public static SubmissionFragment newInstance(long conferenceId, long submissionId) {
        Bundle args = new Bundle();
        args.putLong(ARG_SUBMISSION_ID, submissionId);
        args.putLong(ARG_CONFERENCE_ID, conferenceId);
        SubmissionFragment fragment = new SubmissionFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
