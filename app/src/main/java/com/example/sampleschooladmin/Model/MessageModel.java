package com.example.sampleschooladmin.Model;

public class MessageModel {
    private String title,body,date,time,msgId,from;

    public MessageModel() {
    }

    public MessageModel(String title, String body, String date, String time, String msgId, String from) {
        this.title = title;
        this.body = body;
        this.date = date;
        this.time = time;
        this.msgId = msgId;
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
