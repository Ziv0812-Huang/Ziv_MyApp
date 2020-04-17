package com.myapp.viewmodel;

import android.content.Context;

import com.myapp.manager.response.TvProgramResponse;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TvProgramViewModel
        extends ViewModel {

    private TvProgramDataModel dataModel;

    public TvProgramViewModel(TvProgramDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public MutableLiveData<TvProgramResponse> tvProgramAPI(Context context){
        return dataModel.tvProgramAPI(context);
    }
}
