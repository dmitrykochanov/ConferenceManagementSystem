package com.dmko.conferencemanagementsystem.ui.screens.submission.recyclerview;

import android.os.Parcel;
import android.os.Parcelable;

import com.dmko.conferencemanagementsystem.data.reviews.entities.BriefReview;

public class ExpandableReview implements Parcelable {

    private BriefReview review;

    public ExpandableReview(BriefReview review) {
        this.review = review;
    }

    public BriefReview getReview() {
        return review;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Creator<ExpandableReview> CREATOR = new Creator<ExpandableReview>() {
        @Override
        public ExpandableReview createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public ExpandableReview[] newArray(int i) {
            return new ExpandableReview[0];
        }
    };
}
