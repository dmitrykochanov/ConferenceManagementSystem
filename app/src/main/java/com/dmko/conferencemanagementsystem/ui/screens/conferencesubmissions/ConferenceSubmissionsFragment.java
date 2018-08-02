package com.dmko.conferencemanagementsystem.ui.screens.conferencesubmissions;

import android.arch.paging.PagedList;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.submissions.entities.BriefSubmission;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BaseFragment;
import com.dmko.conferencemanagementsystem.ui.screens.conferencesubmissions.recyclerview.ConferenceSubmissionsAdapter;
import com.dmko.conferencemanagementsystem.ui.screens.navigation.NavigationCallback;
import com.dmko.conferencemanagementsystem.ui.screens.reviewerpicker.ReviewerPickerDialogFragment;
import com.dmko.conferencemanagementsystem.ui.screens.submission.SubmissionFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ConferenceSubmissionsFragment extends BaseFragment implements ConferenceSubmissionsContract.View {

    private static final String ARG_CONFERENCE_ID = "conference_id";

    @BindView(R.id.layout_swipe_to_refresh) SwipeRefreshLayout layoutSwipeRefresh;
    @BindView(R.id.recycler_conference_submissions) RecyclerView recyclerSubmissions;
    @BindView(R.id.spinner_submissions) Spinner spinnerSubmissions;

    @Inject ConferenceSubmissionsContract.Presenter presenter;

    private Unbinder unbinder;
    private NavigationCallback navigationCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getControllerComponent().inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationCallback) {
            navigationCallback = (NavigationCallback) context;
        } else {
            throw new IllegalArgumentException("activity must implement NavigationCallback");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conference_submissions, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);

        if (getArguments() == null) {
            throw new IllegalArgumentException("arguments = null");
        }
        long conferenceId = getArguments().getLong(ARG_CONFERENCE_ID);
        requireActivity().setTitle(R.string.item_submissions);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        DividerItemDecoration decoration = new DividerItemDecoration(requireContext(), layoutManager.getOrientation());
        recyclerSubmissions.setLayoutManager(layoutManager);
        recyclerSubmissions.addItemDecoration(decoration);

        layoutSwipeRefresh.setOnRefreshListener(() -> {
            loadSubmissions(conferenceId);
        });

        spinnerSubmissions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                loadSubmissions(conferenceId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        unbinder.unbind();
    }

    @Override
    public void showLoading(boolean isLoading) {
        layoutSwipeRefresh.setRefreshing(isLoading);
    }

    @Override
    public void setSubmissions(PagedList<BriefSubmission> submissions) {
        ConferenceSubmissionsAdapter adapter = new ConferenceSubmissionsAdapter(presenter);
        recyclerSubmissions.setAdapter(adapter);
        adapter.submitList(submissions);
    }

    @Override
    public void openSubmission(long conferenceId, long submissionId) {
        SubmissionFragment submissionFragment = SubmissionFragment.newInstance(conferenceId, submissionId);
        navigationCallback.showFragment(submissionFragment);
    }

    @Override
    public void openPickReviewerDialog(long conferenceId, long submissionId) {
        ReviewerPickerDialogFragment dialog = ReviewerPickerDialogFragment.newInstance(conferenceId, submissionId);
        dialog.setOnDismissListener(() -> {
            presenter.refresh();
        });
        dialog.show(getFragmentManager(), "dialog");
    }

    private void loadSubmissions(long conferenceId) {
        presenter.clearSubscriptions();
        switch (spinnerSubmissions.getSelectedItemPosition()) {
            case 0:
                presenter.loadAllSubmissions(conferenceId);
            case 1:
                presenter.loadUserSubmissions(conferenceId);
            case 2:
                presenter.loadReviewerSubmissions(conferenceId);
        }
    }

    public static ConferenceSubmissionsFragment newInstance(long conferenceId) {
        Bundle args = new Bundle();
        args.putLong(ARG_CONFERENCE_ID, conferenceId);
        ConferenceSubmissionsFragment fragment = new ConferenceSubmissionsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
