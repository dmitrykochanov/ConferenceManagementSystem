package com.dmko.conferencemanagementsystem.ui.screens.navigation.recyclerview;

import android.view.View;
import android.widget.TextView;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.ui.screens.navigation.NavigationContract;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConferenceItemViewHolder extends ChildViewHolder {
    @BindView(R.id.text_title) TextView textTitle;

    private ConferenceItem conferenceItem;

    public ConferenceItemViewHolder(final View itemView, NavigationContract.Presenter presenter) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(v -> {
            presenter.onItemClicked(conferenceItem);
        });
    }

    public void bindItem(ConferenceItem item) {
        textTitle.setText(item.getTitle());
        this.conferenceItem = item;
    }
}
