package com.dmko.conferencemanagementsystem.ui.screens.addeditreview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.reviews.entities.Review;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BaseFragment;
import com.dmko.conferencemanagementsystem.ui.screens.navigation.NavigationCallback;
import com.dmko.conferencemanagementsystem.ui.screens.submission.SubmissionFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddEditReviewFragment extends BaseFragment implements AddEditReviewContract.View {

    private static final String ARG_REVIEW_ID = "review_id";
    private static final String ARG_DOCUMENT_ID = "document_id";
    private static final String ARG_SUBMISSION_ID = "submission_id";
    private static final String ARG_CONFERENCE_ID = "conference_id";

    @BindView(R.id.spinner_statuses) Spinner spinnerStatuses;
    @BindView(R.id.input_review) EditText inputReview;
    @BindView(R.id.button_save) Button saveButton;

    @Inject AddEditReviewContract.Presenter presenter;

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
        View view = inflater.inflate(R.layout.fragment_add_edit_review, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);

        if (getArguments() == null) {
            throw new IllegalArgumentException("arguments = null");
        }
        Long reviewId = getArguments().getLong(ARG_REVIEW_ID, -1);
        Long documentId = getArguments().getLong(ARG_DOCUMENT_ID, -1);
        Long submissionId = getArguments().getLong(ARG_SUBMISSION_ID);
        Long conferenceId = getArguments().getLong(ARG_CONFERENCE_ID);

        presenter.loadReview(documentId == -1 ? null : documentId, reviewId == -1 ? null : reviewId, submissionId, conferenceId);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        unbinder.unbind();
    }

    @OnClick(R.id.button_save)
    public void onSaveButtonClicked() {
        presenter.saveReview(inputReview.getText().toString(), spinnerStatuses.getSelectedItemPosition());
    }

    @OnClick(R.id.button_cancel)
    public void onButtonCancelClicked() {
        presenter.onCancelSelected();
    }

    @Override
    public void setupForExisting(Review review) {

        inputReview.setFocusableInTouchMode(review.isSubmitted());
        inputReview.setText(review.getEvaluation());

        spinnerStatuses.setFocusable(!review.isSubmitted());
        spinnerStatuses.setSelection(review.getStatus());

        saveButton.setVisibility(review.isSubmitted() ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void setupForNew() {
        inputReview.setFocusableInTouchMode(true);
        saveButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void openSubmission(long conferenceId, long submissionId) {
        SubmissionFragment fragment = SubmissionFragment.newInstance(conferenceId, submissionId);
        navigationCallback.showFragment(fragment);
    }

    public static AddEditReviewFragment newInstance(Long documentId, Long reviewId, long submissionId, long conferenceId) {
        Bundle args = new Bundle();
        if (reviewId != null) {
            args.putLong(ARG_REVIEW_ID, reviewId);
        }
        if (documentId != null) {
            args.putLong(ARG_DOCUMENT_ID, documentId);
        }
        args.putLong(ARG_SUBMISSION_ID, submissionId);
        args.putLong(ARG_CONFERENCE_ID, conferenceId);
        AddEditReviewFragment fragment = new AddEditReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
