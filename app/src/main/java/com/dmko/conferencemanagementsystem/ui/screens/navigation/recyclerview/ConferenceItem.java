package com.dmko.conferencemanagementsystem.ui.screens.navigation.recyclerview;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

public class ConferenceItem implements Parcelable {

    private String title;
    private Fragment fragment;

    public ConferenceItem(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    protected ConferenceItem(Parcel in) {
        title = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
    }

    public static final Creator<ConferenceItem> CREATOR = new Creator<ConferenceItem>() {
        @Override
        public ConferenceItem createFromParcel(Parcel in) {
            return new ConferenceItem(in);
        }

        @Override
        public ConferenceItem[] newArray(int size) {
            return new ConferenceItem[size];
        }
    };
}
