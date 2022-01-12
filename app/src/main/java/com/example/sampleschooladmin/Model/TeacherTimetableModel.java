package com.example.sampleschooladmin.Model;

import java.util.List;

public class TeacherTimetableModel {
    private String dayName;
    List<TeacherTimeModel> time;

    public TeacherTimetableModel() {
    }

    public TeacherTimetableModel(String dayName, List<TeacherTimeModel> time) {
        this.dayName = dayName;
        this.time = time;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public List<TeacherTimeModel> getTime() {
        return time;
    }

    public void setTime(List<TeacherTimeModel> time) {
        this.time = time;
    }
}
