package com.example.sampleschooladmin.Model;

public class ResultModel {
    private String subjectName,marksObtained,totalMarks,examType,resultDocId;

    public ResultModel() {
    }

    public ResultModel(String subjectName, String marksObtained, String totalMarks, String examType, String resultDocId) {
        this.subjectName = subjectName;
        this.marksObtained = marksObtained;
        this.totalMarks = totalMarks;
        this.examType = examType;
        this.resultDocId = resultDocId;
    }

    public String getResultDocId() {
        return resultDocId;
    }

    public void setResultDocId(String resultDocId) {
        this.resultDocId = resultDocId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(String marksObtained) {
        this.marksObtained = marksObtained;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }
}
