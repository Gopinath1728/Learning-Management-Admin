package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_assignments;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampleschooladmin.Adapter.AssignmentAdapter;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Model.AssignmentModel;
import com.example.sampleschooladmin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssignmentsFragment extends Fragment {

    private AssignmentsViewModel mViewModel;

    RecyclerView recycler_assignment;
    TextView txt_noList;
    FloatingActionButton fab_add_assignment;
    AssignmentAdapter adapter;

    int position;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AssignmentsViewModel.class);
        View root = inflater.inflate(R.layout.assignments_fragment, container, false);

        recycler_assignment = (RecyclerView) root.findViewById(R.id.recycler_assignment);
        txt_noList = (TextView) root.findViewById(R.id.txt_noList);
        fab_add_assignment = (FloatingActionButton) root.findViewById(R.id.fab_add_assignment);

        if (getArguments() != null) {
            position = getArguments().getInt("classPos");

            mViewModel.getAssignmentErrorMessage().observe(getViewLifecycleOwner(), s -> Snackbar.make(root, "" + s, Snackbar.LENGTH_LONG).show());

            mViewModel.getAssignmentMutableLiveData(position).observe(getViewLifecycleOwner(), assignmentModels -> {
                if (assignmentModels != null && assignmentModels.size() > 0) {
                    txt_noList.setVisibility(View.GONE);
                    adapter = new AssignmentAdapter(getContext(), assignmentModels, position);
                    recycler_assignment.setAdapter(adapter);
                }
            });
            recycler_assignment.setHasFixedSize(true);
            recycler_assignment.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

            fab_add_assignment.setOnClickListener(addAssignment);

        }

        return root;
    }

    View.OnClickListener addAssignment = view -> {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Assignment");

        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.add_assignment_dialog, null);
        EditText edt_assi_subject_name = (EditText) itemView.findViewById(R.id.edt_assi_subject_name);
        TextView txt_assi_teacher_name = (TextView) itemView.findViewById(R.id.txt_assi_teacher_name);
        EditText edt_assi_topic_name = (EditText) itemView.findViewById(R.id.edt_assi_topic_name);
        EditText edt_assi_body = (EditText) itemView.findViewById(R.id.edt_assi_body);
        Button btn_assi_add = (Button) itemView.findViewById(R.id.btn_assi_add);
        Button btn_assi_cancel = (Button) itemView.findViewById(R.id.btn_assi_cancel);
        builder.setView(itemView);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        String[] teacherNames = new String[Common.teacherModel.getValue().size()];
        for (int i = 0; i < Common.teacherModel.getValue().size(); i++)
            teacherNames[i] = Common.teacherModel.getValue().get(i).getTeacherName();

        txt_assi_teacher_name.setOnClickListener(view1 -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            builder1.setItems(teacherNames, (dialogInterface, i) -> {
                txt_assi_teacher_name.setText(teacherNames[i]);
                dialogInterface.dismiss();
            });
            AlertDialog dialog1 = builder1.create();
            dialog1.setCanceledOnTouchOutside(false);
            dialog1.show();
        });


        btn_assi_cancel.setOnClickListener(v1 -> {
            dialog.dismiss();
        });

        btn_assi_add.setOnClickListener(v12 -> {
            AssignmentModel assignmentModel = new AssignmentModel();

            if (!edt_assi_subject_name.getText().toString().isEmpty())
                assignmentModel.setSubject(edt_assi_subject_name.getText().toString().trim());
            else
                edt_assi_subject_name.setError("Subject Name can't be empty !");

            if (!txt_assi_teacher_name.getText().toString().isEmpty())
                assignmentModel.setTeacherName(txt_assi_teacher_name.getText().toString().trim());
            else
                txt_assi_teacher_name.setError("Teacher Name can't be empty !");

            if (!edt_assi_topic_name.getText().toString().isEmpty())
                assignmentModel.setTopic(edt_assi_topic_name.getText().toString().trim());
            else
                edt_assi_topic_name.setError("Topic can't be empty !");

            if (!edt_assi_body.getText().toString().isEmpty())
                assignmentModel.setBody(edt_assi_body.getText().toString().trim());
            else
                edt_assi_body.setError("Body can't be empty !");

            assignmentModel.setDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));

            if (!edt_assi_subject_name.getText().toString().isEmpty() &&
                    !txt_assi_teacher_name.getText().toString().isEmpty() &&
                    !edt_assi_topic_name.getText().toString().isEmpty() &&
                    !edt_assi_body.getText().toString().isEmpty()) {
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                assignmentModel.setAssignmentId(firestore.collection("Classes")
                        .document(Common.classModels.getValue().get(position).getDocId())
                        .collection("Assignments")
                        .document().getId());
                firestore.collection("Classes")
                        .document(Common.classModels.getValue().get(position).getDocId())
                        .collection("Assignments")
                        .document(assignmentModel.getAssignmentId())
                        .set(assignmentModel)
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                dialog.dismiss();
            }

        });
    };

}