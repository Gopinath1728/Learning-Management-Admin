package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_students.view_student_data.student_attendance;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sampleschooladmin.Adapter.AttendanceListAdapter;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Model.AttendanceModel;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.databinding.FragmentStudentAttendanceBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentAttendanceFragment extends Fragment {

    private StudentAttendanceViewModel studentAttendanceViewModel;

    private FragmentStudentAttendanceBinding binding;

    public static StudentAttendanceFragment newInstance() {
        return new StudentAttendanceFragment();
    }

    RecyclerView recycler_attendance;
    TextView txt_no_attendance;
    AttendanceListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        studentAttendanceViewModel = new ViewModelProvider(this).get(StudentAttendanceViewModel.class);
        binding = FragmentStudentAttendanceBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        recycler_attendance= binding.recyclerAttendance;
        txt_no_attendance = binding.txtNoAttendance;

        if (getArguments() != null)
        {
            String uid = getArguments().getString("studentUid");

            studentAttendanceViewModel.getAttendanceErrorMessage().observe(getViewLifecycleOwner(), s -> Snackbar.make(root,""+s,Snackbar.LENGTH_LONG).show());

            studentAttendanceViewModel.getAttendanceMutableLiveData(uid).observe(getViewLifecycleOwner(), attendanceModels -> {
                if (attendanceModels != null && attendanceModels.size()>0)
                {
                    Map<String, List<String>> attendanceData = new HashMap<>();
                    for (int i=0;i<attendanceModels.size();i++)
                    {
                        List<String>attendance = new ArrayList<>();
                        for (int j=0;j<attendanceModels.get(i).getAttendance().size();j++)
                        {
                            attendance.add(attendanceModels.get(i).getAttendance().get(j).getStatus());
                        }
                        attendanceData.put(attendanceModels.get(i).getSubjectName(),attendance);
                    }
                    txt_no_attendance.setVisibility(View.GONE);
                    adapter = new AttendanceListAdapter(getContext(),attendanceData,attendanceModels);
                    recycler_attendance.setAdapter(adapter);
                }
            });

            recycler_attendance.setHasFixedSize(true);
            recycler_attendance.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));


        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}