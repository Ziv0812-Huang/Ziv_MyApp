package com.myapp.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    public SharedPreferences providePreferences(Application application) {
        String pref_name = "ziv_myapp";
        return application.getSharedPreferences(pref_name,
                                                Context.MODE_PRIVATE);
    }
}
