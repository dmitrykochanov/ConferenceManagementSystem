package com.dmko.conferencemanagementsystem.data.requests.entities;

import java.util.Date;

public class ConferenceRequestComment {

    public interface Statuses {
        int PENDING = 0;
        int DECLINED = 1;
        int ACCEPTED = 2;
    }

    private long id;
    private Date date;
    private String content;
    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ConferenceRequestComment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", status=" + status +
                '}';
    }
}
