package com.myapp.viewmodel;

import android.content.Context;

import com.myapp.manager.ApiServiceManager;
import com.myapp.manager.response.StockViewResponse;
import com.myapp.model.StockView;
import com.myapp.widget.Constants;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockTWDataModel {

    public MutableLiveData<StockViewResponse> stockTWAPI(Context context) {

        final MutableLiveData<StockViewResponse> stockViewResponse = new MutableLiveData<>();
        
            ApiServiceManager.getApiServiceManager()
                             .StockTWAPI()
                             .enqueue(new Callback<List<StockView>>() {

                                 @Override
                                 public void onResponse(Call<List<StockView>> call,
                                                        Response<List<StockView>> response) {
                                     StockViewResponse res = new StockViewResponse();
                                     if (response.body() != null) {
                                         res.setResult(Constants.RESULT_SUCCESS);
                                         res.setList(response.body());
                                     }
                                     stockViewResponse.setValue(res);

                                 }

                                 @Override
                                 public void onFailure(Call<List<StockView>> call,
                                                       Throwable t) {
                                     t.printStackTrace();
                                     StockViewResponse res = new StockViewResponse();
                                     res.setResult(Constants.RESULT_FAIL);

                                     stockViewResponse.setValue(res);
                                 }
                             });

        return stockViewResponse;
    }
}
