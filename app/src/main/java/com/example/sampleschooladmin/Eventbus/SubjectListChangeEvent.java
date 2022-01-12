package com.example.sampleschooladmin.Eventbus;

import com.example.sampleschooladmin.Model.SubjectModel;

import java.util.List;

public class SubjectListChangeEvent {
    private List<SubjectModel> subjectModelList;

    public SubjectListChangeEvent(List<SubjectModel> subjectModelList) {
        this.subjectModelList = subjectModelList;
    }

    public List<SubjectModel> getSubjectModelList() {
        return subjectModelList;
    }

    public void setSubjectModelList(List<SubjectModel> subjectModelList) {
        this.subjectModelList = subjectModelList;
    }
}
