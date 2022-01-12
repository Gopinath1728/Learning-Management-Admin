package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_students.view_student_data;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.databinding.FragmentStudentsBinding;
import com.example.sampleschooladmin.databinding.FragmentViewStudentDataBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class ViewStudentDataFragment extends Fragment {

    int position;

    TextView txt_student_data_name,txt_student_data_Uid,txt_student_data_email,txt_student_data_class,
            txt_student_data_roll,txt_student_data_address,txt_student_data_parent_name,txt_student_data_parent_phone;

    CircleImageView img_student_data;
    ImageButton btn_call_parent,btn_view_students_attendance,btn_view_students_results;

    private FragmentViewStudentDataBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentViewStudentDataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        assert getArguments() != null;
        position = getArguments().getInt("stuPos");

        btn_call_parent = (ImageButton)root.findViewById(R.id.btn_call_parent);
        btn_view_students_attendance = (ImageButton)root.findViewById(R.id.btn_view_students_attendance);
        btn_view_students_results = (ImageButton)root.findViewById(R.id.btn_view_students_results);



        btn_call_parent.setOnClickListener(callParentClick);

        img_student_data = (CircleImageView)root.findViewById(R.id.img_student_data);
        txt_student_data_name = (TextView) root.findViewById(R.id.txt_student_data_name);
        txt_student_data_Uid = (TextView) root.findViewById(R.id.txt_student_data_Uid);
        txt_student_data_email = (TextView) root.findViewById(R.id.txt_student_data_email);
        txt_student_data_class = (TextView) root.findViewById(R.id.txt_student_data_class);
        txt_student_data_roll = (TextView) root.findViewById(R.id.txt_student_data_roll);
        txt_student_data_address = (TextView) root.findViewById(R.id.txt_student_data_address);
        txt_student_data_parent_name = (TextView) root.findViewById(R.id.txt_student_data_parent_name);
        txt_student_data_parent_phone = (TextView) root.findViewById(R.id.txt_student_data_parent_phone);
        String image = Common.studentModel.get(position).getStudentImage();

        if (!image.equalsIgnoreCase("Null"))
            Glide.with(requireContext()).load(image).into(img_student_data);

        txt_student_data_name.setText(new StringBuilder(Common.studentModel.get(position).getStudentName()));
        txt_student_data_Uid.setText(new StringBuilder(Common.studentModel.get(position).getStudentUid()));
        txt_student_data_email.setText(new StringBuilder(Common.studentModel.get(position).getStudentEmail()));
        txt_student_data_class.setText(new StringBuilder(Common.studentModel.get(position).getStudentClass()));
        txt_student_data_roll.setText(new StringBuilder(Common.studentModel.get(position).getStudentRollNo()));
        txt_student_data_address.setText(new StringBuilder(Common.studentModel.get(position).getStudentAddress()));
        txt_student_data_parent_name.setText(new StringBuilder(Common.studentModel.get(position).getStudentParentName()));
        txt_student_data_parent_phone.setText(new StringBuilder(Common.studentModel.get(position).getStudentParentPhone()));

        Bundle bundle = new Bundle();
        bundle.putString("studentUid",Common.studentModel.get(position).getUid());

        btn_view_students_attendance.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_attendance,bundle));

        btn_view_students_results.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_student_result,bundle));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }


    View.OnClickListener callParentClick = view -> {
        Snackbar.make(view,"Call Student Parent",Snackbar.LENGTH_LONG)
                .setAction("OK",null).show();
    };
}