package com.myapp.room;

import android.content.Context;

import com.myapp.room.converter.DataConverter;
import com.myapp.room.dao.TvProgramDao;
import com.myapp.room.model.entity.TvProgramEntity;

import javax.annotation.Detainted;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {TvProgramEntity.class},
          version = AppDatabase.VERSION,
          exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class AppDatabase
        extends RoomDatabase {

    private static AppDatabase sInstance;

    public static final int VERSION = 1;

    @VisibleForTesting
    public final static String DATABASE_NAME = "myapp-db";

    public abstract TvProgramDao getTvProgramDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    /*@Deprecated
    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext());
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }*/

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     *
     * ☆allowMainThreadQueries() support database access on the main thread
     */
    /*@Deprecated
    private static AppDatabase buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext,
                                    AppDatabase.class,
                                    DATABASE_NAME)
                   .addCallback(new Callback() {
                       @Override
                       public void onCreate(@NonNull SupportSQLiteDatabase db) {
                           super.onCreate(db);
                       }
                   })
                   //for test...
                   .fallbackToDestructiveMigration() //fallback to destructive migration enabled
                   //.addMigrations(MigrationDB.MIGRATION_1_2)
                   //.allowMainThreadQueries()
                   .build();
    }*/

    public static void destoryInstance(){
        if (sInstance != null) {
            sInstance.close();
        }
        sInstance = null;
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
