package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_assignments;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschooladmin.Callback.IAssignmentLoadCallbackListener;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Model.AssignmentModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AssignmentsViewModel extends ViewModel implements IAssignmentLoadCallbackListener {
    private MutableLiveData<List<AssignmentModel>> assignmentMutableLiveData;
    private MutableLiveData<String> assignmentErrorMessage = new MutableLiveData<>();
    private IAssignmentLoadCallbackListener assignmentLoadCallbackListener;

    public AssignmentsViewModel() {
        assignmentLoadCallbackListener=this;
    }


    public MutableLiveData<List<AssignmentModel>> getAssignmentMutableLiveData(int pos) {
        if (assignmentMutableLiveData == null) {
            assignmentMutableLiveData = new MutableLiveData<>();
            assignmentErrorMessage = new MutableLiveData<>();
            loadAssignments(pos);
        }
        return assignmentMutableLiveData;
    }

    public MutableLiveData<String> getAssignmentErrorMessage() {
        return assignmentErrorMessage;
    }

    private void loadAssignments(int pos) {
        List<AssignmentModel> assignmentModelList = new ArrayList<>();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Classes")
                .document(Common.classModels.getValue().get(pos).getDocId())
                .collection("Assignments")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.d("Classes Listen Failed", "" + error);
                        assignmentLoadCallbackListener.onAssignmentLoadFailed(error.getMessage());
                        return;
                    }
                    if (value != null) {
                        assignmentModelList.clear();
                        for (QueryDocumentSnapshot snapshot : value)
                        {
                            AssignmentModel assignmentModel = snapshot.toObject(AssignmentModel.class);
                            assignmentModelList.add(assignmentModel);
                        }
                        if (assignmentModelList.size()>0)
                        {
                            assignmentLoadCallbackListener.onAssignmentLoadSuccess(assignmentModelList);
                        }
                    }
                });
    }

    @Override
    public void onAssignmentLoadSuccess(List<AssignmentModel> assignmentModelList) {
        assignmentMutableLiveData.setValue(assignmentModelList);
    }

    @Override
    public void onAssignmentLoadFailed(String error) {
        assignmentErrorMessage.setValue(error);
    }
}