package com.example.sampleschooladmin.Callback;



import com.example.sampleschooladmin.Model.AttendanceModel;

import java.util.List;

public interface IAttendanceViewCallback {
    void onAttendanceLoadSuccess(List<AttendanceModel> attendanceModelList);
    void onAttendanceLoadFailed(String attendanceLoadError);
}
