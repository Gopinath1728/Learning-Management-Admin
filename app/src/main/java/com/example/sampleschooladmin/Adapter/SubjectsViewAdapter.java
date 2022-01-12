package com.example.sampleschooladmin.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Eventbus.SubjectListChangeEvent;
import com.example.sampleschooladmin.Model.ClassModel;
import com.example.sampleschooladmin.Model.SubjectModel;
import com.example.sampleschooladmin.Model.TeacherModel;
import com.example.sampleschooladmin.Model.TeachingClass;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_subjects.ViewClassSubjectsViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class SubjectsViewAdapter extends RecyclerView.Adapter<SubjectsViewAdapter.MyViewHolder> {

    Context context;
    List<SubjectModel> subjectModelList;
    int classPos;
    View parentView;

    SharedPreferences sharedPreferences;


    public SubjectsViewAdapter(Context context, List<SubjectModel> subjectModelList, int classPos, View parentView) {
        this.context = context;
        this.subjectModelList = subjectModelList;
        this.classPos = classPos;
        this.parentView = parentView;
        sharedPreferences = context.getSharedPreferences("appoint", Context.MODE_PRIVATE);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SubjectsViewAdapter.MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.view_class_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_class_name.setText(new StringBuilder(subjectModelList.get(position).getSubjectName()));
        holder.itemView.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View itemView = LayoutInflater.from(context).inflate(R.layout.subject_detail_dialog_layout, null);
            builder.setView(itemView);
            CircleImageView img_subject_dialog_tcr = (CircleImageView) itemView.findViewById(R.id.img_subject_dialog_tcr);
            TextView txt_subject_dialog_tName = (TextView) itemView.findViewById(R.id.txt_subject_dialog_tName);
            Button btn_subject_dialog_drive = (Button) itemView.findViewById(R.id.btn_subject_dialog_drive);
            Button btn_subject_dialog_ok = (Button) itemView.findViewById(R.id.btn_subject_dialog_ok);
            LinearLayout linear_teacher_item = (LinearLayout) itemView.findViewById(R.id.linear_teacher_item);
            if (!subjectModelList.get(position).getSubjTchrImg().equals("Null"))
                Glide.with(context).load(subjectModelList.get(position).getSubjTchrImg()).into(img_subject_dialog_tcr);
            txt_subject_dialog_tName.setText(new StringBuilder(subjectModelList.get(position).getSubjTeacher()));
            AlertDialog dialog = builder.create();
            dialog.show();
            btn_subject_dialog_ok.setOnClickListener(view1 -> dialog.dismiss());
            Bundle bundle = new Bundle();
            bundle.putString("subjectLink", subjectModelList.get(position).getMaterialEdtLink());
            bundle.putInt("classPos", classPos);
            bundle.putInt("subPos", position);
            btn_subject_dialog_drive.setOnClickListener(view12 -> {
                dialog.dismiss();
                Navigation.findNavController(parentView).navigate(R.id.nav_drive_fragment, bundle);
            });
            linear_teacher_item.setOnClickListener(view13 -> {
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

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setTitle("Appoint a new Teacher");
                    builder1.setItems(teacherNames, (dialogInterface, i) -> {
                        AlertDialog.Builder builder12 = new AlertDialog.Builder(context);
                        builder12.setMessage("Do you want to assign " + teacherNames[i] + " as the teacher for " +
                                subjectModelList.get(position).getSubjectName() + " ?");
                        builder12.setPositiveButton("YES", (dialogInterface1, i1) -> {

                            TeachingClass teachingClass = new TeachingClass(subjectModelList.get(position).getSubjectName(), Common.classModels.getValue().get(classPos).getClassName());

                                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                firebaseFirestore.collection("Teachers")
                                        .document(teacherUid[i])
                                        .update("teachingClasses", FieldValue.arrayUnion(teachingClass))
                                        .addOnSuccessListener(unused -> {
//                                            EventBus.getDefault().post(new SubjectListChangeEvent(Common.subjectModels));

                                            FirebaseFirestore.getInstance().collection("Classes")
                                                    .document(Common.classModels.getValue().get(classPos).getDocId())
                                                    .collection("Subjects")
                                                    .document(subjectModelList.get(position).getSubjDocId())
                                                    .update("subjTeacher",teacherNames[i],
                                                            "subjTchrImg",teacherImg[i])
                                                    .addOnSuccessListener(unused1 -> Snackbar.make(parentView,"Appointed Successfully",Snackbar.LENGTH_LONG)
                                                            .setAction("OK", view14 -> {
                                                                dialogInterface1.dismiss();
                                                            }).show())
                                                    .addOnFailureListener(e -> Snackbar.make(parentView,""+e.getMessage(),Snackbar.LENGTH_LONG)
                                                            .setAction("OK", view14 -> {
                                                                dialogInterface1.dismiss();
                                                            }).show());


                                        })
                                        .addOnFailureListener(e -> {
                                            Snackbar.make(parentView,"Appoint Failed"+e,Snackbar.LENGTH_LONG)
                                                    .setAction("OK", view14 -> {
                                                        dialogInterface1.dismiss();
                                                    }).show();
                                        });
                        });
                        builder12.setNegativeButton("CANCEL", (dialogInterface12, i12) -> dialogInterface12.dismiss());

                        AlertDialog dialog1 = builder12.create();
                        dialog1.setCanceledOnTouchOutside(false);
                        dialog1.show();
                    });
                    AlertDialog dialog1 = builder1.create();
                    dialog1.show();
                }
            });
        });
    }


    @Override
    public int getItemCount() {
        return subjectModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;
        @BindView(R.id.txt_class_name)
        TextView txt_class_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (subjectModelList.size() == 1)
            return 0;
        else {
            if (subjectModelList.size() % 2 == 0)
                return 0;
            else
                return (position > 1 && position == subjectModelList.size() - 1) ? 1 : 0;
        }

    }
}
