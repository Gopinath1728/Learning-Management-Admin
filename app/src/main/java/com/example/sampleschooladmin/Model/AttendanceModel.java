package com.example.sampleschooladmin.Model;

import java.util.List;

public class AttendanceModel {
    private String subjectName;
    List<AddAttendanceModel> attendance;

    public AttendanceModel() {
    }

    public AttendanceModel(String subjectName, List<AddAttendanceModel> attendance) {
        this.subjectName = subjectName;
        this.attendance = attendance;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<AddAttendanceModel> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<AddAttendanceModel> attendance) {
        this.attendance = attendance;
    }
}
