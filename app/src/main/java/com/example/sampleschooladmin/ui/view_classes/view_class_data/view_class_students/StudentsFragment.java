package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_students;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampleschooladmin.Adapter.StudentViewAdapter;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Eventbus.SendMessageEvent;
import com.example.sampleschooladmin.Model.MessageModel;
import com.example.sampleschooladmin.Model.StudentModel;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.databinding.FragmentStudentsBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class StudentsFragment extends Fragment {

    private StudentsViewModel studentsViewModel;
    private FragmentStudentsBinding binding;

    RecyclerView recycler_students;
    TextView txt_no_users;
    int position;
    StudentViewAdapter adapter;

    List<StudentModel> studentModels = new ArrayList<>();



    public static StudentsFragment newInstance() {
        return new StudentsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        studentsViewModel = new ViewModelProvider(this).get(StudentsViewModel.class);
        binding = FragmentStudentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recycler_students = (RecyclerView) root.findViewById(R.id.recycler_students);
        txt_no_users = (TextView) root.findViewById(R.id.txt_no_users);

        if (getArguments() != null) {
            position = getArguments().getInt("classPos");

            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(Common.classModels.getValue().get(position).getClassName() + " Students");


            studentsViewModel.getErrorMessage().observe(getViewLifecycleOwner(), s -> {
                Snackbar.make(root, "" + s, Snackbar.LENGTH_SHORT).show();
            });

            studentsViewModel.getStudentModelListMutable(position).observe(getViewLifecycleOwner(), studentModelList -> {
                if (studentModelList.size() > 0) {
                    studentModels = studentModelList;
                    txt_no_users.setVisibility(View.GONE);
                    adapter = new StudentViewAdapter(getContext(), studentModelList);
                    recycler_students.setAdapter(adapter);
                }

            });

            recycler_students.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recycler_students.setLayoutManager(linearLayoutManager);
            recycler_students.addItemDecoration(new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation()));
        }

        return root;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.msg_all_stdns,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.msg_all_students)
        {
            if (studentModels != null && studentModels.size()>0)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View itemView = LayoutInflater.from(getContext()).inflate(R.layout.send_message_dialog, null);
                EditText edt_message_from = (EditText) itemView.findViewById(R.id.edt_message_from);
                EditText edt_message_body = (EditText) itemView.findViewById(R.id.edt_message_body);
                EditText edt_message_title = (EditText) itemView.findViewById(R.id.edt_message_title);
                Button btn_message_cancel = (Button)itemView.findViewById(R.id.btn_message_cancel);
                Button btn_message_send = (Button)itemView.findViewById(R.id.btn_message_send);
                builder.setView(itemView);
                AlertDialog dialog = builder.create();
                dialog.show();

                btn_message_cancel.setOnClickListener(v -> dialog.dismiss());

                btn_message_send.setOnClickListener(v -> {
                    MessageModel messageModel = new MessageModel();
                    messageModel.setTitle(edt_message_title.getText().toString().trim());
                    messageModel.setFrom(edt_message_from.getText().toString());
                    messageModel.setBody(edt_message_body.getText().toString().trim());
                    messageModel.setDate(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
                    messageModel.setTime(new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date()));

                    for (int i=0;i<studentModels.size();i++){
                        int j =i;
                        messageModel.setMsgId(FirebaseFirestore.getInstance()
                                .collection("Students")
                                .document(studentModels.get(i).getUid())
                                .collection("Messages")
                                .document().getId());
                        FirebaseFirestore.getInstance()
                                .collection("Students")
                                .document(studentModels.get(i).getUid())
                                .collection("Messages")
                                .document(messageModel.getMsgId())
                                .set(messageModel)
                                .addOnSuccessListener(unused -> EventBus.getDefault().post(new SendMessageEvent(edt_message_title.getText().toString().trim(),
                                        edt_message_body.getText().toString().trim(),
                                        studentModels.get(j).getStudentToken())))
                                .addOnFailureListener(e -> Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show());
                        dialog.dismiss();
                    }
                });

            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}