package com.dmko.conferencemanagementsystem.ui.screens.reviewerpicker.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUser;
import com.dmko.conferencemanagementsystem.ui.screens.reviewerpicker.ReviewerPickerContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_reviewer_name) TextView textReviewerName;
    @BindView(R.id.check_picked) CheckBox checkPicked;

    private BriefUser user;

    public ReviewerViewHolder(View itemView, ReviewerPickerContract.Presenter presenter) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        checkPicked.setOnCheckedChangeListener((button, isChecked) -> {
            if (isChecked) {
                presenter.addReviewer(user.getId());
            } else {
                presenter.deleteReviewer(user.getId());
            }
        });
    }

    public void bindPickedReviewer(BriefUser user) {
        this.user = user;
        String reviewerName = String.format("%s %s", this.user.getFirstName(), this.user.getLastName());
        textReviewerName.setText(reviewerName);
    }
}
