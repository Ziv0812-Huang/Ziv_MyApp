package com.myapp.viewmodel;

import android.content.Context;

import com.myapp.manager.ApiServiceManager;
import com.myapp.manager.response.TvProgramDatas;
import com.myapp.manager.response.TvProgramResponse;
import com.myapp.model.TvProgram;
import com.myapp.widget.Constants;

import java.util.List;

import javax.inject.Singleton;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvProgramDataModel {

    public MutableLiveData<TvProgramResponse> tvProgramAPI(Context context) {

        final MutableLiveData<TvProgramResponse> tvProgramResponse = new MutableLiveData<>();
        
            ApiServiceManager.getApiServiceManager()
                             .TvProgramListAPI()
                             .enqueue(new Callback<TvProgramDatas>() {

                                 @Override
                                 public void onResponse(Call<TvProgramDatas> call,
                                                        Response<TvProgramDatas> response) {
                                     TvProgramResponse res = new TvProgramResponse();
                                     if (response.body() != null) {
                                         res.setResult(Constants.RESULT_SUCCESS);
                                         res.setDatas(response.body());
                                     }
                                     tvProgramResponse.setValue(res);

                                 }

                                 @Override
                                 public void onFailure(Call<TvProgramDatas> call,
                                                       Throwable t) {
                                     t.printStackTrace();
                                     TvProgramResponse res = new TvProgramResponse();
                                     res.setResult(Constants.RESULT_FAIL);

                                     tvProgramResponse.setValue(res);
                                 }
                             });

        return tvProgramResponse;
    }
}
