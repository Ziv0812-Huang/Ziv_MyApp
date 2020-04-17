package com.myapp.model;

import com.google.gson.annotations.SerializedName;

public class Country {

    protected String key;

    @SerializedName("Country")
    protected String country;

    protected boolean isFavor;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isFavor() {
        return isFavor;
    }

    public void setFavor(boolean favor) {
        isFavor = favor;
    }
}
