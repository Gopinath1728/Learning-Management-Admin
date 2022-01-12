package com.example.sampleschooladmin.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sampleschooladmin.Model.AnnouncementModel;
import com.example.sampleschooladmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder> {

    Context context;
    List<AnnouncementModel> announcementModelList;

    public AnnouncementAdapter(Context context, List<AnnouncementModel> announcementModelList) {
        this.context = context;
        this.announcementModelList = announcementModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.announcement_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_announcement_date.setText(new StringBuilder(announcementModelList.get(position).getDate()));
        holder.txt_announcement_topic.setText(new StringBuilder(announcementModelList.get(position).getAnnouncementTitle()));
        holder.txt_announcement_owner.setText(new StringBuilder(announcementModelList.get(position).getOwner()));

        holder.img_announcement_delete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete Announcement");
            builder.setMessage("Do you want to delete ' "+announcementModelList.get(position).getAnnouncementTitle()+" ' ?");
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.setPositiveButton("Delete", (dialogInterface, i) -> {
                FirebaseFirestore.getInstance().collection("Announcements")
                        .document(announcementModelList.get(position).getAnnounceId())
                        .delete()
                        .addOnFailureListener(e -> {
                            dialogInterface.dismiss();
                            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        })
                        .addOnSuccessListener(unused -> dialogInterface.dismiss());
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        Bundle bundle = new Bundle();
        bundle.putInt("announcementPos",position);
        holder.itemView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_announcement_detail,bundle));
    }

    @Override
    public int getItemCount() {
        return announcementModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;

        @BindView(R.id.txt_announcement_date)
        TextView txt_announcement_date;
        @BindView(R.id.txt_announcement_topic)
        TextView txt_announcement_topic;
        @BindView(R.id.txt_announcement_owner)
        TextView txt_announcement_owner;
        @BindView(R.id.img_announcement_delete)
        ImageButton img_announcement_delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
