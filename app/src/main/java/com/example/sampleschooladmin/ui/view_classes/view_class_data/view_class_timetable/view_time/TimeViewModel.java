package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_timetable.view_time;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Model.TimeModel;

import java.util.List;

public class TimeViewModel extends ViewModel {
    private MutableLiveData<List<TimeModel>> mutableLiveData;

    public TimeViewModel() {
    }

    public MutableLiveData<List<TimeModel>> getMutableLiveData(int clsPos,int weekPos) {
        if (mutableLiveData == null)
            mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(Common.classModels.getValue().get(clsPos).getTimetable().get(weekPos).getTime());
        return mutableLiveData;
    }
}
