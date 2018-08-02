package com.dmko.conferencemanagementsystem.ui.screens.navigation.recyclerview;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ExpandableConference extends ExpandableGroup<ConferenceItem> {

    public ExpandableConference(String title, List<ConferenceItem> items) {
        super(title, items);
    }
}
