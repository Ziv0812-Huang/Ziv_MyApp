package com.myapp.room.dao;

import com.myapp.room.dao.common.BaseDao;
import com.myapp.room.model.entity.TvProgramEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class TvProgramDao
        implements BaseDao<TvProgramEntity> {

    @Query("SELECT * FROM TvProgram")
    public abstract LiveData<List<TvProgramEntity>>  queryAll();

    @Query("DELETE FROM TvProgram")
    public abstract void deleteAll();

    @Transaction
    public void updateProgram(TvProgramEntity entity) {
        delete(entity);
        insert(entity);
    }
}
