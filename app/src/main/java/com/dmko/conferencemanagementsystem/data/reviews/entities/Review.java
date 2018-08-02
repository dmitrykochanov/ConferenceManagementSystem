package com.dmko.conferencemanagementsystem.data.reviews.entities;

import java.util.Date;

public class Review extends BriefReview {

    private String evaluation;
    private Date date;

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
