package com.myapp.manager.response;

import com.google.gson.annotations.SerializedName;
import com.myapp.model.StockView;

import java.util.List;

public class StockViewResponse {

    @SerializedName("Result")
    private String result;

    @SerializedName("list")
    private List<StockView> list;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<StockView> getList() {
        return list;
    }

    public void setList(List<StockView> list) {
        this.list = list;
    }
}
