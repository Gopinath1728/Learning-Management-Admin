package com.example.sampleschooladmin.Model;

public class StudentModel {
    private String uid,studentUid,studentName,studentEmail,studentPassword,studentClass,studentAddress,studentParentName,studentParentPhone,studentImage,studentToken,studentRollNo;
    private Boolean classMonitor;

    public StudentModel() {
    }

    public StudentModel(String uid, String studentUid, String studentName, String studentEmail, String studentPassword, String studentClass, String studentAddress, String studentParentName, String studentParentPhone, String studentImage, String studentToken, String studentRollNo, Boolean classMonitor) {
        this.uid = uid;
        this.studentUid = studentUid;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentPassword = studentPassword;
        this.studentClass = studentClass;
        this.studentAddress = studentAddress;
        this.studentParentName = studentParentName;
        this.studentParentPhone = studentParentPhone;
        this.studentImage = studentImage;
        this.studentToken = studentToken;
        this.studentRollNo = studentRollNo;
        this.classMonitor = classMonitor;
    }

    public Boolean getClassMonitor() {
        return classMonitor;
    }

    public void setClassMonitor(Boolean classMonitor) {
        this.classMonitor = classMonitor;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStudentUid() {
        return studentUid;
    }

    public void setStudentUid(String studentUid) {
        this.studentUid = studentUid;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    public String getStudentParentName() {
        return studentParentName;
    }

    public void setStudentParentName(String studentParentName) {
        this.studentParentName = studentParentName;
    }

    public String getStudentParentPhone() {
        return studentParentPhone;
    }

    public void setStudentParentPhone(String studentParentPhone) {
        this.studentParentPhone = studentParentPhone;
    }

    public String getStudentImage() {
        return studentImage;
    }

    public void setStudentImage(String studentImage) {
        this.studentImage = studentImage;
    }

    public String getStudentToken() {
        return studentToken;
    }

    public void setStudentToken(String studentToken) {
        this.studentToken = studentToken;
    }

    public String getStudentRollNo() {
        return studentRollNo;
    }

    public void setStudentRollNo(String studentRollNo) {
        this.studentRollNo = studentRollNo;
    }
}
