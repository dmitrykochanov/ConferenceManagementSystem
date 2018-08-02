package com.dmko.conferencemanagementsystem.data.submissions.entities;

import com.dmko.conferencemanagementsystem.data.user.entities.BriefUser;

import java.util.List;

public class BriefSubmission {

    public interface Statuses {
        int PENDING = 0;
        int REJECT = 1;
        int ACCEPT = 2;
    }

    private long id;
    private String title;
    private BriefUser author;
    private List<BriefUser> reviewers;
    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BriefUser getAuthor() {
        return author;
    }

    public void setAuthor(BriefUser author) {
        this.author = author;
    }

    public List<BriefUser> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<BriefUser> reviewers) {
        this.reviewers = reviewers;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BriefSubmission{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                '}';
    }
}
