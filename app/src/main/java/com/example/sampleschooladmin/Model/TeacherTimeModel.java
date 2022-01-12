package com.example.sampleschooladmin.Model;

public class TeacherTimeModel {
    private String timeFrom,timeTo,subjName,className;


    public TeacherTimeModel() {
    }

    public TeacherTimeModel(String timeFrom, String timeTo, String subjName, String className) {
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.subjName = subjName;
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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


}
