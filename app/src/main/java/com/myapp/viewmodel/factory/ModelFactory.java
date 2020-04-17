package com.myapp.viewmodel.factory;


import com.myapp.room.DataRepository;
import com.myapp.viewmodel.DetectReportDataModel;
import com.myapp.viewmodel.DetectReportViewModel;
import com.myapp.viewmodel.StockTWBasicInfoDataModel;
import com.myapp.viewmodel.StockTWBasicInfoViewModel;
import com.myapp.viewmodel.StockTWDataModel;
import com.myapp.viewmodel.StockTWViewModel;
import com.myapp.viewmodel.TvProgramDataModel;
import com.myapp.viewmodel.TvProgramViewModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ModelFactory
        implements ViewModelProvider.Factory {

    private DetectReportDataModel detectReportDataModel;
    private StockTWBasicInfoDataModel stockTWBasicInfoDataModel;
    private StockTWDataModel stockTWDataModel;
    private TvProgramDataModel tvProgramDataModel;

    public ModelFactory() {
        detectReportDataModel = new DetectReportDataModel();
        stockTWBasicInfoDataModel = new StockTWBasicInfoDataModel();
        stockTWDataModel = new StockTWDataModel();
        tvProgramDataModel = new TvProgramDataModel();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetectReportViewModel.class)) {
            return (T) new DetectReportViewModel(detectReportDataModel);
        } else if (modelClass.isAssignableFrom(StockTWBasicInfoViewModel.class)) {
            return (T) new StockTWBasicInfoViewModel(stockTWBasicInfoDataModel);
        } else if (modelClass.isAssignableFrom(StockTWViewModel.class)) {
            return (T) new StockTWViewModel(stockTWDataModel);
        } else if (modelClass.isAssignableFrom(TvProgramViewModel.class)) {
            return (T) new TvProgramViewModel(tvProgramDataModel);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
