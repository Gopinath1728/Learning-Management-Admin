package com.example.sampleschooladmin.ui.view_teachers;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Model.TeachingClass;

import java.util.List;

public class TeacherProfileViewModel extends ViewModel {
    private MutableLiveData<List<TeachingClass>> teachingClassMutableLiveData;

    public TeacherProfileViewModel() {
    }

    public MutableLiveData<List<TeachingClass>> getTeachingClassMutableLiveData(int position) {
        if (teachingClassMutableLiveData == null)
            teachingClassMutableLiveData = new MutableLiveData<>();
        teachingClassMutableLiveData.setValue(Common.teacherModel.getValue().get(position).getTeachingClasses());
        return teachingClassMutableLiveData;
    }
}
