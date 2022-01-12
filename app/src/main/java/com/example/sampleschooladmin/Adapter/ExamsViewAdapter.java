package com.example.sampleschooladmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleschooladmin.Model.ExaminationModel;
import com.example.sampleschooladmin.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ExamsViewAdapter extends RecyclerView.Adapter<ExamsViewAdapter.MyViewHolder> {

    Context context;
    List<ExaminationModel> examinationModelList;

    public ExamsViewAdapter(Context context, List<ExaminationModel> examinationModelList) {
        this.context = context;
        this.examinationModelList = examinationModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExamsViewAdapter.MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.exam_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_exam_date.setText(new StringBuilder(examinationModelList.get(position).getExamDate()));
        holder.txt_exam_subject.setText(new StringBuilder(examinationModelList.get(position).getExamSbjtName()));
        holder.txt_exam_timeFrom.setText(new StringBuilder(examinationModelList.get(position).getExamTimeFrom()));
        holder.txt_exam_timeTo.setText(new StringBuilder(examinationModelList.get(position).getExamTimeTo()));
        holder.txt_exam_venue.setText(new StringBuilder(examinationModelList.get(position).getExamRoom()));
        holder.txt_exam_type.setText(new StringBuilder(examinationModelList.get(position).getExamType()));
    }

    @Override
    public int getItemCount() {
        return examinationModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;

        @BindView(R.id.txt_exam_subject)
        TextView txt_exam_subject;
        @BindView(R.id.txt_exam_date)
        TextView txt_exam_date;
        @BindView(R.id.txt_exam_venue)
        TextView txt_exam_venue;
        @BindView(R.id.txt_exam_timeFrom)
        TextView txt_exam_timeFrom;
        @BindView(R.id.txt_exam_timeTo)
        TextView txt_exam_timeTo;
        @BindView(R.id.txt_exam_type)
        TextView txt_exam_type;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
