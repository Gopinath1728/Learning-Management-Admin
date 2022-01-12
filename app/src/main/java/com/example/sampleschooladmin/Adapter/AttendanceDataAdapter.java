package com.example.sampleschooladmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sampleschooladmin.Model.AddAttendanceModel;
import com.example.sampleschooladmin.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AttendanceDataAdapter extends RecyclerView.Adapter<AttendanceDataAdapter.MyViewHolder> {

    Context context;
    List<AddAttendanceModel> attendanceModelList;

    public AttendanceDataAdapter(Context context, List<AddAttendanceModel> attendanceModelList) {
        this.context = context;
        this.attendanceModelList = attendanceModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.attendance_data_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (attendanceModelList.get(position).getStatus().equals("Absent")) {
            holder.linear_att_color.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
            holder.txt_attendance_status.setText(new StringBuilder("Absent"));
            holder.txt_attendance_status.setTextColor(ContextCompat.getColor(context,R.color.red));
        }
        if (attendanceModelList.get(position).getStatus().equals("Present")) {
            holder.linear_att_color.setBackgroundColor(ContextCompat.getColor(context, R.color.light_green));
            holder.txt_attendance_status.setText(new StringBuilder("Present"));
            holder.txt_attendance_status.setTextColor(ContextCompat.getColor(context,R.color.light_green));
        }
        holder.txt_attendance_date.setText(new StringBuilder(attendanceModelList.get(position).getTodayDate()));
        holder.txt_attendance_teacher_name.setText(new StringBuilder(attendanceModelList.get(position).getTchName()));
    }

    @Override
    public int getItemCount() {
        return attendanceModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Unbinder unbinder;

        @BindView(R.id.linear_att_color)
        LinearLayout linear_att_color;
        @BindView(R.id.txt_attendance_date)
        TextView txt_attendance_date;
        @BindView(R.id.txt_attendance_teacher_name)
        TextView txt_attendance_teacher_name;
        @BindView(R.id.txt_attendance_status)
        TextView txt_attendance_status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
