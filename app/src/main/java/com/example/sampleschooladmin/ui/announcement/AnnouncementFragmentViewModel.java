package com.example.sampleschooladmin.ui.announcement;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschooladmin.Callback.IAnnouncementCallbackListener;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Model.AnnouncementModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementFragmentViewModel extends ViewModel implements IAnnouncementCallbackListener {

    private MutableLiveData<List<AnnouncementModel>> announcementListMutableLiveData;
    private MutableLiveData<String> announcementErrorMessage = new MutableLiveData<>();
    private IAnnouncementCallbackListener iAnnouncementCallbackListener;

    public AnnouncementFragmentViewModel() {
        iAnnouncementCallbackListener=this;
    }

    public MutableLiveData<List<AnnouncementModel>> getAnnouncementListMutableLiveData() {

        if (announcementListMutableLiveData == null) {
            announcementListMutableLiveData = new MutableLiveData<>();
            announcementErrorMessage = new MutableLiveData<>();
            loadAnnouncements();
        }
        return announcementListMutableLiveData;
    }

    private void loadAnnouncements() {
        List<AnnouncementModel> tempList = new ArrayList<>();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Announcements")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.d("Classes Listen Failed", "" + error);
                        iAnnouncementCallbackListener.onAnnouncementLoadFailed(error.getMessage());
                        return;
                    }
                    if (value != null && !value.isEmpty()) {
                        tempList.clear();
                        for (QueryDocumentSnapshot snapshot : value) {
                            AnnouncementModel announcementModel = snapshot.toObject(AnnouncementModel.class);
                            tempList.add(announcementModel);
                        }
                        if (tempList.size()>0)
                        {
                            Common.announcementList = tempList;
                            iAnnouncementCallbackListener.onAnnouncementLoadSuccess(tempList);
                        }

                    }
                });
    }

    public MutableLiveData<String> getAnnouncementErrorMessage() {
        return announcementErrorMessage;
    }

    @Override
    public void onAnnouncementLoadSuccess(List<AnnouncementModel> announcementModelList) {
        announcementListMutableLiveData.setValue(announcementModelList);
    }

    @Override
    public void onAnnouncementLoadFailed(String announcementLoadError) {
        announcementErrorMessage.setValue(announcementLoadError);
    }
}
