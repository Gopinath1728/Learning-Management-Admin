package com.example.sampleschooladmin.Model;

public class TimeModel {
    private String timeFrom,timeTo,subjName,status;


    public TimeModel() {
    }

    public TimeModel(String timeFrom, String timeTo, String subjName, String status) {
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.subjName = subjName;
        this.status = status;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getSubjName() {
        return subjName;
    }

    public void setSubjName(String subjName) {
        this.subjName = subjName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
