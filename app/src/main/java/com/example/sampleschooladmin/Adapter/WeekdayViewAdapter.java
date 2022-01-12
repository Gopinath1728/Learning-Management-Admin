package com.example.sampleschooladmin.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleschooladmin.Model.TimetableModel;
import com.example.sampleschooladmin.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WeekdayViewAdapter extends RecyclerView.Adapter<WeekdayViewAdapter.MyViewHolder> {

    Context context;
    List<TimetableModel> timetableModels;
    int classPosition;

    public WeekdayViewAdapter(Context context, List<TimetableModel> timetableModels, int classPosition) {
        this.context = context;
        this.timetableModels = timetableModels;
        this.classPosition = classPosition;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeekdayViewAdapter.MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.view_class_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_class_name.setText(new StringBuilder(timetableModels.get(position).getDayName()));
        Bundle bundle =new Bundle();
        bundle.putInt("classPosition",classPosition);
        bundle.putInt("weekPosition",position);
        holder.itemView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_time_fragment,bundle));
    }

    @Override
    public int getItemCount() {
        return timetableModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;

        @BindView(R.id.txt_class_name)
        TextView txt_class_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder= ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (timetableModels.size() == 1)
            return 0;
        else
        {
            if (timetableModels.size() % 2 == 0)
                return 0;
            else
                return (position > 1 && position == timetableModels.size()-1) ? 1:0;
        }

    }
}
