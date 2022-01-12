package com.example.sampleschooladmin.Model;

public class TeachingClass {
    private String subjectName,className;

    public TeachingClass() {
    }

    public TeachingClass(String subjectName, String className) {
        this.subjectName = subjectName;
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
