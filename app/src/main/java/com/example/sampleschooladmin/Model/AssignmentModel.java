package com.example.sampleschooladmin.Model;

public class AssignmentModel {
    private String teacherName,topic,body,subject,date,assignmentId;

    public AssignmentModel() {
    }

    public AssignmentModel(String teacherName, String topic, String body, String subject, String date, String assignmentId) {
        this.teacherName = teacherName;
        this.topic = topic;
        this.body = body;
        this.subject = subject;
        this.date = date;
        this.assignmentId = assignmentId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
