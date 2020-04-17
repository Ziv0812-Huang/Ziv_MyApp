package com.myapp.room;

import com.myapp.room.model.entity.TvProgramEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Repository handling the work with products and comments.
 */
public interface DataRepository {

    LiveData<List<TvProgramEntity>>  findAll();

    void update(TvProgramEntity entity);

    void updateProgram(TvProgramEntity entity);

    void deleteAll();

    int delete(TvProgramEntity entity);

}
