package com.myapp.manager.response;

import com.google.gson.annotations.SerializedName;
import com.myapp.model.StockView;
import com.myapp.model.TvProgram;

import java.util.List;

public class TvProgramResponse {

    @SerializedName("Result")
    private String result;

    @SerializedName("data")
    private TvProgramDatas datas;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public TvProgramDatas getDatas() {
        return datas;
    }

    public void setDatas(TvProgramDatas datas) {
        this.datas = datas;
    }
}
