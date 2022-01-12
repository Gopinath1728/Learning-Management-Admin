package com.example.sampleschooladmin.Model;

public class AdminModel {
    private String uid,type,token;

    public AdminModel() {
    }

    public AdminModel(String uid, String type, String token) {
        this.uid = uid;
        this.type = type;
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
