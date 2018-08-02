package com.dmko.conferencemanagementsystem.ui.screens.conferences.recyclerview;


import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.conferences.entities.BriefConference;

public class ConferencesAdapter extends PagedListAdapter<BriefConference, ConferenceViewHolder> {

    public ConferencesAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ConferenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_conference, parent, false);
        return new ConferenceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConferenceViewHolder holder, int position) {
        holder.bindConference(getItem(position));
    }

    private static final DiffUtil.ItemCallback<BriefConference> DIFF_CALLBACK = new DiffUtil.ItemCallback<BriefConference>() {
        @Override
        public boolean areItemsTheSame(BriefConference oldItem, BriefConference newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(BriefConference oldItem, BriefConference newItem) {
            return oldItem.equals(newItem);
        }
    };
}
