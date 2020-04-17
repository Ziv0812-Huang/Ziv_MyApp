package com.myapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.myapp.di.AppModule;
import com.myapp.di.DaggerAppComponent;
import com.myapp.di.RoomModule;
import com.myapp.utils.SharedPreferencesManager;

import javax.inject.Inject;

import androidx.multidex.MultiDexApplication;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;


public class MyApp
        extends MultiDexApplication
        implements HasAndroidInjector {              

    private static final String TAG = "MyApp";

    @Inject
    public SharedPreferencesManager sharedPreferencesManager;

    private static MyApp mInstance;

    @Inject
    DispatchingAndroidInjector<Object> fragmentDispatchingAndroidInjector;

    public MyApp() {
        mInstance = this;
    }

    public static MyApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //applicationContext = this;
        DaggerAppComponent.builder()
                          .appModule(new AppModule(this))
                          .roomModule(new RoomModule(this))
                          .build()
                          .inject(this);
    }

    /*@Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }*/

    /*public SharedPreferencesManager getSharedPreferencesManager() {
        if (sharedPreferencesManager == null) {
            sharedPreferencesManager = new SharedPreferencesManager(mAppContext);
        }
        return sharedPreferencesManager;
    }*/

    /*public Context getAppContext() {
        return applicationContext;
    }*/

    @Override
    public AndroidInjector<Object> androidInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}