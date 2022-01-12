package com.example.sampleschooladmin.ui.view_classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleschooladmin.Adapter.ClassesViewAdapter;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Common.SpacesItemDecoration;
import com.example.sampleschooladmin.Model.ClassModel;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.databinding.FragmentViewClassesBinding;

import java.util.List;

public class ViewClassesFragment extends Fragment {

    RecyclerView recycler_view_classes;

    private ViewClassesViewModel viewClassesViewModel;
    private FragmentViewClassesBinding binding;

    ClassesViewAdapter adapter;

    public static ViewClassesFragment newInstance() {
        return new ViewClassesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewClassesViewModel = new ViewModelProvider(this).get(ViewClassesViewModel.class);
        binding = FragmentViewClassesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recycler_view_classes = (RecyclerView) root.findViewById(R.id.recycler_view_classes);


        viewClassesViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), classModelList -> {
            adapter = new ClassesViewAdapter(getContext(),classModelList);
            recycler_view_classes.setAdapter(adapter);
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
        recycler_view_classes.setLayoutManager(layoutManager);
        recycler_view_classes.addItemDecoration(new SpacesItemDecoration(8));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}