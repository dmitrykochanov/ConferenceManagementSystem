package com.dmko.conferencemanagementsystem.ui.screens.submission.recyclerview;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.submissions.entities.Document;
import com.dmko.conferencemanagementsystem.ui.screens.submission.SubmissionContract;
import com.dmko.conferencemanagementsystem.utils.StatusHelper;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpandableDocumentViewHolder extends GroupViewHolder {

    @BindView(R.id.image_expand) ImageView imageExpand;
    @BindView(R.id.text_document) TextView textDocument;
    @BindView(R.id.button_open) Button buttonOpen;
    @BindView(R.id.button_add_review) Button buttonAddReview;
    @BindView(R.id.view_status) View viewStatus;

    private SubmissionContract.Presenter presenter;
    private Document document;

    public ExpandableDocumentViewHolder(View itemView, SubmissionContract.Presenter presenter) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.presenter = presenter;

        buttonAddReview.setOnClickListener(v -> {
            presenter.onAddReviewSelected(document.getId());
        });
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

    public void bindDocument(Document document) {
        this.document = document;
        textDocument.setText(document.getFilename());
        viewStatus.setBackgroundResource(StatusHelper.documentStatusAsColor(document.getStatus()));

        buttonAddReview.setVisibility((presenter.isCurrentUserSubmissionReviewer() && document.getStatus() == Document.Statuses.PENDING) ? View.VISIBLE : View.GONE);
    }
}
