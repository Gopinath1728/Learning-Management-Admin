package com.example.sampleschooladmin.Callback;

import com.example.sampleschooladmin.Model.ClassModel;

import java.util.List;

public interface IViewClassesCallbackListener {
    void onClassesLoadSuccess(List<ClassModel> classModelList);
    void onClassesLoadFailed(String error);
}
