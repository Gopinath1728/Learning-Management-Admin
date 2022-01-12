package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_students.view_student_data.student_results;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschooladmin.Callback.IResultViewCallbackListener;
import com.example.sampleschooladmin.Model.ResultModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentResultsViewModel extends ViewModel implements IResultViewCallbackListener {
    private MutableLiveData<List<ResultModel>> resultMutableLiveData;
    private MutableLiveData<String> resultErrorMutable = new MutableLiveData<>();
    private IResultViewCallbackListener resultViewCallbackListener;

    public StudentResultsViewModel() {
        resultViewCallbackListener=this;
    }

    public MutableLiveData<List<ResultModel>> getResultMutableLiveData(String uid) {
        if (resultMutableLiveData == null) {
            resultMutableLiveData = new MutableLiveData<>();
            resultErrorMutable = new MutableLiveData<>();
            loadResult(uid);
        }
        return resultMutableLiveData;
    }

    private void loadResult(String uid) {
        List<ResultModel> tempList = new ArrayList<>();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Students")
                .document(uid)
                .collection("Results")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.d("Result Listen Failed", "" + error);
                        resultViewCallbackListener.onResultLoadFailed(error.getMessage());
                        return;
                    }
                    if (value != null && !value.isEmpty()) {
                        tempList.clear();
                        for (QueryDocumentSnapshot snapshot : value) {
                            ResultModel resultModel = snapshot.toObject(ResultModel.class);
                            tempList.add(resultModel);
                        }
                        if (tempList.size()>0)
                        {
                            resultViewCallbackListener.onResultLoadSuccess(tempList);
                        }
                    }
                });
    }

    public MutableLiveData<String> getResultErrorMutable() {
        return resultErrorMutable;
    }

    @Override
    public void onResultLoadSuccess(List<ResultModel> resultModelList) {
        resultMutableLiveData.setValue(resultModelList);
    }

    @Override
    public void onResultLoadFailed(String resultLoadError) {
        resultErrorMutable.setValue(resultLoadError);
    }
}