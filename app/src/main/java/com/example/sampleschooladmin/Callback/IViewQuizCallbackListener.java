package com.example.sampleschooladmin.Callback;



import com.example.sampleschooladmin.Model.QuizListModel;

import java.util.List;

public interface IViewQuizCallbackListener {
    void onQuizLoadSuccess(List<QuizListModel> quizListModelList);
    void onQuizLoadFailed(String quizLoadError);
}
