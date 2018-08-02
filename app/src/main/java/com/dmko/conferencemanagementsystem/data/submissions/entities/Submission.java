package com.dmko.conferencemanagementsystem.data.submissions.entities;

import java.util.List;

public class Submission extends BriefSubmission {

    private List<Document> documents;
    private long conferenceId;
    private boolean reviewable;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public boolean isReviewable() {
        return reviewable;
    }

    public void setReviewable(boolean reviewable) {
        this.reviewable = reviewable;
    }
}
