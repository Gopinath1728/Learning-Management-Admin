package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_subjects;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschooladmin.Callback.IViewClassSubjectsModelCallbackListener;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Model.ClassModel;
import com.example.sampleschooladmin.Model.SubjectModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewClassSubjectsViewModel extends ViewModel implements IViewClassSubjectsModelCallbackListener {
    private MutableLiveData<List<SubjectModel>> listMutableLiveData;
    private MutableLiveData<String> errorMutable = new MutableLiveData<>();
    private IViewClassSubjectsModelCallbackListener iViewClassSubjectsModelCallbackListener;

    public ViewClassSubjectsViewModel() {
        iViewClassSubjectsModelCallbackListener=this;
    }

    public MutableLiveData<List<SubjectModel>> getListMutableLiveData(int position) {
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<>();
            errorMutable = new MutableLiveData<>();
            loadSubjects(position);
        }
        return listMutableLiveData;
    }

    public MutableLiveData<String> getErrorMutable() {
        return errorMutable;
    }

    private void loadSubjects(int position) {
        List<SubjectModel> tempList = new ArrayList<>();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Classes")
                .document(Common.classModels.getValue().get(position).getDocId())
                .collection("Subjects")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.d("Classes Listen Failed", "" + error);
                        iViewClassSubjectsModelCallbackListener.onSubjectLoadFailed(error.getMessage());
                        return;
                    }
                    if (value != null && !value.isEmpty()) {
                        tempList.clear();
                        for (QueryDocumentSnapshot snapshot : value) {
                            SubjectModel subjectModel = snapshot.toObject(SubjectModel.class);
                            tempList.add(subjectModel);
                        }
                        iViewClassSubjectsModelCallbackListener.onSubjectLoadSuccess(tempList);
                    }
                });
    }


    @Override
    public void onSubjectLoadSuccess(List<SubjectModel> subjectModelList) {
        listMutableLiveData.setValue(subjectModelList);
    }

    @Override
    public void onSubjectLoadFailed(String subjectLoadError) {
        errorMutable.setValue(subjectLoadError);
    }
}