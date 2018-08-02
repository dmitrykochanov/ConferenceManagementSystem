package com.dmko.conferencemanagementsystem.data.conferences.entities;

public class BriefConference {

    private long id;
    private String title;
    //private Date expirationDate;

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

    /*public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }*/

    @Override
    public String toString() {
        return "BriefConference{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
