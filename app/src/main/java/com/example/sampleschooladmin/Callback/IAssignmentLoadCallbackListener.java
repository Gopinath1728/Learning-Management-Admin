package com.example.sampleschooladmin.Callback;



import com.example.sampleschooladmin.Model.AssignmentModel;

import java.util.List;

public interface IAssignmentLoadCallbackListener {
    void onAssignmentLoadSuccess(List<AssignmentModel> assignmentModelList);
    void onAssignmentLoadFailed(String error);
}
