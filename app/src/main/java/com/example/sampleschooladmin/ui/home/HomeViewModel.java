package com.example.sampleschooladmin.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschooladmin.Callback.IAnnouncementCallbackListener;
import com.example.sampleschooladmin.Callback.IViewClassesCallbackListener;
import com.example.sampleschooladmin.Callback.IViewTeacherCallbackListener;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Model.AnnouncementModel;
import com.example.sampleschooladmin.Model.ClassModel;
import com.example.sampleschooladmin.Model.TeacherModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel implements IViewClassesCallbackListener, IViewTeacherCallbackListener {

    private MutableLiveData<List<ClassModel>> classModelListMutable;
    private MutableLiveData<List<TeacherModel>> teacherModelListMutable;

    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private IViewClassesCallbackListener classesCallbackListener;
    private IViewTeacherCallbackListener viewTeacherCallbackListener;

    private MutableLiveData<String> teacherError = new MutableLiveData<>();

    public HomeViewModel() {
        classesCallbackListener = this;
        viewTeacherCallbackListener=this;
    }

    public MutableLiveData<List<ClassModel>> getClassModelListMutable() {
        if (classModelListMutable == null) {
            classModelListMutable = new MutableLiveData<>();
            errorMessage = new MutableLiveData<>();
            loadclasses();
        }
        Common.classModels=classModelListMutable;
        return classModelListMutable;
    }

    public MutableLiveData<List<TeacherModel>> getTeacherModelListMutable() {
        if (teacherModelListMutable == null) {
            teacherModelListMutable = new MutableLiveData<>();
            errorMessage = new MutableLiveData<>();
            loadteachers();
        }
        Common.teacherModel = teacherModelListMutable;
        return teacherModelListMutable;
    }

    private void loadteachers() {
        List<TeacherModel> tempList = new ArrayList<>();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Teachers")
                .orderBy("teacherName")
                .addSnapshotListener((value, error) -> {

                    if (error != null) {
                        Log.d("Teacher Listen Failed",""+error);
                        return;
                    }
                    if (value != null) {
                        tempList.clear();
                        List<TeacherModel> teacherModelList = value.toObjects(TeacherModel.class);
                        tempList.addAll(teacherModelList);
                        if (tempList.size() > 0)
                            viewTeacherCallbackListener.onTeacherLoadSuccess(tempList);
                        else
                            viewTeacherCallbackListener.onTeacherLoadFailed("No User found");
                    }
                });

    }

    public MutableLiveData<String> getTeacherError() {
        return teacherError;
    }

    private void loadclasses() {
        List<ClassModel> tempList = new ArrayList<>();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Classes")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.d("Classes Listen Failed", "" + error);
                        classesCallbackListener.onClassesLoadFailed(error.getMessage());
                        return;
                    }
                    if (value != null && !value.isEmpty()) {
                        tempList.clear();
                        for (QueryDocumentSnapshot snapshot : value) {
                            ClassModel classModel = snapshot.toObject(ClassModel.class);
                            tempList.add(classModel);
                        }
                        classesCallbackListener.onClassesLoadSuccess(tempList);
                    }
                });
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }



    @Override
    public void onClassesLoadSuccess(List<ClassModel> classModelList) {
        classModelListMutable.setValue(classModelList);
    }

    @Override
    public void onClassesLoadFailed(String error) {
        errorMessage.setValue(error);
    }

    @Override
    public void onTeacherLoadSuccess(List<TeacherModel> teacherModelList) {
        teacherModelListMutable.setValue(teacherModelList);
    }

    @Override
    public void onTeacherLoadFailed(String error) {
        teacherError.setValue(error);
    }


}