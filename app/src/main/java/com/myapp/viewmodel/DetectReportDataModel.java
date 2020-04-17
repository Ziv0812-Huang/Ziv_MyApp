package com.myapp.viewmodel;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.myapp.manager.ApiServiceManager;
import com.myapp.manager.request.DetectRequest;
import com.myapp.manager.response.CommonResponse;
import com.myapp.model.DeviceAttributes;
import com.myapp.utils.DeviceUtilities;
import com.myapp.widget.Constants;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.BuildConfig.BASE_URL;

public class DetectReportDataModel {

    public MutableLiveData<CommonResponse> detectReportAPI(Context context,
                                                           String jsonStr) {
        DeviceAttributes attributes = new DeviceUtilities(context).getDeviceAttributes();
        final MutableLiveData<CommonResponse> commonRespones = new MutableLiveData<>();

        Gson gson = new GsonBuilder().create();

        DetectRequest detectRequest = gson.fromJson(jsonStr,
                                                    new TypeToken<DetectRequest>() {
                                                    }.getType());

        ApiServiceManager.getApiServiceManager(BASE_URL)
                         .DetectReportAPI(detectRequest)
                         .enqueue(new Callback<CommonResponse>() {

                             @Override
                             public void onResponse(Call<CommonResponse> call,
                                                    Response<CommonResponse> response) {
                                 CommonResponse res = response.body();
                                 if (res == null) {
                                     res = new CommonResponse();
                                 }
                                 commonRespones.setValue(res);
                             }

                             @Override
                             public void onFailure(Call<CommonResponse> call,
                                                   Throwable t) {
                                 CommonResponse FailResponse = new CommonResponse();
                                 FailResponse.setResult(Constants.RESULT_FAIL);
                                 commonRespones.setValue(FailResponse);

                             }
                         });
        return commonRespones;
    }
}
