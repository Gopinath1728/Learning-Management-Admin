package com.example.sampleschooladmin.Model;

import java.util.List;

public class TimetableModel {
    private String dayName;
    List<TimeModel> time;

    public TimetableModel() {
    }

    public TimetableModel(String dayName, List<TimeModel> time) {
        this.dayName = dayName;
        this.time = time;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public List<TimeModel> getTime() {
        return time;
    }

    public void setTime(List<TimeModel> time) {
        this.time = time;
    }
}
