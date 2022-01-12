package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_exams;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschooladmin.Callback.IViewExamsCallbackListener;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Model.ClassModel;
import com.example.sampleschooladmin.Model.ExaminationModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ClassExamsViewModel extends ViewModel implements IViewExamsCallbackListener {
    private MutableLiveData<List<ExaminationModel>> examMutableLiveData;
    private MutableLiveData<String> errorMutable = new MutableLiveData<>();
    private IViewExamsCallbackListener iViewExamsCallbackListener;

    public ClassExamsViewModel() {
        iViewExamsCallbackListener=this;
    }

    public MutableLiveData<List<ExaminationModel>> getExamMutableLiveData(int pos) {
        if (examMutableLiveData == null) {
            examMutableLiveData = new MutableLiveData<>();
            errorMutable = new MutableLiveData<>();
            loadExams(pos);
        }
        return examMutableLiveData;
    }

    private void loadExams(int pos) {
        List<ExaminationModel> tempList = new ArrayList<>();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Classes")
                .document(Common.classModels.getValue().get(pos).getDocId())
                .collection("Examinations")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.d("Exams Listen Failed", "" + error);
                        iViewExamsCallbackListener.onExamsLoadFailed(error.getMessage());
                        return;
                    }
                    if (value != null && !value.isEmpty()) {
                        tempList.clear();
                        for (QueryDocumentSnapshot snapshot : value) {
                            ExaminationModel examinationModel = snapshot.toObject(ExaminationModel.class);
                            tempList.add(examinationModel);
                        }
                        iViewExamsCallbackListener.onExamsLoadSuccess(tempList);
                    }
                });
    }

    public MutableLiveData<String> getErrorMutable() {
        return errorMutable;
    }

    @Override
    public void onExamsLoadSuccess(List<ExaminationModel> examinationModels) {
        examMutableLiveData.setValue(examinationModels);
    }

    @Override
    public void onExamsLoadFailed(String examLoadError) {
errorMutable.setValue(examLoadError);
    }
}