package com.dmko.conferencemanagementsystem.ui.screens.conferenceparticipants;

import android.arch.paging.PagedList;
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
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BaseFragment;
import com.dmko.conferencemanagementsystem.ui.screens.conferenceparticipants.recyclerview.ConferenceParticipantsAdapter;
import com.dmko.conferencemanagementsystem.ui.screens.rolepicker.RolePickerDialogFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles.Roles.*;

public class ConferenceParticipantsFragment extends BaseFragment implements ConferenceParticipantsContract.View {
    private static final String ARG_CONFERENCE_ID = "conference_id";

    @BindView(R.id.recycler_conference_participants) RecyclerView recyclerParticipants;
    @BindView(R.id.layout_swipe_to_refresh) SwipeRefreshLayout layoutSwipeRefresh;
    @BindView(R.id.spinner_roles) Spinner spinnerRoles;

    @Inject ConferenceParticipantsContract.Presenter presenter;
    ConferenceParticipantsAdapter adapter;

    private Unbinder unbinder;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getControllerComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conference_participants, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);

        if (getArguments() == null) {
            throw new IllegalArgumentException("arguments = null");
        }
        long conferenceId = getArguments().getLong(ARG_CONFERENCE_ID);
        getActivity().setTitle(R.string.item_participants);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        recyclerParticipants.setLayoutManager(layoutManager);
        recyclerParticipants.addItemDecoration(decoration);

        layoutSwipeRefresh.setOnRefreshListener(() -> {
            presenter.clearSubscriptions();
            presenter.loadConferenceParticipants(conferenceId, getSelectedFilterRole());
        });

        spinnerRoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                presenter.clearSubscriptions();
                presenter.loadConferenceParticipants(conferenceId, getSelectedFilterRole());
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
    public void setConferenceParticipants(PagedList<BriefUserRoles> participants) {
        adapter = new ConferenceParticipantsAdapter(presenter);
        recyclerParticipants.setAdapter(adapter);
        adapter.submitList(participants);
    }

    @Override
    public void showPickRolesDialog(long conferenceId, long userId) {
        RolePickerDialogFragment dialog = RolePickerDialogFragment.newInstance(conferenceId, userId);
        dialog.setOnDialogClosedListener(() -> {
            presenter.refresh();
        });
        dialog.show(getFragmentManager(), "dialog");
    }

    private Long getSelectedFilterRole() {
        switch (spinnerRoles.getSelectedItemPosition()) {
            case 1:
                return ADMIN;
            case 2:
                return REVIEWER;
            default:
                return null;
        }
    }

    public static ConferenceParticipantsFragment newInstance(long conferenceId) {
        Bundle args = new Bundle();
        args.putLong(ARG_CONFERENCE_ID, conferenceId);
        ConferenceParticipantsFragment fragment = new ConferenceParticipantsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}