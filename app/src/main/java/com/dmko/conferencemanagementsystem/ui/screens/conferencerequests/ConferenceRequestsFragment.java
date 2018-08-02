package com.dmko.conferencemanagementsystem.ui.screens.conferencerequests;

import android.arch.paging.PagedList;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.requests.entities.BriefConferenceRequest;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BaseFragment;
import com.dmko.conferencemanagementsystem.ui.screens.addeditconferencerequest.AddEditConferenceRequestFragment;
import com.dmko.conferencemanagementsystem.ui.screens.conferencerequests.recyclerview.ConferenceRequestsAdapter;
import com.dmko.conferencemanagementsystem.ui.screens.navigation.NavigationCallback;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ConferenceRequestsFragment extends BaseFragment implements ConferenceRequestsContract.View {

    @Inject ConferenceRequestsContract.Presenter presenter;

    @BindView(R.id.recycler_conference_requests) RecyclerView recyclerRequests;
    @BindView(R.id.layout_swipe_to_refresh) SwipeRefreshLayout layoutSwipeRefresh;

    private NavigationCallback navigationCallback;
    private ConferenceRequestsAdapter adapter;
    private Unbinder unbinder;

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
        View view = inflater.inflate(R.layout.fragment_conference_requests, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);
        requireActivity().setTitle(getString(R.string.title_conference_requests));

        recyclerRequests.setLayoutManager(new LinearLayoutManager(getContext()));

        layoutSwipeRefresh.setOnRefreshListener(() -> {
            presenter.clearSubscriptions();
            presenter.loadConferenceRequests();
        });

        presenter.loadConferenceRequests();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        unbinder.unbind();
    }

    @OnClick(R.id.button_create_conference_request)
    public void onCreateConferenceRequestClicked() {
        presenter.onOpenConferenceRequestSelected(null);
    }

    @Override
    public void setConferenceRequests(PagedList<BriefConferenceRequest> conferenceRequests) {
        adapter = new ConferenceRequestsAdapter(presenter);
        recyclerRequests.setAdapter(adapter);
        adapter.submitList(conferenceRequests);
    }

    @Override
    public void openConferenceRequest(Long conferenceRequestId) {
        navigationCallback.showFragment(AddEditConferenceRequestFragment.newInstance(conferenceRequestId));
    }

    @Override
    public void showLoading(boolean isLoading) {
        layoutSwipeRefresh.setRefreshing(isLoading);
    }

    public static ConferenceRequestsFragment newInstance() {
        Bundle args = new Bundle();
        ConferenceRequestsFragment fragment = new ConferenceRequestsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
