package com.myapp.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvProgram implements Serializable {

    @SerializedName("drama_id")
    @Expose
    private Integer dramaId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("total_views")
    @Expose
    private Integer totalViews;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("thumb")
    @Expose
    private String thumb;

    @SerializedName("rating")
    @Expose
    private Double rating;

    private final static long serialVersionUID = 8892696246850979327L;

    /**
     * No args constructor for use in serialization
     *
     */
    public TvProgram() {
    }

    /**
     *
     * @param createdAt
     * @param dramaId
     * @param thumb
     * @param name
     * @param rating
     * @param totalViews
     */
    public TvProgram(Integer dramaId, String name, Integer totalViews, String createdAt, String thumb, Double rating) {
        super();
        this.dramaId = dramaId;
        this.name = name;
        this.totalViews = totalViews;
        this.createdAt = createdAt;
        this.thumb = thumb;
        this.rating = rating;
    }

    public Integer getDramaId() {
        return dramaId;
    }

    public void setDramaId(Integer dramaId) {
        this.dramaId = dramaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(Integer totalViews) {
        this.totalViews = totalViews;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

}