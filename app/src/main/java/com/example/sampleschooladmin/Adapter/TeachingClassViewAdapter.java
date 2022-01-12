package com.example.sampleschooladmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleschooladmin.Model.TeachingClass;
import com.example.sampleschooladmin.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TeachingClassViewAdapter extends RecyclerView.Adapter<TeachingClassViewAdapter.MyViewHolder> {

    Context context;
    List<TeachingClass> teachingClassList;

    public TeachingClassViewAdapter(Context context, List<TeachingClass> teachingClassList) {
        this.context = context;
        this.teachingClassList = teachingClassList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeachingClassViewAdapter.MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.teaching_classes_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_teaching_class_name.setText(new StringBuilder(teachingClassList.get(position).getClassName()));
        holder.txt_teaching_subject_name.setText(new StringBuilder(teachingClassList.get(position).getSubjectName()));
    }

    @Override
    public int getItemCount() {
        return teachingClassList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;

        @BindView(R.id.txt_teaching_subject_name)
        TextView txt_teaching_subject_name;
        @BindView(R.id.txt_teaching_class_name)
        TextView txt_teaching_class_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder= ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (teachingClassList.size() == 1)
            return 0;
        else
        {
            if (teachingClassList.size() % 2 == 0)
                return 0;
            else
                return (position > 1 && position == teachingClassList.size()-1) ? 1:0;
        }

    }
}
