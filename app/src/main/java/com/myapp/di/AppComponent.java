package com.myapp.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.myapp.MyApp;
import com.myapp.di.module.ActivityModule;
import com.myapp.di.module.FragmentModule;
import com.myapp.room.DataRepository;
import com.myapp.utils.SharedPreferencesManager;
import com.myapp.viewmodel.TvProgramDataModel;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
                      AppModule.class,
                      RoomModule.class,
                      ActivityModule.class,
                      FragmentModule.class})
public interface AppComponent extends AndroidInjector<MyApp> {

//    @Component.Builder
//    interface Builder {
//
//        @BindsInstance
//        Builder application(Application application);
//        AppComponent build();
//
//    }

    Application application();

    SharedPreferences sharedPreferences();

    SharedPreferencesManager sharedPreferencesManager();

    DataRepository dataRepository();

}