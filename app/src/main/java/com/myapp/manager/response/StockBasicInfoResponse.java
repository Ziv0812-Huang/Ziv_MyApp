package com.myapp.manager.response;

import com.google.gson.annotations.SerializedName;
import com.myapp.model.StockBasicInfo;

import java.util.List;

public class StockBasicInfoResponse {

    @SerializedName("Result")
    private String result;

    @SerializedName("list")
    private List<StockBasicInfo> list;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<StockBasicInfo> getList() {
        return list;
    }

    public void setList(List<StockBasicInfo> list) {
        this.list = list;
    }
}
