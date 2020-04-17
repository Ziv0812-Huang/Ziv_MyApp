package com.myapp.di;

import android.app.Application;
import android.content.Context;

import com.myapp.room.AppDatabase;
import com.myapp.room.DataRepository;
import com.myapp.room.DataSource;
import com.myapp.room.dao.TvProgramDao;

import javax.annotation.Signed;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private AppDatabase appDatabase;

    public RoomModule(Application application){
        appDatabase = Room.databaseBuilder(application,
                                           AppDatabase.class,
                                           AppDatabase.DATABASE_NAME)
                          /*.addCallback(new RoomDatabase.Callback() {
                              @Override
                              public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                  super.onCreate(db);
                              }
                          })*/
                          //for test...
                          .fallbackToDestructiveMigration() //fallback to destructive migration enabled
                          //.addMigrations(MigrationDB.MIGRATION_1_2)
                          .allowMainThreadQueries()
                          .build();
    }

    @Provides
    @Singleton
    public AppDatabase providesAppDatabase() {
        return appDatabase;
    }

    @Provides
    @Singleton
    public TvProgramDao providesTvProgramDao(AppDatabase appDatabase) {
        return appDatabase.getTvProgramDao();
    }

    @Provides
    @Singleton
    public DataRepository providesTDataRepository(DataSource dataSource) {
        return dataSource;
    }

}
