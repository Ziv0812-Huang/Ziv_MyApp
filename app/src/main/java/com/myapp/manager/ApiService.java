package com.myapp.manager;

import com.myapp.manager.request.DetectRequest;
import com.myapp.manager.response.CommonResponse;
import com.myapp.manager.response.TvProgramDatas;
import com.myapp.model.StockBasicInfo;
import com.myapp.model.StockView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {


    @POST("/event/api/mobile_detect_receive")
    Call<CommonResponse> DetectReportAPI(@Body DetectRequest request);

    @GET("https://quality.data.gov.tw/dq_download_json.php?nid=18420&md5_url=cfee038a8a9009bf31df7b23328dcc3f")
    Call<List<StockBasicInfo>> StockTWBasicInfoAPI();

    @GET("https://quality.data.gov.tw/dq_download_json.php?nid=11549&md5_url=bb878d47ffbe7b83bfc1b41d0b24946e")
    Call<List<StockView>> StockTWAPI();

    @GET("https://static.linetv.tw/interview/dramas-sample.json")
    Call<TvProgramDatas> TvProgramListAPI();
}
