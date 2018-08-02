package com.dmko.conferencemanagementsystem.ui.screens.submission.recyclerview;

import com.dmko.conferencemanagementsystem.data.submissions.entities.Document;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ExpandableDocument extends ExpandableGroup<ExpandableReview> {

    private Document document;

    public ExpandableDocument(Document document, List<ExpandableReview> items) {
        super(document.getFilename(), items);
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }
}
