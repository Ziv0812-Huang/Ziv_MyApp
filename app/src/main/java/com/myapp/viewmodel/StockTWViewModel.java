package com.myapp.viewmodel;

import android.content.Context;

import com.myapp.manager.response.StockViewResponse;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StockTWViewModel
        extends ViewModel {

    private StockTWDataModel dataModel;

    public StockTWViewModel(StockTWDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public MutableLiveData<StockViewResponse> stockTW(Context context){
        return dataModel.stockTWAPI(context);
    }
}
