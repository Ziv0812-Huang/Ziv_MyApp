package com.myapp.viewmodel;

import android.content.Context;

import com.myapp.manager.response.CommonResponse;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetectReportViewModel
        extends ViewModel {

    private DetectReportDataModel dataModel;

    public DetectReportViewModel(DetectReportDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public MutableLiveData<CommonResponse> detectReport(Context context, String jsonStr){
        return dataModel.detectReportAPI(context, jsonStr);
    }
}
