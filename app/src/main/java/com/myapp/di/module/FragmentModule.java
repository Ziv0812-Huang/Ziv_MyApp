package com.myapp.di.module;


import com.myapp.fragment.MobileDetectionFragment;
import com.myapp.fragment.StockBasicInfoFragment;
import com.myapp.fragment.StockViewFragment;
import com.myapp.fragment.TvProgramFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract MobileDetectionFragment contributeMobileDetectionFragment();

    @ContributesAndroidInjector
    abstract StockBasicInfoFragment contributeStockBasicInfoFragment();

    @ContributesAndroidInjector
    abstract StockViewFragment contributeStockViewFragment();

    @ContributesAndroidInjector
    abstract TvProgramFragment tvProgramListFragment();
}
