package com.example.sampleschooladmin.Callback;


import com.example.sampleschooladmin.Model.AnnouncementModel;

import java.util.List;

public interface IAnnouncementCallbackListener {
    void onAnnouncementLoadSuccess(List<AnnouncementModel> announcementModelList);
    void onAnnouncementLoadFailed(String announcementLoadError);
}
