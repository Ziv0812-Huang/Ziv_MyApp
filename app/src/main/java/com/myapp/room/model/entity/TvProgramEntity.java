package com.myapp.room.model.entity;


import com.myapp.model.TvProgram;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "TvProgram")
public class TvProgramEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "dramaId")
    private Integer dramaId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "totalViews")
    private Integer totalViews;

    @ColumnInfo(name = "createdAt")
    private String createdAt;

    @ColumnInfo(name = "thumb")
    private String thumb;

    @ColumnInfo(name = "photo" ,typeAffinity = ColumnInfo.BLOB)
    private byte[] photo;

    @ColumnInfo(name = "rating")
    private Double rating;

    public TvProgramEntity() {

    }

    @Ignore
    public TvProgramEntity(@NonNull TvProgram tvProgram) {
        this.dramaId = tvProgram.getDramaId();
        this.name = tvProgram.getName();
        this.totalViews = tvProgram.getTotalViews();
        this.createdAt = tvProgram.getCreatedAt();
        this.thumb = tvProgram.getThumb();
        this.rating = tvProgram.getRating();
    }


    @NonNull
    public Integer getDramaId() {
        return dramaId;
    }

    public void setDramaId(@NonNull Integer dramaId) {
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Ignore
    @Override
    public String toString() {
        return "TvProgramEntity{" +
               "dramaId=" + dramaId +
               ", name='" + name + '\'' +
               ", totalViews=" + totalViews +
               ", createdAt='" + createdAt + '\'' +
               ", thumb='" + thumb + '\'' +
               ", rating=" + rating +
               '}';
    }
}
