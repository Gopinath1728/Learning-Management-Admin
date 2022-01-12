package com.example.sampleschooladmin.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.sampleschooladmin.Common.FileUtils;
import com.example.sampleschooladmin.Model.StudentModel;
import com.example.sampleschooladmin.Model.TeacherModel;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.databinding.FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    ImageButton btn_add_students, btn_add_teachers,btn_view_students,btn_view_teachers,btn_view_announcements,btn_view_classes;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    Button btn_pick_file;
    FileInputStream fileInputStream;
    File file;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btn_add_students = binding.btnAddStudents;
        btn_add_teachers = binding.btnAddTeachers;
        btn_view_students = binding.btnViewStudents;
        btn_view_teachers = binding.btnViewTeachers;
        btn_view_announcements = binding.btnViewAnnouncements;
        btn_view_classes = binding.btnViewClasses;
        btn_pick_file = binding.btnPickFile;

        btn_pick_file.setOnClickListener(pickFile);

        btn_add_teachers.setOnClickListener(add_teachers);
        btn_add_students.setOnClickListener(add_students);

        btn_view_students.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_view_classes));
        btn_view_teachers.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_view_teachers));
        btn_view_announcements.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_announcement));
        btn_view_classes.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_view_classes));

        homeViewModel.getErrorMessage().observe(getViewLifecycleOwner(), s -> {
            Snackbar.make(root,""+s,Snackbar.LENGTH_SHORT).show();
        });

        homeViewModel.getClassModelListMutable().observe(getViewLifecycleOwner(), classModelList -> {

        });

        homeViewModel.getTeacherError().observe(getViewLifecycleOwner(), s -> {
            Snackbar.make(root,""+s,Snackbar.LENGTH_SHORT).show();
        });

        homeViewModel.getTeacherModelListMutable().observe(getViewLifecycleOwner(), teacherModelList -> {

        });



//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Classes");
//        List<ClassModel> classModelList = new ArrayList<>();
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot:snapshot.getChildren())
//                {
//                    ClassModel classModel = dataSnapshot.getValue(ClassModel.class);
//                    classModelList.add(classModel);
//                }
//                addToDatabase(classModelList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
//            }
//        });


        return root;
    }

//    private void addToDatabase(List<ClassModel> classModelList) {
//        for (int i=0;i<classModelList.size();i++)
//        {
//            int j=i;
//            int k = 65+j;
//            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//            ClassModel classModel = classModelList.get(j);
//            firestore.collection("Classes")
//                    .document("Class "+(char)k)
//                    .set(classModel)
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {

                    try {
                        fileInputStream = new FileInputStream(file);

                        HSSFWorkbook xssfWorkbook = new HSSFWorkbook(fileInputStream);
                        Sheet sheet = xssfWorkbook.getSheetAt(0);

                        List<StudentModel> studentModelList = new ArrayList<>();

                        for (int i=sheet.getFirstRowNum()+1;i<=sheet.getLastRowNum();i++)
                        {
                            StudentModel studentModel = new StudentModel();
                            Row row = sheet.getRow(i);

                            for(int j=row.getFirstCellNum();j<=row.getLastCellNum();j++)
                            {
                                Cell cell = row.getCell(j);

                                if (j==0)
                                    studentModel.setUid(cell.getStringCellValue());

                                if (j==1)
                                    studentModel.setStudentUid(cell.getStringCellValue());

                                if (j==2)
                                    studentModel.setStudentName(cell.getStringCellValue());

                                if (j==3)
                                    studentModel.setStudentEmail(cell.getStringCellValue());

                                if (j==4)
                                    studentModel.setStudentPassword(String.valueOf((int) cell.getNumericCellValue()));

                                if (j==5)
                                    studentModel.setStudentClass(cell.getStringCellValue());

                                if (j==6)
                                    studentModel.setStudentAddress(cell.getStringCellValue());

                                if (j==7)
                                    studentModel.setStudentParentName(cell.getStringCellValue());

                                if (j==8)
                                    studentModel.setStudentParentPhone(cell.getStringCellValue());

                                if (j==9)
                                    studentModel.setStudentImage(cell.getStringCellValue());

                                if (j==10)
                                    studentModel.setStudentToken(cell.getStringCellValue());

                                if (j==11)
                                    studentModel.setStudentRollNo(String.valueOf((int) cell.getNumericCellValue()));

                            }
                            studentModelList.add(studentModel);
                        }
                        int a = studentModelList.size();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getContext(), "You must grant permission to upload data.", Toast.LENGTH_SHORT).show();
                }
            });

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {


                        file = FileUtils.getFile(getContext(),result.getData().getData());
                        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

                    }
                }
            });


    View.OnClickListener pickFile = view -> {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("*/*");
        launcher.launch(intent);
    };



    View.OnClickListener add_teachers = view -> {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("add_teachers");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<TeacherModel> teacherModelList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TeacherModel studentModel = dataSnapshot.getValue(TeacherModel.class);
                        teacherModelList.add(studentModel);
                    }
                    if (!teacherModelList.isEmpty()) {
                        confirmAddteachers(teacherModelList, view);
                    }
                } else {
                    Snackbar.make(view, "No user data found.", Snackbar.LENGTH_LONG)
                            .setAction("OK", null)
                            .show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(view, "Realtime" + error, Snackbar.LENGTH_LONG)
                        .setAction("OK", null)
                        .show();
            }
        });
    };

    private void confirmAddteachers(List<TeacherModel> teacherModelList, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.add_more_users_dialog, null);
        TextView txt_users_dataView = (TextView) itemView.findViewById(R.id.txt_users_dataView);
        Button btn_cancel = (Button) itemView.findViewById(R.id.btn_cancel);
        Button btn_add = (Button) itemView.findViewById(R.id.btn_add);
        builder.setView(itemView);
        AlertDialog dialog = builder.create();
        dialog.show();
        txt_users_dataView.setText(new StringBuilder("There are " + teacherModelList.size() + " users. Confirm to add?"));

        btn_add.setOnClickListener(view1 -> {
            for (int j = 0; j < teacherModelList.size(); j++) {
                int i = j;
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(
                                teacherModelList.get(i).getTeacherEmail(),
                                teacherModelList.get(i).getTeacherPassword())
                        .addOnSuccessListener(authResult -> {
                            TeacherModel teacherModel = teacherModelList.get(i);
                            teacherModel.setUid(FirebaseAuth.getInstance().getUid());
                            firestore.collection("Teachers")
                                    .document(teacherModel.getUid())
                                    .set(teacherModel)
                                    .addOnSuccessListener(unused -> {
                                        Map<String, String> data = new HashMap<>();
                                        data.put("add_teachers", null);
                                        FirebaseDatabase.getInstance().getReference("add_teachers")
                                                .setValue(data)
                                                .addOnFailureListener(e -> Snackbar.make(view,"Delete add_teachers Error: "+e,Snackbar.LENGTH_LONG)
                                                        .setAction("OK",null).show());
                                    })
                                    .addOnFailureListener(e -> {
                                        Snackbar.make(view, "" + e, Snackbar.LENGTH_LONG)
                                                .setAction("OK", null)
                                                .show();
                                    });
                        })
                        .addOnFailureListener(e -> {
                            Snackbar.make(view, "" + e, Snackbar.LENGTH_LONG)
                                    .setAction("OK", null)
                                    .show();
                        });
            }
            Snackbar.make(view,"Teachers added Successfully",Snackbar.LENGTH_LONG)
                    .setAction("OK",null).show();
            dialog.dismiss();
        });

        btn_cancel.setOnClickListener(view12 -> {
            dialog.dismiss();
        });
    }

    View.OnClickListener add_students = view -> {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("add_students");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<StudentModel> studentModelList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        StudentModel studentModel = dataSnapshot.getValue(StudentModel.class);
                        studentModelList.add(studentModel);
                    }
                    if (!studentModelList.isEmpty()) {
                        confirmAddStudents(studentModelList, view);
                    }
                } else {
                    Snackbar.make(view, "No user data found.", Snackbar.LENGTH_LONG)
                            .setAction("OK", null)
                            .show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(view, "" + error, Snackbar.LENGTH_LONG)
                        .setAction("OK", null)
                        .show();
            }
        });
    };

    private void confirmAddStudents(List<StudentModel> studentModelList, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.add_more_users_dialog, null);
        TextView txt_users_dataView = (TextView) itemView.findViewById(R.id.txt_users_dataView);
        Button btn_cancel = (Button) itemView.findViewById(R.id.btn_cancel);
        Button btn_add = (Button) itemView.findViewById(R.id.btn_add);
        builder.setView(itemView);
        AlertDialog dialog = builder.create();
        dialog.show();
        txt_users_dataView.setText(new StringBuilder("There are " + studentModelList.size() + " users. Confirm to add?"));

        btn_add.setOnClickListener(view1 -> {
            for (int j = 0; j < studentModelList.size(); j++) {
                int i = j;
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(
                                studentModelList.get(i).getStudentEmail(),
                                studentModelList.get(i).getStudentPassword())
                        .addOnSuccessListener(authResult -> {
                            StudentModel studentModel = studentModelList.get(i);
                            studentModel.setUid(FirebaseAuth.getInstance().getUid());
                            
                            firestore.collection("Students")
                                    .document(studentModel.getUid())
                                    .set(studentModel)
                                    .addOnSuccessListener(unused -> {
                                        Map<String, String> data = new HashMap<>();
                                        data.put("add_students", null);
                                        FirebaseDatabase.getInstance().getReference("add_students")
                                                .setValue(data)
                                                .addOnFailureListener(e -> Snackbar.make(view,"Delete add_students Error: "+e,Snackbar.LENGTH_LONG)
                                                .setAction("OK",null).show());
                                    })
                                    .addOnFailureListener(e -> {
                                        Snackbar.make(view, "" + e, Snackbar.LENGTH_LONG)
                                                .setAction("OK", null)
                                                .show();
                                    });
                        })
                        .addOnFailureListener(e -> {
                            Snackbar.make(view, "" + e, Snackbar.LENGTH_LONG)
                                    .setAction("OK", null)
                                    .show();
                        });
            }
            Snackbar.make(view,"Students added Successfully",Snackbar.LENGTH_LONG)
                    .setAction("OK",null).show();
            dialog.dismiss();
        });

        btn_cancel.setOnClickListener(view12 -> {
            dialog.dismiss();
        });

    }

}