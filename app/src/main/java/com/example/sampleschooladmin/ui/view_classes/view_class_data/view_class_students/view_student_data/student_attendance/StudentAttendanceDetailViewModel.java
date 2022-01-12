package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_students.view_student_data.student_attendance;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Model.AddAttendanceModel;

import java.util.List;

public class StudentAttendanceDetailViewModel extends ViewModel {
    private MutableLiveData<List<AddAttendanceModel>> mutableLiveData;

    public StudentAttendanceDetailViewModel() {
    }

    public MutableLiveData<List<AddAttendanceModel>> getMutableLiveData(int pos) {
        if (mutableLiveData == null)
            mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(Common.myAttendanceList.get(pos).getAttendance());
        return mutableLiveData;
    }
}