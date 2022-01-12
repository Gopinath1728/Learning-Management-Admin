package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_quiz;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.Eventbus.OptionsEvent;
import com.example.sampleschooladmin.Model.QuizListModel;
import com.example.sampleschooladmin.Model.QuizModel;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.databinding.FragmentAddQuizBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AddQuizFragment extends Fragment {

    private FragmentAddQuizBinding binding;

    String subject, date;
    int l, count = 1, classPos;

    TextView txt_quiz_question,txt_quiz_subject,txt_quiz_Date;
    EditText edt_quiz_question, edt_quiz_question_time, edt_quiz_crtAnswer;
    Button btn_quiz_cancel, btn_quiz_next;
    List<QuizModel> quizModelList = new ArrayList<>();


    EditText edt_add_option;
    Button btn_add_option;
    List<String> optionsList = new ArrayList<>();
    LinearLayout linear_show_options;
    RecyclerView recycler_options;
    OptionsAdapter adapter;
    QuizModel q = new QuizModel();
    Boolean back = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddQuizBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        if (getArguments() != null)
        {
            l = getArguments().getInt("quizNo");
            subject = getArguments().getString("quizSub");
            date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            classPos = getArguments().getInt("classPos");

            txt_quiz_subject = binding.txtQuizSubject;
            txt_quiz_Date = binding.txtQuizDate;
            txt_quiz_question = binding.txtQuizQuestion;
            edt_quiz_question = binding.edtQuizQuestion;
            edt_quiz_question_time = binding.edtQuizQuestionTime;
            edt_quiz_crtAnswer = binding.edtQuizCrtAnswer;
            btn_quiz_cancel = binding.btnQuizCancel;
            btn_quiz_next = binding.btnQuizNext;


            edt_add_option = binding.edtAddOption;
            btn_add_option = binding.btnAddOption;
            linear_show_options = binding.linearShowOptions;
            recycler_options = binding.recyclerOptions;

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            linear_show_options.setLayoutParams(layoutParams);

            txt_quiz_subject.setText(new StringBuilder(subject));
            txt_quiz_Date.setText(new StringBuilder(date));


            if (l == 1) {
                btn_quiz_next.setText(new StringBuilder("Finish"));
            }
            btn_quiz_next.setOnClickListener(nextClick);

            btn_add_option.setOnClickListener(addOptions);

            btn_quiz_cancel.setOnClickListener(cancelBtnClick);


            adapter = new OptionsAdapter(getContext(), optionsList);
            recycler_options.setAdapter(adapter);
            recycler_options.setHasFixedSize(true);
            recycler_options.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        }

        return root;
    }

    View.OnClickListener addOptions = v -> {
        if (!edt_add_option.getText().toString().isEmpty()) {

            optionsList.add(edt_add_option.getText().toString().trim());
            adapter.notifyDataSetChanged();
            edt_add_option.getText().clear();

        } else
            edt_add_option.setError("Field Empty !");
    };

    View.OnClickListener cancelBtnClick = v -> {
        if (btn_quiz_cancel.getText().toString().equals("Back")) {
            back = true;
            btn_quiz_next.setText("Next");
            --count;
            optionsList = new ArrayList<>();
            optionsList = quizModelList.get(count - 1).getOptions();
            adapter = new OptionsAdapter(getContext(), optionsList);
            recycler_options.setAdapter(adapter);
            recycler_options.setHasFixedSize(true);
            recycler_options.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            if (count <= l)
                txt_quiz_question.setText(new StringBuilder("Question " + count));
            edt_quiz_question.setText(quizModelList.get(count - 1).getQuestion());
            edt_quiz_crtAnswer.setText(quizModelList.get(count - 1).getCrtOption());
            edt_quiz_question_time.setText(quizModelList.get(count - 1).getTimePerQ());
        }
        if (btn_quiz_cancel.getText().toString().equals("CANCEL")) {
            getActivity().onBackPressed();
        }
    };

    View.OnClickListener nextClick = v -> {
        btn_quiz_cancel.setText("Back");
        if (back) {
            changeData();
            back = false;
            recycler_options.removeAllViews();
            return;
        }

        ++count;
        recycler_options.removeAllViews();


        if (count == l + 1) {
            getData();
            addComplete();
        } else
            getData();


    };

    private void changeData() {
        Boolean minus = null;

        if (edt_quiz_question.getText().toString().isEmpty()) {
            edt_quiz_question.setError("Field can't be Empty.");
            minus = true;
        } else {
            quizModelList.get(count - 1).setQuestion(edt_quiz_question.getText().toString());
            edt_quiz_question.getText().clear();
        }

        if (edt_quiz_question_time.getText().toString().isEmpty()) {
            edt_quiz_question_time.setError("Field can't be Empty.");
            minus = true;
        } else {
            quizModelList.get(count - 1).setTimePerQ(edt_quiz_question_time.getText().toString());
        }

        if (edt_quiz_crtAnswer.getText().toString().isEmpty()) {
            edt_quiz_crtAnswer.setError("Field can't be Empty.");
            minus = true;
        } else {
            quizModelList.get(count - 1).setCrtOption(edt_quiz_crtAnswer.getText().toString());
            edt_quiz_crtAnswer.getText().clear();
        }
        if (!(optionsList.size() > 0)) {
            edt_add_option.setError("No options Added");
            minus = true;
        } else {

            List<String> list = new ArrayList<>();
            list.addAll(optionsList);
            quizModelList.get(count - 1).setOptions(list);
            optionsList.clear();
        }
        ++count;
        if (count <= l)
            txt_quiz_question.setText(new StringBuilder("Question " + count));

        if (count == l)
            btn_quiz_next.setText(new StringBuilder("Finish"));

    }

    private void getData() {
        Boolean minus = null;
        QuizModel quizModel = new QuizModel();

        if (edt_quiz_question.getText().toString().isEmpty()) {
            edt_quiz_question.setError("Field can't be Empty.");
            minus = true;
        } else {
            quizModel.setQuestion(edt_quiz_question.getText().toString());
            edt_quiz_question.getText().clear();
        }

        if (edt_quiz_question_time.getText().toString().isEmpty()) {
            edt_quiz_question_time.setError("Field can't be Empty.");
            minus = true;
        } else {
            quizModel.setTimePerQ(edt_quiz_question_time.getText().toString());
        }

        if (edt_quiz_crtAnswer.getText().toString().isEmpty()) {
            edt_quiz_crtAnswer.setError("Field can't be Empty.");
            minus = true;
        } else {
            quizModel.setCrtOption(edt_quiz_crtAnswer.getText().toString());
            edt_quiz_crtAnswer.getText().clear();
        }
        if (!(optionsList.size() > 0)) {
            edt_add_option.setError("No options Added");
            minus = true;
        } else {

            List<String> list = new ArrayList<>();
            list.addAll(optionsList);
            quizModel.setOptions(list);
            optionsList.clear();
        }

        if (minus != null && minus)
            --count;
        else {
            quizModelList.add(quizModel);

            minus = false;
        }
        if (count <= l)
            txt_quiz_question.setText(new StringBuilder("Question " + count));

        if (count == l)
            btn_quiz_next.setText(new StringBuilder("Finish"));
    }

    private void addComplete() {

        if (l == 1)
            getData();
        if (quizModelList.size() > 0) {
            QuizListModel quizListModel = new QuizListModel(
                    "Administrator", subject, date,"", quizModelList);
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            quizListModel.setQuizDocId(firestore.collection("Classes")
                    .document(Common.classModels.getValue().get(classPos).getDocId())
                    .collection("Quiz").document().getId());
            firestore.collection("Classes")
                    .document(Common.classModels.getValue().get(classPos).getDocId())
                    .collection("Quiz")
                    .document(quizListModel.getQuizDocId())
                    .set(quizListModel)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(getContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();

                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
                    });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    @Override
    public void onStop() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onListChangedEvent(OptionsEvent event) {
        if (event.getDelete()) {
            optionsList.remove(event.getPosition());
            adapter.notifyItemRemoved(event.getPosition());
        } else {
            optionsList.set(event.getPosition(), event.getText());
            adapter.notifyItemChanged(event.getPosition());
        }
    }
}

class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.MyViewHolder> {

    Context context;
    List<String> options;

    public OptionsAdapter(Context context, List<String> options) {
        this.context = context;
        this.options = options;
    }

    @NonNull
    @Override
    public OptionsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OptionsAdapter.MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.options_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OptionsAdapter.MyViewHolder holder, int position) {
        holder.txt_option.setText(new StringBuilder((position + 1) + " " + options.get(position)));
        holder.img_btn_cancel_option.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete Option");
            builder.setMessage("Do you want to delete " + options.get(position) + " ?");
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.setPositiveButton("Delete", (dialog, which) -> {
                EventBus.getDefault().post(new OptionsEvent(position, "Delete", true));
                dialog.dismiss();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        holder.txt_option.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                EditText editText = new EditText(context);
                editText.setLayoutParams(layoutParams);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Edit Option");
                builder.setView(editText);
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                builder.setPositiveButton("OK", (dialog, which) -> {
                    EventBus.getDefault().post(new OptionsEvent(holder.getAdapterPosition(),
                            editText.getText().toString().trim(), false));
                    dialog.dismiss();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;

        @BindView(R.id.txt_option)
        TextView txt_option;
        @BindView(R.id.img_btn_cancel_option)
        ImageButton img_btn_cancel_option;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }


}