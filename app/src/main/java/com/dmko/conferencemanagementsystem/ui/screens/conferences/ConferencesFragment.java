package com.dmko.conferencemanagementsystem.ui.screens.conferences;


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

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.conferences.entities.BriefConference;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BaseFragment;
import com.dmko.conferencemanagementsystem.ui.screens.conferences.recyclerview.ConferencesAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ConferencesFragment extends BaseFragment implements ConferencesContract.View {

    @BindView(R.id.recycler_conferences) RecyclerView recyclerConferences;
    @BindView(R.id.layout_swipe_to_refresh) SwipeRefreshLayout layoutSwipeRefresh;

    @Inject ConferencesContract.Presenter presenter;

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getControllerComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conferences, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);

        requireActivity().setTitle(getString(R.string.title_conferences));

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        DividerItemDecoration decoration = new DividerItemDecoration(requireContext(), layoutManager.getOrientation());
        recyclerConferences.setLayoutManager(layoutManager);
        recyclerConferences.addItemDecoration(decoration);

        layoutSwipeRefresh.setOnRefreshListener(() -> {
            presenter.clearSubscriptions();
            presenter.loadConferences();
        });

        presenter.loadConferences();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
    }

    @Override
    public void setConferences(PagedList<BriefConference> conferences) {
        ConferencesAdapter conferencesAdapter = new ConferencesAdapter();
        recyclerConferences.setAdapter(conferencesAdapter);
        conferencesAdapter.submitList(conferences);
    }

    @Override
    public void showLoading(boolean isLoading) {
        layoutSwipeRefresh.setRefreshing(isLoading);
    }

    public static ConferencesFragment newInstance() {
        Bundle args = new Bundle();
        ConferencesFragment fragment = new ConferencesFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
