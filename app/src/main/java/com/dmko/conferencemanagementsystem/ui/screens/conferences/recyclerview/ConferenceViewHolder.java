package com.dmko.conferencemanagementsystem.ui.screens.conferences.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.conferences.entities.BriefConference;

import butterknife.BindView;
import butterknife.ButterKnife;

class ConferenceViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_title) TextView textTitle;

    public ConferenceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindConference(BriefConference conference) {
        textTitle.setText(conference.getTitle());
    }
}
