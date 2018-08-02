package com.dmko.conferencemanagementsystem.ui.screens.submission.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.reviews.entities.BriefReview;
import com.dmko.conferencemanagementsystem.data.submissions.entities.Document;
import com.dmko.conferencemanagementsystem.ui.screens.submission.SubmissionContract;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ExpandableDocumentAdapter extends ExpandableRecyclerViewAdapter<ExpandableDocumentViewHolder, ExpandableReviewViewHolder> {

    private SubmissionContract.Presenter presenter;

    public ExpandableDocumentAdapter(List<? extends ExpandableGroup> groups, SubmissionContract.Presenter presenter) {
        super(groups);
        this.presenter = presenter;
    }

    @Override
    public ExpandableDocumentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_expandable_document, parent, false);
        return new ExpandableDocumentViewHolder(itemView, presenter);
    }

    @Override
    public ExpandableReviewViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_expandable_review, parent, false);
        return new ExpandableReviewViewHolder(itemView, presenter);
    }

    @Override
    public void onBindChildViewHolder(ExpandableReviewViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        BriefReview review = ((ExpandableDocument) group).getItems().get(childIndex).getReview();
        holder.bindReview(review);
    }

    @Override
    public void onBindGroupViewHolder(ExpandableDocumentViewHolder holder, int flatPosition, ExpandableGroup group) {
        Document document = ((ExpandableDocument) group).getDocument();
        holder.bindDocument(document);
    }
}
