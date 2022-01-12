package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_quiz;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sampleschooladmin.Adapter.QuizViewAdapter;
import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.databinding.FragmentQuizBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class QuizFragment extends Fragment {

    private QuizViewModel quizViewModel;
    private FragmentQuizBinding binding;

    RecyclerView recycler_quiz;
    TextView txt_no_quiz;
    FloatingActionButton fab_add_quiz;

    int classPos, l;

    QuizViewAdapter adapter;

    public static QuizFragment newInstance() {
        return new QuizFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        binding = FragmentQuizBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recycler_quiz = binding.recyclerQuiz;
        txt_no_quiz = binding.txtNoQuiz;
        fab_add_quiz = binding.fabAddQuiz;

        if (getArguments() != null) {
            classPos = getArguments().getInt("classPos");

            fab_add_quiz.setOnClickListener(addQuiz);

            quizViewModel.getErrorMessage().observe(getViewLifecycleOwner(), s -> {
                Snackbar.make(root, "" + s, Snackbar.LENGTH_SHORT).show();
            });


            quizViewModel.getQuizMutableLiveData(classPos).observe(getViewLifecycleOwner(), quizListModelList -> {
                if (quizListModelList != null && quizListModelList.size() > 0) {
                    txt_no_quiz.setVisibility(View.GONE);
                    adapter = new QuizViewAdapter(getContext(), quizListModelList);
                    recycler_quiz.setAdapter(adapter);
                }
            });
            recycler_quiz.setHasFixedSize(true);
            recycler_quiz.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }

        return root;
    }

    View.OnClickListener addQuiz = v -> {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.add_quiz_dialog, null);
        EditText edt_quiz_question_no = (EditText) itemView.findViewById(R.id.edt_quiz_question_no);
        EditText edt_quiz_subject = (EditText) itemView.findViewById(R.id.edt_quiz_subject);
        Button btn_f_quiz_cancel = (Button) itemView.findViewById(R.id.btn_f_quiz_cancel);
        Button btn_f_quiz_next = (Button) itemView.findViewById(R.id.btn_f_quiz_next);
        ImageButton img_subject_list = (ImageButton) itemView.findViewById(R.id.img_subject_list);
        builder.setView(itemView);
        AlertDialog dialog = builder.create();
        dialog.show();

        String[] subjects = new String[Common.classModels.getValue().get(classPos).getSubjects().size()];

        for (int i = 0; i < Common.classModels.getValue().get(classPos).getSubjects().size(); i++)
            subjects[i] = Common.classModels.getValue().get(classPos).getSubjects().get(i).getSubjectName();

        img_subject_list.setOnClickListener(view -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            builder1.setItems(subjects, (dialogInterface, i) -> edt_quiz_subject.setText(subjects[i]));
            AlertDialog dialog1 = builder1.create();
            dialog1.show();
        });


        btn_f_quiz_next.setOnClickListener(v14 -> {
            if (edt_quiz_question_no.getText().toString().isEmpty())
                edt_quiz_question_no.setError("Enter Number of Questions");

            if (edt_quiz_subject.getText().toString().isEmpty())
                edt_quiz_subject.setError("Enter Subject Name");

            if (!edt_quiz_question_no.getText().toString().isEmpty() &&
                    !edt_quiz_subject.getText().toString().isEmpty()) {

                l = Integer.parseInt(edt_quiz_question_no.getText().toString().trim());
                Bundle bundle = new Bundle();
                bundle.putInt("quizNo", l);
                bundle.putString("quizSub", edt_quiz_subject.getText().toString().trim());
                bundle.putInt("classPos", classPos);
                Navigation.findNavController(v).navigate(R.id.nav_add_quiz,bundle);
                dialog.dismiss();
            }
        });
        btn_f_quiz_cancel.setOnClickListener(v15 -> dialog.dismiss());

    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}