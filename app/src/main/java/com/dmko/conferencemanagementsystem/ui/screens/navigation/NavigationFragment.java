package com.dmko.conferencemanagementsystem.ui.screens.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.conferences.entities.BriefConference;
import com.dmko.conferencemanagementsystem.data.user.entities.User;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BaseFragment;
import com.dmko.conferencemanagementsystem.ui.screens.conferenceinformation.ConferenceInformationFragment;
import com.dmko.conferencemanagementsystem.ui.screens.conferenceparticipants.ConferenceParticipantsFragment;
import com.dmko.conferencemanagementsystem.ui.screens.conferencerequests.ConferenceRequestsFragment;
import com.dmko.conferencemanagementsystem.ui.screens.conferences.ConferencesFragment;
import com.dmko.conferencemanagementsystem.ui.screens.conferencesubmissions.ConferenceSubmissionsFragment;
import com.dmko.conferencemanagementsystem.ui.screens.navigation.recyclerview.ConferenceItem;
import com.dmko.conferencemanagementsystem.ui.screens.navigation.recyclerview.ExpandableConference;
import com.dmko.conferencemanagementsystem.ui.screens.navigation.recyclerview.ExpandableConferenceAdapter;
import com.dmko.conferencemanagementsystem.ui.screens.signin.SignInActivity;
import com.dmko.conferencemanagementsystem.ui.screens.signup.SignUpActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NavigationFragment extends BaseFragment implements NavigationContract.View {

    @BindView(R.id.text_user_name) TextView textUserName;
    @BindView(R.id.text_user_email) TextView textUserEmail;
    @BindView(R.id.textAllConferences) TextView textAllConferences;
    @BindView(R.id.recycler_menu) RecyclerView recyclerMenu;
    @BindView(R.id.textSignIn) TextView textSignIn;
    @BindView(R.id.textSignUp) TextView textSignUp;
    @BindView(R.id.textLogout) TextView textLogout;
    @BindView(R.id.text_conference_requests) TextView textConferenceRequests;
    @BindView(R.id.layout_swipe_to_refresh) SwipeRefreshLayout layoutSwipeRefresh;

    @BindString(R.string.item_information) String itemInformation;
    @BindString(R.string.item_submissions) String itemSubmissions;
    @BindString(R.string.item_participants) String itemParticipants;

    @Inject NavigationContract.Presenter presenter;

    private Unbinder unbinder;
    private NavigationCallback navigationCallback;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
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
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        unbinder = ButterKnife.bind(this, view);

        layoutSwipeRefresh.setOnRefreshListener(() -> {
            presenter.clearSubscriptions();
            presenter.start();
        });

        setupRecyclerView();
        presenter.attachView(this);
        presenter.start();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void setupForGuest() {
        textUserName.setText(R.string.guest);

        textUserName.setVisibility(View.VISIBLE);
        textSignIn.setVisibility(View.VISIBLE);
        textSignUp.setVisibility(View.VISIBLE);
        textAllConferences.setVisibility(View.VISIBLE);

        textLogout.setVisibility(View.GONE);
        recyclerMenu.setVisibility(View.GONE);
        textUserEmail.setVisibility(View.GONE);
        textConferenceRequests.setVisibility(View.GONE);
    }

    @Override
    public void setupForSignedInUser(User user, List<BriefConference> conferences) {
        textUserName.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
        textUserEmail.setText(user.getEmail());
        setConferencesToRecyclerView(conferences);

        textUserEmail.setVisibility(View.VISIBLE);
        textUserName.setVisibility(View.VISIBLE);
        textLogout.setVisibility(View.VISIBLE);
        textAllConferences.setVisibility(View.VISIBLE);
        recyclerMenu.setVisibility(View.VISIBLE);
        textConferenceRequests.setVisibility(View.VISIBLE);

        textSignIn.setVisibility(View.GONE);
        textSignUp.setVisibility(View.GONE);
    }

    @Override
    public void openFragment(Fragment fragment) {
        navigationCallback.showFragment(fragment);
    }

    @Override
    public void showLoading(boolean isLoading) {
        layoutSwipeRefresh.setRefreshing(isLoading);
    }

    public void onNewIntent(Intent intent) {
        presenter.checkIfLoggedIn();
    }

    private void setupRecyclerView() {
        recyclerMenu.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setConferencesToRecyclerView(List<BriefConference> conferences) {
        List<ExpandableConference> expandableConferences = new ArrayList<>(conferences.size());
        for (BriefConference conference : conferences) {
            List<ConferenceItem> items = new ArrayList<>();
            items.add(new ConferenceItem(itemInformation, ConferenceInformationFragment.newInstance(conference.getId())));
            items.add(new ConferenceItem(itemSubmissions, ConferenceSubmissionsFragment.newInstance(conference.getId())));
            items.add(new ConferenceItem(itemParticipants, ConferenceParticipantsFragment.newInstance(conference.getId())));

            expandableConferences.add(new ExpandableConference(conference.getTitle(), items));
        }

        ExpandableConferenceAdapter adapter = new ExpandableConferenceAdapter(expandableConferences, presenter);
        recyclerMenu.setAdapter(adapter);
    }

    @OnClick({R.id.textSignIn, R.id.textSignUp, R.id.textLogout, R.id.textAllConferences, R.id.text_conference_requests})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textAllConferences:
                openFragment(ConferencesFragment.newInstance());
                break;
            case R.id.text_conference_requests:
                openFragment(ConferenceRequestsFragment.newInstance());
                break;
            case R.id.textSignIn:
                Intent signInIntent = new Intent(getContext(), SignInActivity.class);
                startActivity(signInIntent);
                break;
            case R.id.textSignUp:
                Intent signUpIntent = new Intent(getContext(), SignUpActivity.class);
                startActivity(signUpIntent);
                break;
            case R.id.textLogout:
                presenter.logout();
                break;
        }
    }
}