package com.example.sampleschooladmin.Common;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.sampleschooladmin.Model.AnnouncementModel;
import com.example.sampleschooladmin.Model.AttendanceModel;
import com.example.sampleschooladmin.Model.ClassModel;
import com.example.sampleschooladmin.Model.StudentModel;
import com.example.sampleschooladmin.Model.SubjectModel;
import com.example.sampleschooladmin.Model.TeacherModel;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Common {
    public static MutableLiveData<List<ClassModel>> classModels;
    public static List<StudentModel> studentModel;
    public static MutableLiveData<List<TeacherModel>> teacherModel;
    public static List<SubjectModel> subjectModels;
    public static List<AnnouncementModel> announcementList;
    public static List<AttendanceModel> myAttendanceList;
    public static Map<String, List<String>> attendanceData = new HashMap<>();


}
