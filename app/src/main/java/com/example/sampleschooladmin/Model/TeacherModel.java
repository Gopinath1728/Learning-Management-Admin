package com.example.sampleschooladmin.Model;

import java.util.List;

public class TeacherModel {
    private String uid,tUid,teacherName,teacherPhone,
            teacherAddress,teacherEmail,teacherPassword,teacherImage,proctorClass,teacherToken;
    private Boolean proctor;
    List<TeachingClass> teachingClasses;
    List<TeacherTimetableModel> timetable;

    public TeacherModel() {
    }

    public TeacherModel(String uid, String tUid, String teacherName, String teacherPhone, String teacherAddress, String teacherEmail, String teacherPassword, String teacherImage, String proctorClass, String teacherToken, Boolean proctor, List<TeachingClass> teachingClasses, List<TeacherTimetableModel> timetable) {
        this.uid = uid;
        this.tUid = tUid;
        this.teacherName = teacherName;
        this.teacherPhone = teacherPhone;
        this.teacherAddress = teacherAddress;
        this.teacherEmail = teacherEmail;
        this.teacherPassword = teacherPassword;
        this.teacherImage = teacherImage;
        this.proctorClass = proctorClass;
        this.teacherToken = teacherToken;
        this.proctor = proctor;
        this.teachingClasses = teachingClasses;
        this.timetable = timetable;
    }

    public List<TeacherTimetableModel> getTimetable() {
        return timetable;
    }

    public void setTimetable(List<TeacherTimetableModel> timetable) {
        this.timetable = timetable;
    }

    public String getTeacherToken() {
        return teacherToken;
    }

    public void setTeacherToken(String teacherToken) {
        this.teacherToken = teacherToken;
    }

    public Boolean getProctor() {
        return proctor;
    }

    public void setProctor(Boolean proctor) {
        this.proctor = proctor;
    }

    public String getProctorClass() {
        return proctorClass;
    }

    public void setProctorClass(String proctorClass) {
        this.proctorClass = proctorClass;
    }

    public List<TeachingClass> getTeachingClasses() {
        return teachingClasses;
    }

    public void setTeachingClasses(List<TeachingClass> teachingClasses) {
        this.teachingClasses = teachingClasses;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String gettUid() {
        return tUid;
    }

    public void settUid(String tUid) {
        this.tUid = tUid;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public String getTeacherAddress() {
        return teacherAddress;
    }

    public void setTeacherAddress(String teacherAddress) {
        this.teacherAddress = teacherAddress;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }

    public String getTeacherImage() {
        return teacherImage;
    }

    public void setTeacherImage(String teacherImage) {
        this.teacherImage = teacherImage;
    }
}
