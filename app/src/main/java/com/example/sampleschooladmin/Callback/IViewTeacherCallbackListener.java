package com.example.sampleschooladmin.Callback;

import com.example.sampleschooladmin.Model.TeacherModel;

import java.util.List;

public interface IViewTeacherCallbackListener {
    void onTeacherLoadSuccess(List<TeacherModel> teacherModelList);
    void onTeacherLoadFailed(String error);
}
