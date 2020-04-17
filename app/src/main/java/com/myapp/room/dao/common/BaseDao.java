package com.myapp.room.dao.common;


import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

public interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T obj);

    @Delete
    int delete(T Obj);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(T Obj);

}
