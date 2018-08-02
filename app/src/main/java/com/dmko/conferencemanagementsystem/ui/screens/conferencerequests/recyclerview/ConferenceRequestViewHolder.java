package com.dmko.conferencemanagementsystem.ui.screens.conferencerequests.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.requests.entities.BriefConferenceRequest;
import com.dmko.conferencemanagementsystem.ui.screens.conferencerequests.ConferenceRequestsContract;
import com.dmko.conferencemanagementsystem.utils.StatusHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConferenceRequestViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_conference_request_title) TextView textConferenceRequestTitle;
    @BindView(R.id.view_status) View viewStatus;

    private BriefConferenceRequest conferenceRequest;

    public ConferenceRequestViewHolder(View itemView, ConferenceRequestsContract.Presenter presenter) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(v -> {
            presenter.onOpenConferenceRequestSelected(conferenceRequest.getId());
        });
    }

    public void bindConferenceRequest(BriefConferenceRequest conferenceRequest) {
        this.conferenceRequest = conferenceRequest;
        viewStatus.setBackgroundResource(StatusHelper.conferenceRequestStatusAsColor(conferenceRequest.getStatus()));
        textConferenceRequestTitle.setText(conferenceRequest.getTitle());
    }
}
