package com.example.sampleschooladmin.ui.announcement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampleschooladmin.Adapter.AnnouncementAdapter;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Model.AnnouncementModel;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.databinding.FragmentAnnouncementBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AnnouncementFragment extends Fragment {

    private FragmentAnnouncementBinding binding;

    RecyclerView recycler_announcements;
    AnnouncementAdapter adapter;
    AnnouncementFragmentViewModel viewModel;
    FloatingActionButton fab_add_announcement;
    TextView txt_no_announcements;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel = new ViewModelProvider(this).get(AnnouncementFragmentViewModel.class);
        binding = FragmentAnnouncementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recycler_announcements = binding.recyclerAnnouncements;
        fab_add_announcement = binding.fabAddAnnouncement;
        txt_no_announcements = binding.txtNoAnnouncements;

        fab_add_announcement.setOnClickListener(addAnnouncement);

        viewModel.getAnnouncementErrorMessage().observe(getViewLifecycleOwner(), s -> Snackbar.make(root, "" + s, Snackbar.LENGTH_SHORT).show());

        viewModel.getAnnouncementListMutableLiveData().observe(getViewLifecycleOwner(), announcementModels -> {
            if (announcementModels != null && announcementModels.size() > 0) {
                txt_no_announcements.setVisibility(View.GONE);
                adapter = new AnnouncementAdapter(getContext(), announcementModels);
                recycler_announcements.setAdapter(adapter);
            }
        });

        recycler_announcements.setHasFixedSize(true);
        recycler_announcements.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));


        return root;
    }

    View.OnClickListener addAnnouncement = view -> {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.add_announcement_dialog, null);
        TextView txt_announce_date = (TextView) itemView.findViewById(R.id.txt_announce_date);
        EditText edt_announcement_title = (EditText) itemView.findViewById(R.id.edt_announcement_title);
        EditText edt_announcement_body = (EditText) itemView.findViewById(R.id.edt_announcement_body);
        EditText edt_announcement_owner = (EditText) itemView.findViewById(R.id.edt_announcement_owner);
        Button btn_cancel_announcement = (Button) itemView.findViewById(R.id.btn_cancel_announcement);
        Button btn_add_announcement = (Button) itemView.findViewById(R.id.btn_add_announcement);
        builder.setView(itemView);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        txt_announce_date.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));


        btn_cancel_announcement.setOnClickListener(view1 -> dialog.dismiss());

        btn_add_announcement.setOnClickListener(view12 -> {
            AnnouncementModel announcementModel = new AnnouncementModel();
            announcementModel.setDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
            if (!edt_announcement_title.getText().toString().isEmpty()) {
                announcementModel.setAnnouncementTitle(edt_announcement_title.getText().toString().trim());
            } else {
                edt_announcement_title.setError("Title cannot be empty !");
            }

            if (!edt_announcement_body.getText().toString().isEmpty()) {
                announcementModel.setAnnouncementBody(edt_announcement_body.getText().toString().trim());
            } else {
                edt_announcement_body.setError("Body cannot be empty !");
            }

            if (!edt_announcement_owner.getText().toString().isEmpty()) {
                announcementModel.setOwner(edt_announcement_owner.getText().toString().trim());
            } else {
                edt_announcement_owner.setError("Owner cannot be empty !");
            }

            if (!edt_announcement_title.getText().toString().isEmpty() &&
                    !edt_announcement_body.getText().toString().isEmpty() &&
                    !edt_announcement_owner.getText().toString().isEmpty()) {
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                String id = firebaseFirestore.collection("Announcements").document().getId();
                announcementModel.setAnnounceId(id);

                firebaseFirestore
                        .collection("Announcements")
                        .document(id)
                        .set(announcementModel)
                        .addOnSuccessListener(documentReference -> {
                            dialog.dismiss();

                        })
                        .addOnFailureListener(e -> {
                            dialog.dismiss();
                            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
                        });
            }

        });
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}