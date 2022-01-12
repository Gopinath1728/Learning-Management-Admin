package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_subjects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleschooladmin.Adapter.SubjectsViewAdapter;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Common.SpacesItemDecoration;
import com.example.sampleschooladmin.databinding.FragmentViewClassSubjectsBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class ViewClassSubjectsFragment extends Fragment {

    private ViewClassSubjectsViewModel viewClassSubjectsViewModel;
    private FragmentViewClassSubjectsBinding binding;

    RecyclerView recycler_view_class_subjects;
    private int position;
    SubjectsViewAdapter adapter;
    TextView txt_no_subjects;



    public static ViewClassSubjectsFragment newInstance() {
        return new ViewClassSubjectsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewClassSubjectsViewModel = new ViewModelProvider(this).get(ViewClassSubjectsViewModel.class);
        binding = FragmentViewClassSubjectsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        if (getArguments() != null)
        {
            position = getArguments().getInt("subPos");
            recycler_view_class_subjects = binding.recyclerViewClassSubjects;
            txt_no_subjects = binding.txtNoSubjects;


            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(Common.classModels.getValue().get(position).getClassName() + " Subjects");


            viewClassSubjectsViewModel.getListMutableLiveData(position).observe(getViewLifecycleOwner(), subjectModels -> {
                if (subjectModels != null && subjectModels.size()>0)
                {
                    txt_no_subjects.setVisibility(View.GONE);
                    adapter = new SubjectsViewAdapter(getContext(), subjectModels, position, root);
                    recycler_view_class_subjects.setAdapter(adapter);
                }
            });

            viewClassSubjectsViewModel.getErrorMutable().observe(getViewLifecycleOwner(), s -> Snackbar.make(root, "" + s, Snackbar.LENGTH_LONG).show());

            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (adapter != null) {
                        switch (adapter.getItemViewType(position)) {
                            case 0:
                                return 1;
                            case 1:
                                return 2;
                            default:
                                return -1;
                        }
                    }
                    return -1;
                }
            });
            recycler_view_class_subjects.setLayoutManager(layoutManager);
            recycler_view_class_subjects.addItemDecoration(new SpacesItemDecoration(8));
        }

        return root;
    }

}