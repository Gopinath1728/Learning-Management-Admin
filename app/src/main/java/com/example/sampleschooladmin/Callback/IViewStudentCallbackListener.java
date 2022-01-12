package com.example.sampleschooladmin.Callback;


import com.example.sampleschooladmin.Model.StudentModel;

import java.util.List;

public interface IViewStudentCallbackListener {
    void onStudentLoadSuccess(List<StudentModel> studentModelList);
    void onStudentLoadFailed(String error);
}
