package com.dmko.conferencemanagementsystem.ui.screens.conferencerequests.recyclerview;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.requests.entities.BriefConferenceRequest;
import com.dmko.conferencemanagementsystem.ui.screens.conferencerequests.ConferenceRequestsContract;

public class ConferenceRequestsAdapter extends PagedListAdapter<BriefConferenceRequest, ConferenceRequestViewHolder> {

    private ConferenceRequestsContract.Presenter presenter;

    public ConferenceRequestsAdapter(ConferenceRequestsContract.Presenter presenter) {
        super(DIFF_CALLBACK);
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ConferenceRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_conference_request, parent, false);
        return new ConferenceRequestViewHolder(itemView, presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull ConferenceRequestViewHolder holder, int position) {
        holder.bindConferenceRequest(getCurrentList().get(position));
    }

    private static final DiffUtil.ItemCallback<BriefConferenceRequest> DIFF_CALLBACK = new DiffUtil.ItemCallback<BriefConferenceRequest>() {
        @Override
        public boolean areItemsTheSame(BriefConferenceRequest oldItem, BriefConferenceRequest newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(BriefConferenceRequest oldItem, BriefConferenceRequest newItem) {
            return oldItem.equals(newItem);
        }
    };
}
