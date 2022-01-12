package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_exams;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sampleschooladmin.Adapter.ExamsViewAdapter;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Model.ExaminationModel;
import com.example.sampleschooladmin.Model.SubjectModel;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.databinding.FragmentClassExamsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ClassExamsFragment extends Fragment {

    private ClassExamsViewModel classExamsViewModel;

    private FragmentClassExamsBinding binding;

    int classPosition;

    RecyclerView recycler_examinations;
    TextView txt_no_exams;
    EditText edt_exam_subject,edt_exam_date,edt_exam_venue,edt_exam_timeFrom,edt_exam_timeTo,edt_exam_type;
    ImageButton img_exam_type_list,img_exam_subject_list;
    Button btn_exam_cancel,btn_exam_upload;
    FloatingActionButton fab_add_exam;
    ExamsViewAdapter adapter;

    public static ClassExamsFragment newInstance() {
        return new ClassExamsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        classExamsViewModel = new ViewModelProvider(this).get(ClassExamsViewModel.class);
        binding = FragmentClassExamsBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        recycler_examinations = binding.recyclerExaminations;
        txt_no_exams = binding.txtNoExams;
        fab_add_exam = binding.fabAddExam;

        fab_add_exam.setOnClickListener(addExamsClick);

        if (getArguments() != null)
        {
            classPosition = getArguments().getInt("classPos");

            classExamsViewModel.getErrorMutable().observe(getViewLifecycleOwner(), s -> Snackbar.make(root,""+s,Snackbar.LENGTH_LONG).show());

            classExamsViewModel.getExamMutableLiveData(classPosition).observe(getViewLifecycleOwner(), examinationModels -> {
                if (examinationModels != null && examinationModels.size()>0)
                {
                    txt_no_exams.setVisibility(View.GONE);
                    adapter = new ExamsViewAdapter(getContext(),examinationModels);
                    recycler_examinations.setAdapter(adapter);
                }
            });
            recycler_examinations.setHasFixedSize(true);
            recycler_examinations.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }

        return root;
    }




    View.OnClickListener addExamsClick = view -> {
        if (Common.classModels.getValue() != null){
            List<SubjectModel> subjectModelList = Common.classModels.getValue().get(classPosition).getSubjects();
            String[] subjects = new String[subjectModelList.size()];
            for (int i=0;i<subjectModelList.size();i++)
            {
                subjects[i] = subjectModelList.get(i).getSubjectName();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.add_exams_dialog,null);
            edt_exam_subject = (EditText)itemView.findViewById(R.id.edt_exam_subject);
            edt_exam_date = (EditText)itemView.findViewById(R.id.edt_exam_date);
            edt_exam_venue = (EditText)itemView.findViewById(R.id.edt_exam_venue);
            edt_exam_timeFrom = (EditText)itemView.findViewById(R.id.edt_exam_timeFrom);
            edt_exam_timeTo = (EditText)itemView.findViewById(R.id.edt_exam_timeTo);
            edt_exam_type = (EditText)itemView.findViewById(R.id.edt_exam_type);
            img_exam_type_list = (ImageButton)itemView.findViewById(R.id.img_exam_type_list);
            img_exam_subject_list = (ImageButton)itemView.findViewById(R.id.img_exam_subject_list);
            btn_exam_cancel = (Button)itemView.findViewById(R.id.btn_exam_cancel);
            btn_exam_upload = (Button)itemView.findViewById(R.id.btn_exam_upload);
            builder.setView(itemView);
            AlertDialog dialog = builder.create();
            dialog.show();

            img_exam_subject_list.setOnClickListener(view15 -> {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setItems(subjects, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        edt_exam_subject.setText(subjects[i]);
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog1 = builder1.create();
                dialog1.show();
            });

            String[] examTypes = {"Mid Term","End Term"};

            img_exam_type_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setItems(examTypes, (dialogInterface, i) -> {
                        edt_exam_type.setText(examTypes[i]);
                        dialogInterface.dismiss();
                    });
                    AlertDialog dialog1 = builder1.create();
                    dialog1.show();
                }
            });

            edt_exam_date.setOnClickListener(view13 -> {
                Calendar date = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date.set(Calendar.YEAR, i);
                        date.set(Calendar.MONTH, i1);
                        date.set(Calendar.DAY_OF_MONTH, i2);
                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        edt_exam_date.setText(new StringBuilder(""+sdf.format(date.getTime())));
                    }
                },date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            });

            edt_exam_timeFrom.setOnClickListener(view12 -> {
                Calendar mcurrentTime = Calendar.getInstance();

                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mcurrentTime.set(Calendar.HOUR_OF_DAY,selectedHour);
                        mcurrentTime.set(Calendar.MINUTE,selectedMinute);
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.US);
                        edt_exam_timeFrom.setText(new StringBuilder(""+sdf.format(mcurrentTime.getTime())));
                    }
                }, hour, minute, false);
                mTimePicker.show();
            });

            edt_exam_timeTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            mcurrentTime.set(Calendar.HOUR_OF_DAY,selectedHour);
                            mcurrentTime.set(Calendar.MINUTE,selectedMinute);
                            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.US);
                            edt_exam_timeTo.setText(new StringBuilder(""+sdf.format(mcurrentTime.getTime())));
                        }
                    }, hour, minute, false);//Yes 24 hour time
                    mTimePicker.show();
                }
            });

            btn_exam_cancel.setOnClickListener(view1 -> dialog.dismiss());

            btn_exam_upload.setOnClickListener(view14 -> {
                ExaminationModel examinationModel = new ExaminationModel();

                if (!edt_exam_subject.getText().toString().isEmpty())
                    examinationModel.setExamSbjtName(edt_exam_subject.getText().toString().trim());
                else
                    edt_exam_subject.setError("Field can't be empty !");

                if (!edt_exam_date.getText().toString().isEmpty())
                    examinationModel.setExamDate(edt_exam_date.getText().toString().trim());
                else
                    edt_exam_date.setError("Field can't be empty !");

                if (!edt_exam_venue.getText().toString().isEmpty())
                    examinationModel.setExamRoom(edt_exam_venue.getText().toString().trim());
                else
                    edt_exam_venue.setError("Field can't be empty !");

                if (!edt_exam_timeFrom.getText().toString().isEmpty())
                    examinationModel.setExamTimeFrom(edt_exam_timeFrom.getText().toString().trim());
                else
                    edt_exam_timeFrom.setError("Field can't be empty !");

                if (!edt_exam_timeTo.getText().toString().isEmpty())
                    examinationModel.setExamTimeTo(edt_exam_timeTo.getText().toString().trim());
                else
                    edt_exam_timeTo.setError("Field can't be empty !");

                if (!edt_exam_type.getText().toString().isEmpty())
                    examinationModel.setExamType(edt_exam_type.getText().toString().trim());
                else
                    edt_exam_type.setError("Field can't be empty !");

                if (!edt_exam_subject.getText().toString().isEmpty() &&
                        !edt_exam_date.getText().toString().isEmpty() &&
                        !edt_exam_venue.getText().toString().isEmpty() &&
                        !edt_exam_timeFrom.getText().toString().isEmpty() &&
                        !edt_exam_timeTo.getText().toString().isEmpty() &&
                        !edt_exam_type.getText().toString().isEmpty())
                {
                    examinationModel.setExamDocId(FirebaseFirestore.getInstance().collection("Classes")
                    .document(Common.classModels.getValue().get(classPosition).getDocId())
                    .collection("Examinations")
                    .document().getId());

                    FirebaseFirestore.getInstance().collection("Classes")
                            .document(Common.classModels.getValue().get(classPosition).getDocId())
                            .collection("Examinations")
                            .document(examinationModel.getExamDocId())
                            .set(examinationModel)
                            .addOnFailureListener(e -> {
                                dialog.dismiss();
                                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            })
                            .addOnSuccessListener(unused -> dialog.dismiss());
                }
            });
        }
    };





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}