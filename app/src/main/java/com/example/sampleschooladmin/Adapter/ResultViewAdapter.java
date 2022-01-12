package com.example.sampleschooladmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleschooladmin.Model.ResultModel;
import com.example.sampleschooladmin.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ResultViewAdapter extends RecyclerView.Adapter<ResultViewAdapter.MyViewHolder> {

    Context context;
    List<ResultModel> resultModelList;

    public ResultViewAdapter(Context context, List<ResultModel> resultModelList) {
        this.context = context;
        this.resultModelList = resultModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.result_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_result_exam_type.setText(new StringBuilder(resultModelList.get(position).getExamType()));
        holder.txt_result_subject_name.setText(new StringBuilder(resultModelList.get(position).getSubjectName()));
        holder.txt_marks_obtained.setText(new StringBuilder(resultModelList.get(position).getMarksObtained()));
        holder.txt_total_marks.setText(new StringBuilder(resultModelList.get(position).getTotalMarks()));
        float m = Integer.parseInt(resultModelList.get(position).getMarksObtained());
        float Tm = Integer.parseInt(resultModelList.get(position).getTotalMarks());
        float percentage = Math.round(100*(m/Tm));

        holder.text_result_progress.setText(new StringBuilder(""+(int)percentage+"%"));

        if (percentage >0 && percentage <=40)
            holder.progress_result.setIndicatorColor(ContextCompat.getColor(context,R.color.dark_red));
        if (percentage>40 && percentage<=60)
            holder.progress_result.setIndicatorColor(ContextCompat.getColor(context,R.color.orange));
        if (percentage>60 && percentage<=80)
            holder.progress_result.setIndicatorColor(ContextCompat.getColor(context,R.color.dark_blue));
        if (percentage>80 && percentage<=100)
            holder.progress_result.setIndicatorColor(ContextCompat.getColor(context,R.color.light_green));
        holder.progress_result.setProgress((int)percentage);
    }

    @Override
    public int getItemCount() {
        return resultModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;

        @BindView(R.id.txt_result_exam_type)
        TextView txt_result_exam_type;
        @BindView(R.id.txt_result_subject_name)
        TextView txt_result_subject_name;
        @BindView(R.id.txt_marks_obtained)
        TextView txt_marks_obtained;
        @BindView(R.id.txt_total_marks)
        TextView txt_total_marks;
        @BindView(R.id.text_result_progress)
        TextView text_result_progress;
        @BindView(R.id.progress_result)
        CircularProgressIndicator progress_result;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
