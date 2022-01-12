package com.example.sampleschooladmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sampleschooladmin.Model.QuizListModel;
import com.example.sampleschooladmin.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class QuizViewAdapter extends RecyclerView.Adapter<QuizViewAdapter.MyViewHolder> {

    Context context;
    List<QuizListModel> quizModelList;

    public QuizViewAdapter(Context context, List<QuizListModel> quizModelList) {
        this.context = context;
        this.quizModelList = quizModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.quiz_view_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_quiz_subject_name.setText(new StringBuilder(quizModelList.get(position).getSubject()));
        holder.txt_quiz_item_date.setText(new StringBuilder(quizModelList.get(position).getDate()));
        holder.txt_quiz_teacher_name.setText(new StringBuilder(quizModelList.get(position).getTeacherName()));

    }

    @Override
    public int getItemCount() {
        return quizModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;

        @BindView(R.id.txt_quiz_subject_name)
        TextView txt_quiz_subject_name;
        @BindView(R.id.txt_quiz_item_date)
        TextView txt_quiz_item_date;
        @BindView(R.id.txt_quiz_teacher_name)
        TextView txt_quiz_teacher_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder= ButterKnife.bind(this,itemView);
        }
    }
}
