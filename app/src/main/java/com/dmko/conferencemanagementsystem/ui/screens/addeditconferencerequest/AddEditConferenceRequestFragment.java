package com.dmko.conferencemanagementsystem.ui.screens.addeditconferencerequest;

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
import android.widget.TextView;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.requests.entities.ConferenceRequest;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BaseFragment;
import com.dmko.conferencemanagementsystem.ui.screens.addeditconferencerequest.recyclerview.CommentsAdapter;
import com.dmko.conferencemanagementsystem.ui.screens.conferencecomment.ConferenceCommentDialogFragment;
import com.dmko.conferencemanagementsystem.ui.screens.conferencerequests.ConferenceRequestsFragment;
import com.dmko.conferencemanagementsystem.ui.screens.navigation.NavigationCallback;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.dmko.conferencemanagementsystem.data.requests.entities.BriefConferenceRequest.Statuses.DECLINED;
import static com.dmko.conferencemanagementsystem.data.requests.entities.BriefConferenceRequest.Statuses.PENDING;

public class AddEditConferenceRequestFragment extends BaseFragment implements AddEditConferenceRequestContract.View {

    private static final String ARG_CONFERENCE_REQUEST_ID = "conference_request_id";

    @BindView(R.id.input_title) TextInputLayout inputTitle;
    @BindView(R.id.input_acronym) TextInputLayout inputAcronym;
    @BindView(R.id.input_web_page) TextInputLayout inputWebPage;
    @BindView(R.id.input_country) TextInputLayout inputCountry;
    @BindView(R.id.input_city) TextInputLayout inputCity;
    @BindView(R.id.text_comments_title) TextView textCommentsTitle;
    @BindView(R.id.recycler_commentaries) RecyclerView recyclerCommentaries;
    @BindView(R.id.button_evaluate) Button buttonEvaluate;
    @BindView(R.id.button_send) Button buttonSend;

    @Inject AddEditConferenceRequestContract.Presenter presenter;
    @Inject CommentsAdapter adapter;

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
        View view = inflater.inflate(R.layout.fragment_add_edit_conference_request, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);

        recyclerCommentaries.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerCommentaries.setAdapter(adapter);

        if (getArguments() == null) {
            throw new IllegalArgumentException("arguments = null");
        }
        long conferenceRequestId = getArguments().getLong(ARG_CONFERENCE_REQUEST_ID, -1);
        presenter.loadConferenceRequest(conferenceRequestId == -1 ? null : conferenceRequestId);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        unbinder.unbind();
    }


    @OnClick(R.id.button_send)
    public void onButtonSendClicked() {
        ConferenceRequest conferenceRequest = new ConferenceRequest();
        conferenceRequest.setTitle(inputTitle.getEditText().getText().toString());
        conferenceRequest.setAcronym(inputAcronym.getEditText().getText().toString());
        conferenceRequest.setWebPage(inputWebPage.getEditText().getText().toString());
        conferenceRequest.setCountry(inputCountry.getEditText().getText().toString());
        conferenceRequest.setCity(inputCity.getEditText().getText().toString());
        presenter.saveConferenceRequest(conferenceRequest);
    }

    @OnClick(R.id.button_evaluate)
    public void onButtonEvaluateClicked() {
        presenter.onEvaluateConferenceRequestSelected();
    }

    @Override
    public void setupForExisting(ConferenceRequest conferenceRequest) {
        adapter.setComments(conferenceRequest.getComments());
        textCommentsTitle.setVisibility(View.VISIBLE);
        recyclerCommentaries.setVisibility(View.VISIBLE);

        inputTitle.getEditText().setText(conferenceRequest.getTitle());
        inputAcronym.getEditText().setText(conferenceRequest.getAcronym());
        inputWebPage.getEditText().setText(conferenceRequest.getWebPage());
        inputCountry.getEditText().setText(conferenceRequest.getCountry());
        inputCity.getEditText().setText(conferenceRequest.getCity());

        if (conferenceRequest.getStatus() == PENDING && presenter.isCurrentUserGlobalAdmin()) {
            buttonEvaluate.setVisibility(View.VISIBLE);
        } else {
            buttonEvaluate.setVisibility(View.GONE);
        }

        if (presenter.getCurrentUser().getId() == conferenceRequest.getOrganizer().getId()) {
            if (conferenceRequest.getStatus() == DECLINED) {
                setEditable(true);
                buttonSend.setText(R.string.button_send);
                buttonSend.setEnabled(true);
                buttonSend.setVisibility(View.VISIBLE);
            } else if (conferenceRequest.getStatus() == PENDING) {
                setEditable(false);
                buttonSend.setEnabled(false);
                buttonSend.setVisibility(View.VISIBLE);
                buttonSend.setText(R.string.button_send_disabled);
            } else {
                setEditable(false);
                buttonSend.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void setupForNew() {
        buttonSend.setVisibility(View.VISIBLE);
        setEditable(true);
    }

    @Override
    public void showEvaluateConferenceRequestDialog(long conferenceRequestId) {
        ConferenceCommentDialogFragment dialog = ConferenceCommentDialogFragment.newInstance(conferenceRequestId);
        dialog.setOnDismissListener(() -> {
            presenter.refresh();
        });
        dialog.show(getFragmentManager(), "dialog");
    }

    @Override
    public void showConferenceRequests() {
        ConferenceRequestsFragment fragment = ConferenceRequestsFragment.newInstance();
        navigationCallback.showFragment(fragment);
    }

    private void setEditable(boolean isEditable) {
        inputTitle.getEditText().setFocusableInTouchMode(isEditable);
        inputAcronym.getEditText().setFocusableInTouchMode(isEditable);
        inputWebPage.getEditText().setFocusableInTouchMode(isEditable);
        inputCountry.getEditText().setFocusableInTouchMode(isEditable);
        inputCity.getEditText().setFocusableInTouchMode(isEditable);
    }

    public static AddEditConferenceRequestFragment newInstance(Long conferenceRequestId) {
        Bundle args = new Bundle();
        if (conferenceRequestId != null) {
            args.putLong(ARG_CONFERENCE_REQUEST_ID, conferenceRequestId);
        }
        AddEditConferenceRequestFragment fragment = new AddEditConferenceRequestFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
