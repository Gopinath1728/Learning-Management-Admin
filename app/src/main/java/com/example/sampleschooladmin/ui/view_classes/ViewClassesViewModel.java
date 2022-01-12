package com.example.sampleschooladmin.ui.view_classes;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.sampleschooladmin.Callback.IViewClassesCallbackListener;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Model.ClassModel;
import com.example.sampleschooladmin.Model.TeacherModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewClassesViewModel extends ViewModel{
    private MutableLiveData<List<ClassModel>> mutableLiveData;

    public ViewClassesViewModel() {
    }

    public MutableLiveData<List<ClassModel>> getMutableLiveData() {
        if (mutableLiveData == null)
            mutableLiveData = new MutableLiveData<>();
        mutableLiveData = Common.classModels;
        return mutableLiveData;
    }
}