package com.dmko.conferencemanagementsystem.ui.screens.reviewerpicker.recyclerview;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUser;
import com.dmko.conferencemanagementsystem.ui.screens.reviewerpicker.ReviewerPickerContract;

public class ReviewersAdapter extends PagedListAdapter<BriefUser, ReviewerViewHolder> {

    private ReviewerPickerContract.Presenter presenter;

    public ReviewersAdapter(ReviewerPickerContract.Presenter presenter) {
        super(DIFF_CALLBACK);
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ReviewerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_picked_reviewer, parent, false);
        return new ReviewerViewHolder(itemView, presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewerViewHolder holder, int position) {
        holder.bindPickedReviewer(getItem(position));
    }

    private static final DiffUtil.ItemCallback<BriefUser> DIFF_CALLBACK = new DiffUtil.ItemCallback<BriefUser>() {

        @Override
        public boolean areItemsTheSame(BriefUser oldItem, BriefUser newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(BriefUser oldItem, BriefUser newItem) {
            return oldItem.equals(newItem);
        }
    };
}
