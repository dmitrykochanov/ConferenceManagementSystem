package com.dmko.conferencemanagementsystem.data.requests.entities;

import com.dmko.conferencemanagementsystem.data.user.entities.BriefUser;

public class BriefConferenceRequest {

    public interface Statuses {
        int PENDING = 0;
        int DECLINED = 1;
        int ACCEPTED = 2;
    }

    private Long id;
    private String title;
    private BriefUser organizer;
    private int status;
    private Long conferenceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BriefUser getOrganizer() {
        return organizer;
    }

    public void setOrganizer(BriefUser organizer) {
        this.organizer = organizer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    @Override
    public String toString() {
        return "BriefConferenceRequest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                '}';
    }
}
