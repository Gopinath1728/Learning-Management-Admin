package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_students.view_student_data.student_results;

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

import com.example.sampleschooladmin.Adapter.ResultViewAdapter;
import com.example.sampleschooladmin.Model.ResultModel;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.databinding.FragmentStudentResultsBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class StudentResultsFragment extends Fragment {

    private StudentResultsViewModel studentResultsViewModel;

    private FragmentStudentResultsBinding binding;

    RecyclerView recycler_student_results;
    TextView txt_no_results;
    ResultViewAdapter adapter;

    public static StudentResultsFragment newInstance() {
        return new StudentResultsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        studentResultsViewModel = new ViewModelProvider(this).get(StudentResultsViewModel.class);
        binding = FragmentStudentResultsBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        txt_no_results = binding.txtNoResults;
        recycler_student_results = binding.recyclerStudentResults;

        if (getArguments() != null)
        {
            String uid = getArguments().getString("studentUid");

            studentResultsViewModel.getResultErrorMutable().observe(getViewLifecycleOwner(), s -> Snackbar.make(root,""+s,Snackbar.LENGTH_LONG).show());

            studentResultsViewModel.getResultMutableLiveData(uid).observe(getViewLifecycleOwner(), resultModels -> {
                if (resultModels != null && resultModels.size()>0)
                {
                    txt_no_results.setVisibility(View.GONE);
                    adapter = new ResultViewAdapter(getContext(),resultModels);
                    recycler_student_results.setAdapter(adapter);
                }
            });
            recycler_student_results.setHasFixedSize(true);
            recycler_student_results.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));


        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}