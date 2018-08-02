package com.dmko.conferencemanagementsystem.ui.screens.navigation.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.ui.screens.navigation.NavigationContract;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ExpandableConferenceAdapter extends ExpandableRecyclerViewAdapter<ExpandableConferenceViewHolder, ConferenceItemViewHolder> {
    private NavigationContract.Presenter presenter;

    public ExpandableConferenceAdapter(List<ExpandableConference> conferences, NavigationContract.Presenter presenter) {
        super(conferences);
        this.presenter = presenter;
    }

    @Override
    public ExpandableConferenceViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_expandable_conference, parent, false);
        return new ExpandableConferenceViewHolder(itemView);
    }

    @Override
    public ConferenceItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_conference_item, parent, false);
        return new ConferenceItemViewHolder(itemView, presenter);
    }

    @Override
    public void onBindChildViewHolder(ConferenceItemViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        ConferenceItem conferenceItem = ((ConferenceItem) group.getItems().get(childIndex));
        holder.bindItem(conferenceItem);
    }

    @Override
    public void onBindGroupViewHolder(ExpandableConferenceViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.bindConference(((ExpandableConference) group));
    }
}
