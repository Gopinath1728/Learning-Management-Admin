package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_timetable.view_time;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sampleschooladmin.Adapter.TimeViewAdapter;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Common.SpacesItemDecoration;
import com.example.sampleschooladmin.Model.TimeModel;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.databinding.FragmentTimeBinding;
import com.example.sampleschooladmin.databinding.FragmentViewClassTimetableBinding;
import com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_timetable.ViewClassTimetableViewModel;

import java.util.List;
import java.util.Objects;

public class TimeFragment extends Fragment {

    private TimeViewModel timeViewModel;
    private FragmentTimeBinding binding;

    RecyclerView recycler_time;
    int classPos,weekPos;
    TimeViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        timeViewModel = new ViewModelProvider(this).get(TimeViewModel.class);
        binding = FragmentTimeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recycler_time = (RecyclerView) root.findViewById(R.id.recycler_time);
        classPos = getArguments().getInt("classPosition");
        weekPos = getArguments().getInt("weekPosition");

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(Common.classModels.getValue().get(classPos).getClassName()+" Timetable");

        timeViewModel.getMutableLiveData(classPos,weekPos).observe(getViewLifecycleOwner(), timeModels -> {
            adapter = new TimeViewAdapter(getContext(),timeModels);
            recycler_time.setAdapter(adapter);
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter != null)
                {
                    switch (adapter.getItemViewType(position))
                    {
                        case 0: return 1;
                        case 1: return 2;
                        default:return -1;
                    }
                }
                return -1;
            }
        });
        recycler_time.setLayoutManager(layoutManager);
        recycler_time.addItemDecoration(new SpacesItemDecoration(8));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}