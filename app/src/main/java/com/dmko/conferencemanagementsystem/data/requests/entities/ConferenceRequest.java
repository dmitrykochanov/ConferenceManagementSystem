package com.dmko.conferencemanagementsystem.data.requests.entities;

import com.dmko.conferencemanagementsystem.data.user.entities.BriefUser;

import java.util.List;

public class ConferenceRequest extends BriefConferenceRequest {

    private String acronym;
    private String webPage;
    private String city;
    private String country;
    private List<ConferenceRequestComment> comments;

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

    public List<ConferenceRequestComment> getComments() {
        return comments;
    }

    public void setComments(List<ConferenceRequestComment> comments) {
        this.comments = comments;
    }
}
