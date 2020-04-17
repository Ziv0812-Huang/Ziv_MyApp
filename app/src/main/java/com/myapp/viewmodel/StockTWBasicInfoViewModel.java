package com.myapp.viewmodel;

import android.content.Context;

import com.myapp.manager.response.CommonResponse;
import com.myapp.manager.response.StockBasicInfoResponse;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StockTWBasicInfoViewModel
        extends ViewModel {

    private StockTWBasicInfoDataModel dataModel;

    public StockTWBasicInfoViewModel(StockTWBasicInfoDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public MutableLiveData<StockBasicInfoResponse> stockTWBasicInfo(Context context){
        return dataModel.stockBasicInfoAPI(context);
    }
}
