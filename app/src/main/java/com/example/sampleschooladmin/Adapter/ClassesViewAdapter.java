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

import com.example.sampleschooladmin.Model.ClassModel;
import com.example.sampleschooladmin.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ClassesViewAdapter extends RecyclerView.Adapter<ClassesViewAdapter.MyViewHolder> {

    Context context;
    List<ClassModel> classModelList;

    public ClassesViewAdapter(Context context, List<ClassModel> classModelList) {
        this.context = context;
        this.classModelList = classModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClassesViewAdapter.MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.view_class_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_class_name.setText(new StringBuilder(classModelList.get(position).getClassName()));
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        holder.itemView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_view_class_data,bundle));
    }

    @Override
    public int getItemCount() {
        return classModelList.size();
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
        if (classModelList.size() == 1)
            return 0;
        else
        {
            if (classModelList.size() % 2 == 0)
                return 0;
            else
                return (position > 1 && position == classModelList.size()-1) ? 1:0;
        }

    }
}
