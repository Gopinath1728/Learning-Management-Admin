package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_timetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sampleschooladmin.Adapter.WeekdayViewAdapter;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Common.SpacesItemDecoration;
import com.example.sampleschooladmin.Model.TimetableModel;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.databinding.FragmentViewClassSubjectsBinding;
import com.example.sampleschooladmin.databinding.FragmentViewClassTimetableBinding;

import java.util.List;
import java.util.Objects;

public class ViewClassTimetableFragment extends Fragment {

    private ViewClassTimetableViewModel mViewModel;
    private FragmentViewClassTimetableBinding binding;
    private int position;

    RecyclerView recycler_weekday_view;
    WeekdayViewAdapter adapter;

    public static ViewClassTimetableFragment newInstance() {
        return new ViewClassTimetableFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ViewClassTimetableViewModel.class);
        binding = FragmentViewClassTimetableBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        assert getArguments() != null;
        position = getArguments().getInt("tblPos");
        recycler_weekday_view = (RecyclerView)root.findViewById(R.id.recycler_weekday_view);

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(Common.classModels.getValue().get(position).getClassName()+" Timetable");

        mViewModel.getMutableLiveData(position).observe(getViewLifecycleOwner(), timetableModels -> {
            adapter = new WeekdayViewAdapter(getContext(),timetableModels,position);
            recycler_weekday_view.setAdapter(adapter);
        });
        recycler_weekday_view.setHasFixedSize(true);
        recycler_weekday_view.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));


        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}