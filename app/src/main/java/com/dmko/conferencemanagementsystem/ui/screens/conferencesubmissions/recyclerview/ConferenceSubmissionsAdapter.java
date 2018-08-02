package com.dmko.conferencemanagementsystem.ui.screens.conferencesubmissions.recyclerview;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.submissions.entities.BriefSubmission;
import com.dmko.conferencemanagementsystem.ui.screens.conferencesubmissions.ConferenceSubmissionsContract;

public class ConferenceSubmissionsAdapter extends PagedListAdapter<BriefSubmission, ConferenceSubmissionViewHolder> {

    private ConferenceSubmissionsContract.Presenter presenter;

    public ConferenceSubmissionsAdapter(ConferenceSubmissionsContract.Presenter presenter) {
        super(DIFF_CALLBACK);
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ConferenceSubmissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_submission, parent, false);
        return new ConferenceSubmissionViewHolder(itemView, presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull ConferenceSubmissionViewHolder holder, int position) {
        holder.bindSubmission(getCurrentList().get(position));
    }

    private static final DiffUtil.ItemCallback<BriefSubmission> DIFF_CALLBACK = new DiffUtil.ItemCallback<BriefSubmission>() {
        @Override
        public boolean areItemsTheSame(BriefSubmission oldItem, BriefSubmission newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(BriefSubmission oldItem, BriefSubmission newItem) {
            return oldItem.equals(newItem);
        }
    };
}
