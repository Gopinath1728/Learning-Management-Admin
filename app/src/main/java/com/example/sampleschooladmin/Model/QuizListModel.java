package com.example.sampleschooladmin.Model;

import java.util.List;

public class QuizListModel {
    String teacherName,subject,date,quizDocId;
    List<QuizModel> quiz;

    public QuizListModel() {
    }

    public QuizListModel(String teacherName, String subject, String date, String quizDocId, List<QuizModel> quiz) {
        this.teacherName = teacherName;
        this.subject = subject;
        this.date = date;
        this.quizDocId = quizDocId;
        this.quiz = quiz;
    }

    public String getQuizDocId() {
        return quizDocId;
    }

    public void setQuizDocId(String quizDocId) {
        this.quizDocId = quizDocId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<QuizModel> getQuiz() {
        return quiz;
    }

    public void setQuiz(List<QuizModel> quiz) {
        this.quiz = quiz;
    }
}
