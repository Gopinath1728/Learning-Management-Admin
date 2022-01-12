package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_timetable;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Model.TimetableModel;

import java.util.List;

public class ViewClassTimetableViewModel extends ViewModel {
    private MutableLiveData<List<TimetableModel>> mutableLiveData;

    public ViewClassTimetableViewModel() {
    }

    public MutableLiveData<List<TimetableModel>> getMutableLiveData(int position) {
        if (mutableLiveData == null)
            mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(Common.classModels.getValue().get(position).getTimetable());
        return mutableLiveData;
    }
}