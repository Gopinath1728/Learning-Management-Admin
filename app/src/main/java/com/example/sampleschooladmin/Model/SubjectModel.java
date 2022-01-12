package com.example.sampleschooladmin.Model;

public class SubjectModel {
    private String materialEdtLink,subjectName,subjTeacher,subjTchrImg,subjDocId;

    public SubjectModel() {
    }

    public SubjectModel(String materialEdtLink, String subjectName, String subjTeacher, String subjTchrImg, String subjDocId) {
        this.materialEdtLink = materialEdtLink;
        this.subjectName = subjectName;
        this.subjTeacher = subjTeacher;
        this.subjTchrImg = subjTchrImg;
        this.subjDocId = subjDocId;
    }

    public String getSubjDocId() {
        return subjDocId;
    }

    public void setSubjDocId(String subjDocId) {
        this.subjDocId = subjDocId;
    }

    public String getMaterialEdtLink() {
        return materialEdtLink;
    }

    public void setMaterialEdtLink(String materialEdtLink) {
        this.materialEdtLink = materialEdtLink;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjTeacher() {
        return subjTeacher;
    }

    public void setSubjTeacher(String subjTeacher) {
        this.subjTeacher = subjTeacher;
    }

    public String getSubjTchrImg() {
        return subjTchrImg;
    }

    public void setSubjTchrImg(String subjTchrImg) {
        this.subjTchrImg = subjTchrImg;
    }
}
