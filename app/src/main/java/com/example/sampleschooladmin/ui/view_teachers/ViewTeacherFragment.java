package com.example.sampleschooladmin.ui.view_teachers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleschooladmin.Adapter.TeacherViewAdapter;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Eventbus.SendMessageEvent;
import com.example.sampleschooladmin.Model.MessageModel;
import com.example.sampleschooladmin.Model.TeacherModel;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.databinding.FragmentViewTeacherBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewTeacherFragment extends Fragment {

    private ViewTeacherViewModel viewTeacherViewModel;
    private FragmentViewTeacherBinding binding;
    TeacherViewAdapter adapter;

    RecyclerView recycler_view_teachers;

    List<TeacherModel> teacherModels = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        viewTeacherViewModel =
                new ViewModelProvider(this).get(ViewTeacherViewModel.class);

        binding = FragmentViewTeacherBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recycler_view_teachers = (RecyclerView) root.findViewById(R.id.recycler_view_teachers);

        viewTeacherViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), teacherModelList -> {
            teacherModels=teacherModelList;
            adapter = new TeacherViewAdapter(getContext(),teacherModelList);
            recycler_view_teachers.setAdapter(adapter);
        });
        recycler_view_teachers.setHasFixedSize(true);
        recycler_view_teachers.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.msg_all_tcrs,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.msg_all_teachers)
        {
            if (teacherModels != null && teacherModels.size()>0)
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

                    for (int i=0;i<teacherModels.size();i++){
                        int j =i;
                        messageModel.setMsgId(FirebaseFirestore.getInstance()
                                .collection("Teachers")
                                .document(teacherModels.get(i).getUid())
                                .collection("Messages")
                                .document().getId());
                        FirebaseFirestore.getInstance()
                                .collection("Teachers")
                                .document(teacherModels.get(i).getUid())
                                .collection("Messages")
                                .document(messageModel.getMsgId())
                                .set(messageModel)
                                .addOnSuccessListener(unused -> EventBus.getDefault().post(new SendMessageEvent(edt_message_title.getText().toString().trim(),
                                        edt_message_body.getText().toString().trim(),
                                        teacherModels.get(j).getTeacherToken())))
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