package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_students;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschooladmin.Callback.IViewStudentCallbackListener;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Model.StudentModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentsViewModel extends ViewModel implements IViewStudentCallbackListener {
    private MutableLiveData<List<StudentModel>> studentModelListMutable;
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private IViewStudentCallbackListener viewStudentCallbackListener;

    public StudentsViewModel() {
        viewStudentCallbackListener=this;
    }

    public MutableLiveData<List<StudentModel>> getStudentModelListMutable(int clsPosition) {
        if (studentModelListMutable == null) {
            studentModelListMutable = new MutableLiveData<>();
            errorMessage = new MutableLiveData<>();
            loadstudents(clsPosition);
        }
        return studentModelListMutable;
    }

    private void loadstudents(int clsPosition) {
        List<StudentModel> tempList = new ArrayList<>();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Students")
                .whereEqualTo("studentClass", Common.classModels.getValue().get(clsPosition).getClassName())
                .orderBy("studentName")
                .addSnapshotListener((value, error) -> {

                    if (error != null) {
                        Log.d("Student Listen Failed", "" + error);
                        viewStudentCallbackListener.onStudentLoadFailed(error.getMessage());
                        return;
                    }
                    if (value != null && !value.isEmpty()) {
                        tempList.clear();
                        for (QueryDocumentSnapshot documentSnapshot : value) {
                            StudentModel studentModel = documentSnapshot.toObject(StudentModel.class);
                            tempList.add(studentModel);
                        }
                        if (tempList.size() > 0) {
                            Common.studentModel = tempList;
                            viewStudentCallbackListener.onStudentLoadSuccess(tempList);
                        }
                    }
                });
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void onStudentLoadSuccess(List<StudentModel> studentModelList) {
        studentModelListMutable.setValue(studentModelList);
    }

    @Override
    public void onStudentLoadFailed(String error) {
        errorMessage.setValue(error);
    }
}