package com.example.sampleschooladmin.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sampleschooladmin.Eventbus.SendMessageEvent;
import com.example.sampleschooladmin.Model.MessageModel;
import com.example.sampleschooladmin.Model.StudentModel;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.Services.IFCMServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.CompositeDisposable;

public class StudentViewAdapter extends RecyclerView.Adapter<StudentViewAdapter.MyViewHolder> {

    Context context;
    List<StudentModel> studentModelList;
    Boolean monitor;

    public StudentViewAdapter(Context context, List<StudentModel> studentModelList) {
        this.context = context;
        this.studentModelList = studentModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentViewAdapter.MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.student_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_user_name.setText(new StringBuilder(studentModelList.get(position).getStudentName()));
        if (studentModelList.get(position).getStudentImage().equalsIgnoreCase("Null"))
            holder.img_user.setImageResource(R.drawable.ic_student_icon);
        else
            Glide.with(context).load(studentModelList.get(position).getStudentImage()).into(holder.img_user);
        monitor = studentModelList.get(position).getClassMonitor();
        if (studentModelList.get(position).getClassMonitor()) {
            holder.img_monitor_label.setVisibility(View.VISIBLE);
            holder.img_monitor_button.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to remove " + studentModelList.get(position).getStudentName() +
                        " as Class Monitor?");
                builder.setPositiveButton("Remove", (dialogInterface, i) -> {
                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                    firestore.collection("Students")
                            .document(studentModelList.get(position).getUid())
                            .update("classMonitor", false)
                            .addOnSuccessListener(unused -> {
                                dialogInterface.dismiss();
                            });

                });
                builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            });
        } else {
            holder.img_monitor_button.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to appoint " + studentModelList.get(position).getStudentName() +
                        " as Class Monitor?");
                builder.setPositiveButton("Appoint", (dialogInterface, i) -> {
                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                    firestore.collection("Students")
                            .document(studentModelList.get(position).getUid())
                            .update("classMonitor", true)
                            .addOnSuccessListener(unused -> {
                                dialogInterface.dismiss();
                            });

                });
                builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            });
        }
        holder.img_send_msg_button.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View itemView = LayoutInflater.from(context).inflate(R.layout.send_message_dialog,null);
            EditText edt_message_from = (EditText) itemView.findViewById(R.id.edt_message_from);
            EditText edt_message_body = (EditText) itemView.findViewById(R.id.edt_message_body);
            EditText edt_message_title = (EditText) itemView.findViewById(R.id.edt_message_title);
            Button btn_message_cancel = (Button)itemView.findViewById(R.id.btn_message_cancel);
            Button btn_message_send = (Button)itemView.findViewById(R.id.btn_message_send);
            builder.setView(itemView);
            AlertDialog dialog = builder.create();
            dialog.show();

            btn_message_cancel.setOnClickListener(view1 -> dialog.dismiss());

            btn_message_send.setOnClickListener(view12 -> {
                MessageModel messageModel = new MessageModel();
                messageModel.setTitle(edt_message_title.getText().toString().trim());
                messageModel.setFrom(edt_message_from.getText().toString().trim());
                messageModel.setBody(edt_message_body.getText().toString().trim());
                messageModel.setDate(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
                messageModel.setTime(new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date()));
                messageModel.setMsgId(FirebaseFirestore.getInstance()
                        .collection("Students")
                        .document(studentModelList.get(position).getUid())
                        .collection("Messages")
                        .document().getId());
                FirebaseFirestore.getInstance()
                        .collection("Students")
                        .document(studentModelList.get(position).getUid())
                        .collection("Messages")
                        .document(messageModel.getMsgId())
                        .set(messageModel)
                        .addOnSuccessListener(unused -> EventBus.getDefault().post(new SendMessageEvent(edt_message_title.getText().toString().trim(),
                                edt_message_body.getText().toString().trim(), studentModelList.get(position).getStudentToken())))
                        .addOnFailureListener(e -> Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show());
            });

        });
        Bundle bundle = new Bundle();
        bundle.putInt("stuPos", position);
        holder.img_profile_button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_view_student_data, bundle));
    }

    @Override
    public int getItemCount() {
        return studentModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Unbinder unbinder;

        @BindView(R.id.img_student)
        CircleImageView img_user;
        @BindView(R.id.txt_student_name)
        TextView txt_user_name;
        @BindView(R.id.img_monitor_button)
        ImageButton img_monitor_button;
        @BindView(R.id.img_profile_button)
        ImageButton img_profile_button;
        @BindView(R.id.img_send_msg_button)
        ImageButton img_send_msg_button;
        @BindView(R.id.img_monitor_label)
        ImageView img_monitor_label;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
