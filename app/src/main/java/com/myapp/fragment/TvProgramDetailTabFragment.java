package com.myapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapp.R;
import com.myapp.databinding.TvProgramDetailFragmentBinding;
import com.myapp.room.model.entity.TvProgramEntity;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class TvProgramDetailTabFragment
        extends BaseFragment{

    private TvProgramDetailFragmentBinding binding;

    @Override
    int getItemLayoutId() {
        return 0;
    }

    @Override
    int getFragmentLayoutId() {
        return R.layout.fragment_tv_program_detail_tab;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                                          getFragmentLayoutId(),
                                          container,
                                          false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view,
                            savedInstanceState);
    }

    public void resetData(TvProgramEntity entity) {
        Picasso.get().load(entity.getThumb()).into(binding.ivPhoto);
        binding.setProgramEntity(entity);
        binding.executePendingBindings();
    }

}
