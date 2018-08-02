package com.dmko.conferencemanagementsystem.ui.screens.conferenceparticipants.recyclerview;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles;
import com.dmko.conferencemanagementsystem.ui.screens.conferenceparticipants.ConferenceParticipantsContract;

public class ConferenceParticipantsAdapter extends PagedListAdapter<BriefUserRoles, ConferenceParticipantViewHolder> {

    private ConferenceParticipantsContract.Presenter presenter;

    public ConferenceParticipantsAdapter(ConferenceParticipantsContract.Presenter presenter) {
        super(DIFF_CALLBACK);
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ConferenceParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_conference_participant, parent, false);
        return new ConferenceParticipantViewHolder(itemView, presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull ConferenceParticipantViewHolder holder, int position) {
        holder.bindParticipant(getCurrentList().get(position));
    }

    private static final DiffUtil.ItemCallback<BriefUserRoles> DIFF_CALLBACK = new DiffUtil.ItemCallback<BriefUserRoles>() {
        @Override
        public boolean areItemsTheSame(BriefUserRoles oldItem, BriefUserRoles newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(BriefUserRoles oldItem, BriefUserRoles newItem) {
            return oldItem.equals(newItem);
        }
    };
}
