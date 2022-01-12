package com.example.sampleschooladmin.Model;

public class AddAttendanceModel {
    private String todayDate,tchName,status;

    public AddAttendanceModel() {
    }

    public AddAttendanceModel(String todayDate, String tchName, String status) {
        this.todayDate = todayDate;
        this.tchName = tchName;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getTchName() {
        return tchName;
    }

    public void setTchName(String tchName) {
        this.tchName = tchName;
    }
}
