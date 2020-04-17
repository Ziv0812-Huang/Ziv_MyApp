package com.myapp.manager.response;

import com.google.gson.annotations.SerializedName;

public class CommonResponse {

    @SerializedName("Result")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
