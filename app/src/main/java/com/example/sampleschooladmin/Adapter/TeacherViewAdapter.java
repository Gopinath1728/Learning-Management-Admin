package com.example.sampleschooladmin.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sampleschooladmin.Eventbus.SendMessageEvent;
import com.example.sampleschooladmin.Model.MessageModel;
import com.example.sampleschooladmin.Model.TeacherModel;
import com.example.sampleschooladmin.R;
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

public class TeacherViewAdapter extends RecyclerView.Adapter<TeacherViewAdapter.MyViewHolder> {

    Context context;
    List<TeacherModel> teacherModelList;

    public TeacherViewAdapter(Context context, List<TeacherModel> teacherModelList) {
        this.context = context;
        this.teacherModelList = teacherModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeacherViewAdapter.MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.teacher_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_user_name.setText(new StringBuilder(teacherModelList.get(position).getTeacherName()));
        if (teacherModelList.get(position).getTeacherImage().equalsIgnoreCase("Null"))
            holder.img_user.setImageResource(R.drawable.ic_teacher_icon);
        else
            Glide.with(context).load(teacherModelList.get(position).getTeacherImage()).into(holder.img_user);
        Bundle bundle = new Bundle();
        bundle.putInt("tcrPos", position);
        holder.img_profile_tcr.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_teacher_data, bundle));

        holder.img_send_msg_tcr.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View itemView = LayoutInflater.from(context).inflate(R.layout.send_message_dialog, null);
            EditText edt_message_from = (EditText) itemView.findViewById(R.id.edt_message_from);
            EditText edt_message_body = (EditText) itemView.findViewById(R.id.edt_message_body);
            EditText edt_message_title = (EditText) itemView.findViewById(R.id.edt_message_title);
            Button btn_message_cancel = (Button) itemView.findViewById(R.id.btn_message_cancel);
            Button btn_message_send = (Button) itemView.findViewById(R.id.btn_message_send);
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
                        .collection("Teachers")
                        .document(teacherModelList.get(position).getUid())
                        .collection("Messages")
                        .document().getId());
                FirebaseFirestore.getInstance()
                        .collection("Teachers")
                        .document(teacherModelList.get(position).getUid())
                        .collection("Messages")
                        .document(messageModel.getMsgId())
                        .set(messageModel)
                        .addOnSuccessListener(unused -> EventBus.getDefault().post(new SendMessageEvent(edt_message_title.getText().toString().trim(),
                                edt_message_body.getText().toString().trim(), teacherModelList.get(position).getTeacherToken())))
                        .addOnFailureListener(e -> Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show());
            });
        });
    }

    @Override
    public int getItemCount() {
        return teacherModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;
        @BindView(R.id.img_user)
        CircleImageView img_user;
        @BindView(R.id.txt_user_name)
        TextView txt_user_name;
        @BindView(R.id.img_profile_tcr)
        ImageButton img_profile_tcr;
        @BindView(R.id.img_send_msg_tcr)
        ImageButton img_send_msg_tcr;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
