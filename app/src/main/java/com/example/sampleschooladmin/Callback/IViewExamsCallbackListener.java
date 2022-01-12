package com.example.sampleschooladmin.Callback;

import com.example.sampleschooladmin.Model.ExaminationModel;

import java.util.List;

public interface IViewExamsCallbackListener {
    void onExamsLoadSuccess(List<ExaminationModel> examinationModels);
    void onExamsLoadFailed(String examLoadError);
}
