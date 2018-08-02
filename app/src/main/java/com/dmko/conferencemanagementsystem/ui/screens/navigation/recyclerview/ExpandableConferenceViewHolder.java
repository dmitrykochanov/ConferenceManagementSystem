package com.dmko.conferencemanagementsystem.ui.screens.navigation.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmko.conferencemanagementsystem.R;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpandableConferenceViewHolder extends GroupViewHolder {

    @BindView(R.id.text_title) TextView textTitle;
    @BindView(R.id.image_expand) ImageView imageExpand;

    public ExpandableConferenceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void expand() {
        super.expand();
        imageExpand.setImageResource(R.drawable.ic_expand_less);
    }

    @Override
    public void collapse() {
        super.collapse();
        imageExpand.setImageResource(R.drawable.ic_expand_more);
    }

    public void bindConference(ExpandableConference conference) {
        textTitle.setText(conference.getTitle());
    }
}
