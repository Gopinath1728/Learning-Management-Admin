package com.example.sampleschooladmin.Callback;


import com.example.sampleschooladmin.Model.ResultModel;

import java.util.List;

public interface IResultViewCallbackListener {
    void onResultLoadSuccess(List<ResultModel> resultModelList);
    void onResultLoadFailed(String resultLoadError);
}
