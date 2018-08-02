package com.dmko.conferencemanagementsystem.ui.screens.conferenceinformation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.conferences.entities.Conference;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ConferenceInformationFragment extends BaseFragment implements ConferenceInformationContract.View {

    private static final String ARG_CONFERENCE_ID = "conference_id";

    @BindView(R.id.input_title) TextInputLayout inputTitle;
    @BindView(R.id.input_acronym) TextInputLayout inputAcronym;
    @BindView(R.id.input_web_page) TextInputLayout inputWebPage;
    @BindView(R.id.input_country) TextInputLayout inputCountry;
    @BindView(R.id.input_city) TextInputLayout inputCity;

    @BindView(R.id.progress_loading) ProgressBar progressLoading;

    @Inject ConferenceInformationContract.Presenter presenter;

    private Unbinder unbinder;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getControllerComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conference_information, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);

        if (getArguments() == null) {
            throw new IllegalArgumentException("arguments = null");
        }
        long conferenceId = getArguments().getLong(ARG_CONFERENCE_ID);
        presenter.loadConference(conferenceId);
        getActivity().setTitle(R.string.item_information);

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
        progressLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setConference(Conference conference) {
        inputTitle.getEditText().setText(conference.getTitle());
        inputAcronym.getEditText().setText(conference.getAcronym());
        inputWebPage.getEditText().setText(conference.getWebPage());
        inputCountry.getEditText().setText(conference.getCountry());
        inputCity.getEditText().setText(conference.getCity());
    }

    public static ConferenceInformationFragment newInstance(long conferenceId) {
        Bundle args = new Bundle();
        args.putLong(ARG_CONFERENCE_ID, conferenceId);
        ConferenceInformationFragment fragment = new ConferenceInformationFragment();
        fragment.setArguments(args);
        return fragment;
    }
}