package com.myapp.manager.response;

import com.google.gson.annotations.SerializedName;
import com.myapp.model.TvProgram;

import java.util.List;

public class TvProgramDatas {

    @SerializedName("data")
    private List<TvProgram> list;

    public List<TvProgram> getList() {
        return list;
    }

    public void setList(List<TvProgram> list) {
        this.list = list;
    }
}
