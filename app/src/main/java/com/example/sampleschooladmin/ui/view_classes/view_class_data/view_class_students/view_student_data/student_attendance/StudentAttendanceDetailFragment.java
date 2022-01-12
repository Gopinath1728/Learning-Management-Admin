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

import com.example.sampleschooladmin.Adapter.AttendanceDataAdapter;
import com.example.sampleschooladmin.Model.AddAttendanceModel;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.databinding.FragmentStudentAttendanceDetailBinding;

import java.util.List;

public class StudentAttendanceDetailFragment extends Fragment {

    private StudentAttendanceDetailViewModel studentAttendanceDetailViewModel;

    private FragmentStudentAttendanceDetailBinding binding;

    public static StudentAttendanceDetailFragment newInstance() {
        return new StudentAttendanceDetailFragment();
    }

    RecyclerView recycler_attendance_data;
    TextView txt_no_attendance_data;

    AttendanceDataAdapter adapter;

    String subjectName;
    int position;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        studentAttendanceDetailViewModel = new ViewModelProvider(this).get(StudentAttendanceDetailViewModel.class);
        binding = FragmentStudentAttendanceDetailBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        recycler_attendance_data = binding.recyclerAttendanceData;
        txt_no_attendance_data = binding.txtNoAttendanceData;

        if (getArguments() != null)
        {
            subjectName = getArguments().getString("subjectName");
            position = getArguments().getInt("position");

            studentAttendanceDetailViewModel.getMutableLiveData(position).observe(getViewLifecycleOwner(), addAttendanceModels -> {
                if (addAttendanceModels != null && addAttendanceModels.size()>0)
                {
                    txt_no_attendance_data.setVisibility(View.GONE);
                    adapter = new AttendanceDataAdapter(getContext(),addAttendanceModels);
                    recycler_attendance_data.setAdapter(adapter);
                }
            });
            recycler_attendance_data.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}