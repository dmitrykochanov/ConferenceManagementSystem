package com.dmko.conferencemanagementsystem.ui.screens.reviewerpicker;

import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUser;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BaseDialogFragment;
import com.dmko.conferencemanagementsystem.ui.screens.reviewerpicker.recyclerview.ReviewersAdapter;
import com.dmko.conferencemanagementsystem.utils.OnDismissListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ReviewerPickerDialogFragment extends BaseDialogFragment implements ReviewerPickerContract.View {

    private static final String ARG_CONFERENCE_ID = "conference_id";
    private static final String ARG_SUBMISSION_ID = "submission_id";

    @BindView(R.id.recycler_picked_reviewers) RecyclerView recyclerPickedReviewers;

    @Inject ReviewerPickerContract.Presenter presenter;

    private ReviewersAdapter adapter;
    private Unbinder unbinder;
    private OnDismissListener onDismissListener;

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getControllerComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_reviewer_picker, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);

        recyclerPickedReviewers.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() == null) {
            throw new IllegalArgumentException("arguments = null");
        }
        long conferenceId = getArguments().getLong(ARG_CONFERENCE_ID);
        long submissionId = getArguments().getLong(ARG_SUBMISSION_ID);

        presenter.loadReviewers(conferenceId, submissionId);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        unbinder.unbind();
    }

    @OnClick(R.id.button_save)
    public void onButtonSaveClicked() {
        presenter.saveReviewers();
    }

    @OnClick(R.id.button_cancel)
    public void onButtonCancelClicked() {
        presenter.onCancelDialogSelected();
    }

    @Override
    public void setReviewers(PagedList<BriefUser> reviewers) {
        adapter = new ReviewersAdapter(presenter);
        recyclerPickedReviewers.setAdapter(adapter);
        adapter.submitList(reviewers);
    }

    @Override
    public void cancelDialog() {
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
        getDialog().cancel();
    }

    public static ReviewerPickerDialogFragment newInstance(long conferenceId, long submissionId) {
        Bundle args = new Bundle();
        args.putLong(ARG_CONFERENCE_ID, conferenceId);
        args.putLong(ARG_SUBMISSION_ID, submissionId);
        ReviewerPickerDialogFragment fragment = new ReviewerPickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
