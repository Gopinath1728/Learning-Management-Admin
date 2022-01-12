package com.example.sampleschooladmin.ui.view_classes.view_class_data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Model.StudentModel;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.databinding.FragmentViewClassDataBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewClassDataFragment extends Fragment {

    CardView proctor_card;
    CircleImageView img_class_proctor;
    TextView txt_class_proctor_name;
    SwitchCompat btn_compatSwitch;
    ImageButton img_subjects,img_timetable,img_students,img_assignments,img_quiz,img_exams;


    private FragmentViewClassDataBinding binding;
    List<StudentModel> studentModels = new ArrayList<>();

    private int position;

    public static ViewClassDataFragment newInstance() {
        return new ViewClassDataFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentViewClassDataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        proctor_card = (CardView) root.findViewById(R.id.proctor_card);
        img_class_proctor = (CircleImageView) root.findViewById(R.id.img_class_proctor);
        txt_class_proctor_name = (TextView) root.findViewById(R.id.txt_class_proctor_name);
        btn_compatSwitch = (SwitchCompat) root.findViewById(R.id.btn_compatSwitch);
        btn_compatSwitch.setChecked(Common.classModels.getValue().get(position).getTeachingMode());

        img_subjects = binding.imgSubjects;
        img_timetable = binding.imgTimetable;
        img_students = binding.imgStudents;
        img_assignments = binding.imgAssignments;
        img_quiz = binding.imgQuiz;
        img_exams = binding.imgExams;

        if (getArguments() != null)
        {
            position = getArguments().getInt("position");

            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(Common.classModels.getValue().get(position).getClassName());

            Bundle subBundle = new Bundle();
            subBundle.putInt("subPos", position);
            img_subjects.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_view_class_subjects, subBundle));

            Bundle tblBundle = new Bundle();
            subBundle.putInt("tblPos", position);
            img_timetable.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_view_class_timetable, tblBundle));

            Bundle clsPos = new Bundle();
            clsPos.putInt("classPos", position);
            img_students.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_class_students, clsPos));

            img_assignments.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_assignment, clsPos));

            img_quiz.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_quiz, clsPos));

            img_exams.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_examinations,clsPos));

            proctor_card.setOnClickListener(appointProctor);

            setData();


            btn_compatSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection("Classes")
                        .document(Common.classModels.getValue().get(position).getDocId())
                        .update("teachingMode", b);
            });
        }

        return root;
    }

    private void setData() {
        if (Common.classModels.getValue().get(position).getProctorName().equals("Proctor Name")) {
            img_class_proctor.setImageResource(R.drawable.ic_baseline_error_24);
            img_class_proctor.setColorFilter(R.color.red);
            txt_class_proctor_name.setText(new StringBuilder("No Proctor Appointed. Click to Appoint."));
        } else {
            if (!Common.classModels.getValue().get(position).getProctorImage().equalsIgnoreCase("Null"))
                Glide.with(getContext()).load(Common.classModels.getValue().get(position).getProctorImage()).into(img_class_proctor);
            img_class_proctor.setImageResource(R.drawable.ic_teacher_icon);
            txt_class_proctor_name.setText(new StringBuilder(Common.classModels.getValue().get(position).getProctorName()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    View.OnClickListener appointProctor = view -> {
        if (Common.teacherModel.getValue() != null) {
            String[] teacherNames = new String[Common.teacherModel.getValue().size()];
            String[] teacherUid = new String[Common.teacherModel.getValue().size()];
            String[] teacherImg = new String[Common.teacherModel.getValue().size()];
            if (Common.teacherModel.getValue().size() > 0) {

                for (int i = 0; i < Common.teacherModel.getValue().size(); i++) {
                    teacherNames[i] = Common.teacherModel.getValue().get(i).getTeacherName();
                    teacherUid[i] = Common.teacherModel.getValue().get(i).getUid();
                    teacherImg[i] = Common.teacherModel.getValue().get(i).getTeacherImage();
                }
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Select a Proctor for " + Common.classModels.getValue().get(position).getClassName());
            builder.setItems(teacherNames, (dialogInterface, i) -> {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setTitle("Appoint " + teacherNames[i] + " as Proctor for " + Common.classModels.getValue().get(position)
                        .getClassName() + " ?");
                builder1.setPositiveButton("YES", (dialogInterface1, i1) -> {
                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                    firestore.collection("Classes")
                            .document(Common.classModels.getValue().get(position).getDocId())
                            .update("proctorImage", teacherImg[i],
                                    "proctorName", teacherNames[i])
                            .addOnSuccessListener(unused -> {
                                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                firebaseFirestore.collection("Teachers")
                                        .document(teacherUid[i])
                                        .update("isProctor", true,
                                                "proctorClass", Common.classModels.getValue().get(position).getClassName())
                                        .addOnFailureListener(e -> Snackbar.make(view, "" + e, Snackbar.LENGTH_SHORT).show())
                                        .addOnSuccessListener(unused1 -> {
                                            setData();
                                            Snackbar.make(view, "Appointed Successfully", Snackbar.LENGTH_SHORT).show();
                                        });
                            })
                            .addOnFailureListener(e -> {
                                Snackbar.make(view, "" + e, Snackbar.LENGTH_SHORT).show();
                            });
                });
                builder1.setNegativeButton("CANCEL", (dialogInterface12, i12) -> dialogInterface12.dismiss());
                AlertDialog dialog = builder1.create();
                dialog.show();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };


}