package com.myapp.model;

public class CountryInfo
        extends Country {

    public CountryInfo(Country country) {
        this.key = country.key;
        this.country = country.country;
        this.isFavor = country.isFavor;
    }

    private int imageResId;

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
