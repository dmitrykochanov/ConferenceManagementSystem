package com.dmko.conferencemanagementsystem.ui.screens.conferencecomment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.requests.entities.ConferenceRequestComment;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BaseDialogFragment;
import com.dmko.conferencemanagementsystem.utils.OnDismissListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ConferenceCommentDialogFragment extends BaseDialogFragment implements ConferenceCommentContract.View {

    private static final String ARG_CONFERENCE_REQUEST_ID = "conference_request_id";

    @BindView(R.id.input_content) EditText inputContent;
    @BindView(R.id.spinner_statuses) Spinner spinnerStatuses;

    @Inject ConferenceCommentContract.Presenter presenter;

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
        View view = inflater.inflate(R.layout.dialog_conference_comment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);

        if (getArguments() == null) {
            throw new IllegalArgumentException("arguments = null");
        }
        long conferenceRequestId = getArguments().getLong(ARG_CONFERENCE_REQUEST_ID);
        presenter.setConferenceRequestId(conferenceRequestId);

        return view;
    }

    @OnClick(R.id.button_save)
    public void onButtonSaveClicked() {
        ConferenceRequestComment comment = new ConferenceRequestComment();
        comment.setContent(inputContent.getText().toString());
        comment.setStatus(spinnerStatuses.getSelectedItemPosition() + 1);
        presenter.addComment(comment);
    }

    @OnClick(R.id.button_cancel)
    public void onButtonCancelClicked() {
        getDialog().cancel();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
    }

    public static ConferenceCommentDialogFragment newInstance(long conferenceRequestId) {
        Bundle args = new Bundle();
        args.putLong(ARG_CONFERENCE_REQUEST_ID, conferenceRequestId);
        ConferenceCommentDialogFragment fragment = new ConferenceCommentDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void cancelDialog() {
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
        getDialog().cancel();
    }
}
