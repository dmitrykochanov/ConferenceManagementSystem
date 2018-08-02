package com.dmko.conferencemanagementsystem.ui.screens.addeditconferencerequest.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.requests.entities.ConferenceRequestComment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_comment) TextView textComment;

    public CommentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindComment(ConferenceRequestComment comment) {
        textComment.setText(comment.getContent());
    }

}
