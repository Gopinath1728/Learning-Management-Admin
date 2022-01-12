package com.example.sampleschooladmin.Callback;

import com.example.sampleschooladmin.Model.SubjectModel;

import java.util.List;

public interface IViewClassSubjectsModelCallbackListener {
    void onSubjectLoadSuccess(List<SubjectModel> subjectModelList);
    void onSubjectLoadFailed(String subjectLoadError);
}
