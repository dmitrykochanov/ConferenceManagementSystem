package com.dmko.conferencemanagementsystem.data.submissions.entities;

import com.dmko.conferencemanagementsystem.data.reviews.entities.BriefReview;

import java.util.List;

public class Document {

    public interface Statuses {
        int PENDING = 0;
        int REJECT = 1;
        int ACCEPT = 2;
    }

    private long id;
    private String filename;
    private int status;
    private List<BriefReview> reviews;
    private boolean reviewable;
    private long submissionId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<BriefReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<BriefReview> reviews) {
        this.reviews = reviews;
    }

    public boolean isReviewable() {
        return reviewable;
    }

    public void setReviewable(boolean reviewable) {
        this.reviewable = reviewable;
    }

    public long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", status=" + status +
                ", reviewable=" + reviewable +
                '}';
    }
}
