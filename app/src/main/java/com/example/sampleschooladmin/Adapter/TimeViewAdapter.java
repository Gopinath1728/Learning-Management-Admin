package com.example.sampleschooladmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleschooladmin.Model.TimeModel;
import com.example.sampleschooladmin.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TimeViewAdapter extends RecyclerView.Adapter<TimeViewAdapter.MyViewHolder> {

    Context context;
    List<TimeModel> timeModelList;

    public TimeViewAdapter(Context context, List<TimeModel> timeModelList) {
        this.context = context;
        this.timeModelList = timeModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TimeViewAdapter.MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.time_table_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_subject_name.setText(new StringBuilder(timeModelList.get(position).getSubjName()));
        holder.txt_time_from.setText(new StringBuilder(timeModelList.get(position).getTimeFrom()));
        holder.txt_time_to.setText(new StringBuilder(timeModelList.get(position).getTimeTo()));
        if (timeModelList.get(position).getStatus().equals("Going on"))
        {
            holder.txt_subject_status.setVisibility(View.VISIBLE);
            holder.txt_subject_status.setText(new StringBuilder("Going On"));
        }
    }

    @Override
    public int getItemCount() {
        return timeModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;

        @BindView(R.id.txt_subject_name)
        TextView txt_subject_name;
        @BindView(R.id.txt_time_from)
        TextView txt_time_from;
        @BindView(R.id.txt_time_to)
        TextView txt_time_to;
        @BindView(R.id.txt_subject_status)
        TextView txt_subject_status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (timeModelList.size() == 1)
            return 0;
        else
        {
            if (timeModelList.size() % 2 == 0)
                return 0;
            else
                return (position > 1 && position == timeModelList.size()-1) ? 1:0;
        }

    }
}
