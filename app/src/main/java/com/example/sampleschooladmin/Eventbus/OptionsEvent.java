package com.example.sampleschooladmin.Eventbus;

public class OptionsEvent {
    int position;
    String text;
    Boolean delete;

    public OptionsEvent(int position, String text, Boolean delete) {
        this.position = position;
        this.text = text;
        this.delete = delete;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
