package com.example.sampleschooladmin.ui.view_teachers;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschooladmin.Callback.IViewTeacherCallbackListener;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Model.TeacherModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewTeacherViewModel extends ViewModel {

    private MutableLiveData<List<TeacherModel>> mutableLiveData;

    public ViewTeacherViewModel() {
    }

    public MutableLiveData<List<TeacherModel>> getMutableLiveData() {
        if (mutableLiveData == null)
            mutableLiveData = new MutableLiveData<>();
        mutableLiveData = Common.teacherModel;
        return mutableLiveData;
    }
}