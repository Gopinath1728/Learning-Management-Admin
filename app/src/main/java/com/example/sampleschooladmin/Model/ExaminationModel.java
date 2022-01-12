package com.example.sampleschooladmin.Model;

public class ExaminationModel {
    private String examSbjtName,examTimeFrom,examTimeTo,examDate,examRoom,examDocId,examType;

    public ExaminationModel() {
    }

    public ExaminationModel(String examSbjtName, String examTimeFrom, String examTimeTo, String examDate, String examRoom, String examDocId, String examType) {
        this.examSbjtName = examSbjtName;
        this.examTimeFrom = examTimeFrom;
        this.examTimeTo = examTimeTo;
        this.examDate = examDate;
        this.examRoom = examRoom;
        this.examDocId = examDocId;
        this.examType = examType;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getExamSbjtName() {
        return examSbjtName;
    }

    public void setExamSbjtName(String examSbjtName) {
        this.examSbjtName = examSbjtName;
    }

    public String getExamTimeFrom() {
        return examTimeFrom;
    }

    public void setExamTimeFrom(String examTimeFrom) {
        this.examTimeFrom = examTimeFrom;
    }

    public String getExamTimeTo() {
        return examTimeTo;
    }

    public void setExamTimeTo(String examTimeTo) {
        this.examTimeTo = examTimeTo;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getExamRoom() {
        return examRoom;
    }

    public void setExamRoom(String examRoom) {
        this.examRoom = examRoom;
    }

    public String getExamDocId() {
        return examDocId;
    }

    public void setExamDocId(String examDocId) {
        this.examDocId = examDocId;
    }
}
