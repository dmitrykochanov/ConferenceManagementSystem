package com.dmko.conferencemanagementsystem.data.conferences.entities;


import com.dmko.conferencemanagementsystem.data.submissions.entities.BriefSubmission;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUser;

import java.util.Date;
import java.util.List;

public class Conference extends BriefConference {

    private String acronym;
    private String webPage;
    private String city;
    private String country;
    private List<BriefSubmission> submissions;
    private List<BriefUser> reviewers;
    private BriefUser organizer;

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<BriefSubmission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<BriefSubmission> submissions) {
        this.submissions = submissions;
    }

    public List<BriefUser> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<BriefUser> reviewers) {
        this.reviewers = reviewers;
    }

    public BriefUser getOrganizer() {
        return organizer;
    }

    public void setOrganizer(BriefUser organizer) {
        this.organizer = organizer;
    }
}
