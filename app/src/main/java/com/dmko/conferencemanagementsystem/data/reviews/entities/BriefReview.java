package com.dmko.conferencemanagementsystem.data.reviews.entities;

import com.dmko.conferencemanagementsystem.data.user.entities.BriefUser;

public class BriefReview {

    public interface Statuses {
        int REJECT = 0;
        int PROBABLY_REJECT = 1;
        int NO_DECISION = 2;
        int PROBABLY_ACCEPT = 3;
        int ACCEPT = 4;
    }

    private long id;
    private String title;
    private int status;
    private boolean submitted;
    private BriefUser author;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public BriefUser getAuthor() {
        return author;
    }

    public void setAuthor(BriefUser author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "BriefReview{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", submitted=" + submitted +
                '}';
    }
}
