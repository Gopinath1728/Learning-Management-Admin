package com.example.sampleschooladmin.Model;

public class AnnouncementModel {
    private String announcementTitle, announcementBody,date,owner,announceId;

    public AnnouncementModel() {
    }

    public AnnouncementModel(String announcementTitle, String announcementBody, String date, String owner, String announceId) {
        this.announcementTitle = announcementTitle;
        this.announcementBody = announcementBody;
        this.date = date;
        this.owner = owner;
        this.announceId = announceId;
    }

    public String getAnnounceId() {
        return announceId;
    }

    public void setAnnounceId(String announceId) {
        this.announceId = announceId;
    }

    public String getAnnouncementTitle() {
        return announcementTitle;
    }

    public void setAnnouncementTitle(String announcementTitle) {
        this.announcementTitle = announcementTitle;
    }

    public String getAnnouncementBody() {
        return announcementBody;
    }

    public void setAnnouncementBody(String announcementBody) {
        this.announcementBody = announcementBody;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
