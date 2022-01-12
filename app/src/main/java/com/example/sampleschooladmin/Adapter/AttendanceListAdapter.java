package com.example.sampleschooladmin.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Model.AttendanceModel;
import com.example.sampleschooladmin.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.MyViewHolder> {

    Context context;
    Map<String, List<String>> attendanceData;
    List<AttendanceModel> attendanceModelList;

    public AttendanceListAdapter(Context context, Map<String, List<String>> attendanceData, List<AttendanceModel> attendanceModelList) {
        this.context = context;
        this.attendanceData = attendanceData;
        this.attendanceModelList = attendanceModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.attendance_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_attendance_subject_name.setText(attendanceModelList.get(position).getSubjectName());
        float total = attendanceData.get(attendanceModelList.get(position).getSubjectName()).size();
        float present = Collections.frequency(attendanceData.get(attendanceModelList.get(position).getSubjectName()),"Present");
        holder.txt_classes_delivered.setText(new StringBuilder(""+(int)total));
        holder.txt_classes_attended.setText(new StringBuilder(""+(int)present));
        float aggregate = Math.round(100*(present/total));
        if (aggregate >0 && aggregate <=25)
            holder.progress_attendance.setIndicatorColor(ContextCompat.getColor(context,R.color.dark_red));
        if (aggregate>25 && aggregate<=50)
            holder.progress_attendance.setIndicatorColor(ContextCompat.getColor(context,R.color.orange));
        if (aggregate>50 && aggregate<=75)
            holder.progress_attendance.setIndicatorColor(ContextCompat.getColor(context,R.color.dark_blue));
        if (aggregate>75 && aggregate<=100)
            holder.progress_attendance.setIndicatorColor(ContextCompat.getColor(context,R.color.light_green));
        holder.progress_attendance.setProgress((int)aggregate);
        holder.text_view_progress.setText(new StringBuilder(""+((int)aggregate)+"%"));


        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        bundle.putString("subjectName", attendanceModelList.get(position).getSubjectName());
        holder.itemView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_attendance_detail,bundle));

    }

    @Override
    public int getItemCount() {
        return attendanceData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;

        @BindView(R.id.progress_attendance)
        CircularProgressIndicator progress_attendance;
        @BindView(R.id.text_view_progress)
        TextView text_view_progress;
        @BindView(R.id.txt_attendance_subject_name)
        TextView txt_attendance_subject_name;
        @BindView(R.id.txt_classes_attended)
        TextView txt_classes_attended;
        @BindView(R.id.txt_classes_delivered)
        TextView txt_classes_delivered;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
