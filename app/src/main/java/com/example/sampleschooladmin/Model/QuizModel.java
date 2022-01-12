package com.example.sampleschooladmin.Model;

import java.util.List;

public class QuizModel {
    private String question,crtOption,timePerQ;
    private List<String> options;


    public QuizModel() {
    }

    public QuizModel(String question, String crtOption, String timePerQ, List<String> options) {
        this.question = question;
        this.crtOption = crtOption;
        this.timePerQ = timePerQ;
        this.options = options;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCrtOption() {
        return crtOption;
    }

    public void setCrtOption(String crtOption) {
        this.crtOption = crtOption;
    }

    public String getTimePerQ() {
        return timePerQ;
    }

    public void setTimePerQ(String timePerQ) {
        this.timePerQ = timePerQ;
    }

}
