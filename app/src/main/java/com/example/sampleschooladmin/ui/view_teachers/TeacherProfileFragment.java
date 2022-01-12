package com.example.sampleschooladmin.ui.view_teachers;

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
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sampleschooladmin.Adapter.TeachingClassViewAdapter;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Common.SpacesItemDecoration;
import com.example.sampleschooladmin.Model.TeacherModel;
import com.example.sampleschooladmin.Model.TeachingClass;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.databinding.FragmentTeacherProfileBinding;
import com.example.sampleschooladmin.databinding.FragmentViewTeacherBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherProfileFragment extends Fragment {

    private FragmentTeacherProfileBinding binding;
    private TeacherProfileViewModel teacherProfileViewModel;

    CircleImageView img_teacher_data;
    TextView txt_teacher_data_Uid, txt_teacher_data_name, txt_teacher_data_email, txt_teacher_data_proctor_class,
            txt_teacher_data_address, txt_teacher_data_phone, txt_teaching_no_class;
    ImageButton btn_call_teacher;
    RecyclerView recycler_teaching_classes;

    TeachingClassViewAdapter adapter;

    int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        teacherProfileViewModel =
                new ViewModelProvider(this).get(TeacherProfileViewModel.class);

        binding = FragmentTeacherProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        assert getArguments() != null;
        position = getArguments().getInt("tcrPos");

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(Common.teacherModel.getValue().get(position).getTeacherName()+" Profile");

        img_teacher_data = (CircleImageView) root.findViewById(R.id.img_teacher_data);
        txt_teacher_data_Uid = (TextView) root.findViewById(R.id.txt_teacher_data_Uid);
        txt_teacher_data_name = (TextView) root.findViewById(R.id.txt_teacher_data_name);
        txt_teacher_data_email = (TextView) root.findViewById(R.id.txt_teacher_data_email);
        txt_teacher_data_proctor_class = (TextView) root.findViewById(R.id.txt_teacher_data_proctor_class);
        txt_teacher_data_address = (TextView) root.findViewById(R.id.txt_teacher_data_address);
        txt_teacher_data_phone = (TextView) root.findViewById(R.id.txt_teacher_data_phone);
        txt_teaching_no_class = (TextView) root.findViewById(R.id.txt_teaching_no_class);
        btn_call_teacher = (ImageButton) root.findViewById(R.id.btn_call_teacher);
        recycler_teaching_classes = (RecyclerView) root.findViewById(R.id.recycler_teaching_classes);

        btn_call_teacher.setOnClickListener(callTeacher);

        TeacherModel teacherModel = Common.teacherModel.getValue().get(position);

        if (!teacherModel.getTeacherImage().equalsIgnoreCase("Null"))
            Glide.with(getContext()).load(teacherModel.getTeacherImage()).into(img_teacher_data);
        txt_teacher_data_Uid.setText(new StringBuilder(teacherModel.gettUid()));
        txt_teacher_data_name.setText(new StringBuilder(teacherModel.getTeacherName()));
        txt_teacher_data_email.setText(new StringBuilder(teacherModel.getTeacherEmail()));
        if (teacherModel.getProctorClass() != null)
            txt_teacher_data_proctor_class.setText(new StringBuilder(teacherModel.getProctorClass()));
        txt_teacher_data_address.setText(new StringBuilder(teacherModel.getTeacherAddress()));
        txt_teacher_data_phone.setText(new StringBuilder(teacherModel.getTeacherPhone()));

        teacherProfileViewModel.getTeachingClassMutableLiveData(position).observe(getViewLifecycleOwner(), teachingClasses -> {
            if (teachingClasses.size() > 0) {
                txt_teaching_no_class.setVisibility(View.GONE);
                adapter = new TeachingClassViewAdapter(getContext(), teachingClasses);
                recycler_teaching_classes.setAdapter(adapter);
            } else
                txt_teaching_no_class.setVisibility(View.VISIBLE);
        });
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
        recycler_teaching_classes.setLayoutManager(layoutManager);
        recycler_teaching_classes.addItemDecoration(new SpacesItemDecoration(8));

        return root;
    }

    View.OnClickListener callTeacher = view -> {
        Snackbar.make(view, "Call Teacher", Snackbar.LENGTH_SHORT).show();
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}