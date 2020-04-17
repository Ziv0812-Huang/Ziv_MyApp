package com.myapp.room;

import com.myapp.room.dao.TvProgramDao;
import com.myapp.room.model.entity.TvProgramEntity;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;

public class DataSource
        implements DataRepository {

    private TvProgramDao tvProgramDao;

    @Inject
    public DataSource(TvProgramDao tvProgramDao) {
        this.tvProgramDao = tvProgramDao;
    }

    @Override
    public LiveData<List<TvProgramEntity>> findAll() {
        return tvProgramDao.queryAll();
    }

    @Override
    public void update(TvProgramEntity entity) {
        tvProgramDao.update(entity);
    }

    @Override
    public void updateProgram(TvProgramEntity entity) {
        tvProgramDao.updateProgram(entity);
    }

    @Override
    public void deleteAll() {
        tvProgramDao.deleteAll();
    }

    @Override
    public int delete(TvProgramEntity entity) {
        return tvProgramDao.delete(entity);
    }
}
