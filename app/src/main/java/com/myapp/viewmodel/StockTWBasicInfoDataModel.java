package com.myapp.viewmodel;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.myapp.manager.ApiServiceManager;
import com.myapp.manager.response.StockBasicInfoResponse;
import com.myapp.model.StockBasicInfo;
import com.myapp.manager.response.CommonResponse;
import com.myapp.widget.Constants;


import java.io.IOException;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockTWBasicInfoDataModel {

    public MutableLiveData<StockBasicInfoResponse> stockBasicInfoAPI(Context context) {

        final MutableLiveData<StockBasicInfoResponse> stockBasicInfoResponse = new MutableLiveData<>();
        
            ApiServiceManager.getApiServiceManager()
                             .StockTWBasicInfoAPI()
                             .enqueue(new Callback<List<StockBasicInfo>>() {

                                 @Override
                                 public void onResponse(Call<List<StockBasicInfo>> call,
                                                        Response<List<StockBasicInfo>> response) {
                                     StockBasicInfoResponse res = new StockBasicInfoResponse();
                                     if (response.body() != null) {
                                         res.setResult(Constants.RESULT_SUCCESS);
                                         res.setList(response.body());
                                     }
                                     stockBasicInfoResponse.setValue(res);

                                 }

                                 @Override
                                 public void onFailure(Call<List<StockBasicInfo>> call,
                                                       Throwable t) {
                                     t.printStackTrace();
                                     StockBasicInfoResponse res = new StockBasicInfoResponse();
                                     res.setResult(Constants.RESULT_FAIL);

                                     stockBasicInfoResponse.setValue(res);
                                 }
                             });

        return stockBasicInfoResponse;
    }
}
