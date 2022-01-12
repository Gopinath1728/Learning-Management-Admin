package com.example.sampleschooladmin.Eventbus;

public class SendMessageEvent {
    private String title,message,token;

    public SendMessageEvent(String title, String message, String token) {
        this.title = title;
        this.message = message;
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
