package com.myapp.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.myapp.R;
import com.myapp.activity.BaseActivity;
import com.myapp.adapter.BaseFragmentPagerAdapter;
import com.myapp.manager.response.TvProgramResponse;
import com.myapp.model.TvProgram;
import com.myapp.room.DataRepository;
import com.myapp.room.model.entity.TvProgramEntity;
import com.myapp.utils.BitmapUtils;
import com.myapp.utils.ProgressDialogUtils;
import com.myapp.viewmodel.TvProgramViewModel;
import com.myapp.viewmodel.factory.ModelFactory;
import com.myapp.widget.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import dagger.android.support.AndroidSupportInjection;

public class TvProgramFragment
        extends BaseFragment{

    private BaseActivity mActivity;

    private TabLayout tablayout;
    private ViewPager viewPager;
    private BaseFragmentPagerAdapter adapter;

    @Inject
    DataRepository dataRepository;

    private MutableLiveData<TvProgramResponse> tvProgramResponse = new MutableLiveData<>();
    public LiveData<List<TvProgramEntity>>  tvProgramList;

    public static BaseFragment newInstance() {
        TvProgramFragment fragment = new TvProgramFragment();
        return fragment;
    }

    @Override
    int getItemLayoutId() {
        return 0;
    }

    @Override
    int getFragmentLayoutId() {
        return R.layout.fragment_tv_program;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mActivity = (BaseActivity) getActivity();
        View view = inflater.inflate(getFragmentLayoutId(),
                                     container,
                                     false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {


        tablayout = view.findViewById(R.id.tl_tab);

        viewPager = view.findViewById(R.id.view_pager);

        initUI(view);


    }

    @Override
    public void onResume() {

        super.onResume();
        ProgressDialogUtils.showProgressDialog(mActivity);

        getTvProgramsJson();

        Observer<TvProgramResponse> tvProgramResponseObserver = response -> {
            String result = response.getResult();
            if (result.equals(Constants.RESULT_SUCCESS)) {
                List<TvProgram> programs = response.getDatas().getList();

                dataRepository.deleteAll();
                for (TvProgram program : programs) {
                    TvProgramEntity entity = new TvProgramEntity(program);
                    //entity.setPhoto(BitmapUtils.loadImageFromUrl(mActivity, entity.getThumb()));
                    dataRepository.updateProgram(entity);
                }
            }

            tvProgramList = dataRepository.findAll();

            tvProgramList.observe(this,
                                  tvProgramEntities -> {
                                      TvProgramListTabFragment fragment = (TvProgramListTabFragment) adapter.getItem(0);
                                      fragment.setDatas(tvProgramEntities);
                                      setDetailView(tvProgramEntities.get(0));
                                      ProgressDialogUtils.dismiss();
                                  });

        };

        tvProgramResponse.observe(mActivity,tvProgramResponseObserver);
    }

    private void initUI(View view) {
        String[] tabTitles = new String[]{getString(R.string.tab_tv_program_list),
                                          getString(R.string.tab_tv_program_detail)};
        Fragment[] contentFragments = new Fragment[]{new TvProgramListTabFragment(),
                                                     new TvProgramDetailTabFragment()};

        adapter = new BaseFragmentPagerAdapter(getChildFragmentManager(),
                                               contentFragments,
                                               tabTitles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        //viewPager.setHorizontalScrollEnabled(true);
        //viewPager.setUseChildHeight(false);

        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.setupWithViewPager(viewPager);

    }

    public void switchTab(TvProgramEntity entity) {
        int currentPage = viewPager.getCurrentItem();
        setDetailView(entity);
        if (currentPage == 0) {
            viewPager.setCurrentItem(1);
        } else {
            viewPager.setCurrentItem(0);
        }
    }

    private void setDetailView(TvProgramEntity entity) {
        TvProgramDetailTabFragment fragment = (TvProgramDetailTabFragment) adapter.getItem(1);
        fragment.resetData(entity);
    }


    private void getTvProgramsJson() {

        ModelFactory modelFactory = new ModelFactory();

        TvProgramViewModel viewModel = new ViewModelProvider(this,
                                                             modelFactory).get(TvProgramViewModel.class);

        tvProgramResponse = viewModel.tvProgramAPI(mActivity);
    }
    
}
