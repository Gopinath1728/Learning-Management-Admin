package com.example.sampleschooladmin.Model;

import java.util.List;

public class ClassModel {
    private String proctorName,proctorImage,className,docId;
    Boolean teachingMode;
    List<SubjectModel> Subjects;
    List<TimetableModel>Timetable;
    List<AssignmentModel> assignments;
    List<ExaminationModel> examinations;

    public ClassModel() {
    }

    public ClassModel(String proctorName, String proctorImage, String className, String docId, Boolean teachingMode, List<SubjectModel> subjects, List<TimetableModel> timetable, List<AssignmentModel> assignments, List<ExaminationModel> examinations) {
        this.proctorName = proctorName;
        this.proctorImage = proctorImage;
        this.className = className;
        this.docId = docId;
        this.teachingMode = teachingMode;
        Subjects = subjects;
        Timetable = timetable;
        this.assignments = assignments;
        this.examinations = examinations;
    }

    public List<ExaminationModel> getExaminations() {
        return examinations;
    }

    public void setExaminations(List<ExaminationModel> examinations) {
        this.examinations = examinations;
    }


    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public List<AssignmentModel> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<AssignmentModel> assignments) {
        this.assignments = assignments;
    }

    public String getProctorName() {
        return proctorName;
    }

    public void setProctorName(String proctorName) {
        this.proctorName = proctorName;
    }

    public String getProctorImage() {
        return proctorImage;
    }

    public void setProctorImage(String proctorImage) {
        this.proctorImage = proctorImage;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Boolean getTeachingMode() {
        return teachingMode;
    }

    public void setTeachingMode(Boolean teachingMode) {
        this.teachingMode = teachingMode;
    }

    public List<SubjectModel> getSubjects() {
        return Subjects;
    }

    public void setSubjects(List<SubjectModel> subjects) {
        Subjects = subjects;
    }

    public List<TimetableModel> getTimetable() {
        return Timetable;
    }

    public void setTimetable(List<TimetableModel> timetable) {
        Timetable = timetable;
    }
}
