package com.myapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myapp.R;
import com.myapp.adapter.BaseRecyclerViewAdapter;
import com.myapp.adapter.TvProgramAdapter;
import com.myapp.databinding.TvProgramListFragmentBinding;
import com.myapp.room.model.entity.TvProgramEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class TvProgramListTabFragment
        extends BaseFragment {

    private Activity mActivity;
    private TvProgramAdapter adapter;
    private TvProgramListFragmentBinding binding;

    @Override
    int getItemLayoutId() {
        return R.layout.adapter_tv_program_list_tab;
    }

    @Override
    int getFragmentLayoutId() {
        return R.layout.fragment_tv_program_list_tab;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mActivity = this.getActivity();
        binding = DataBindingUtil.inflate(inflater,
                                          getFragmentLayoutId(),
                                          container,
                                          false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

        adapter = new TvProgramAdapter(mActivity, getItemLayoutId());

        adapter.addDatas(new ArrayList<>());

        binding.rvList.setLayoutManager(new LinearLayoutManager(mActivity));
        binding.rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.BaseOnItemClickListener(){
            @Override
            public void onItemClick(View v,
                                    int position) {
                TvProgramEntity entity = adapter.getItem(position);
                Toast.makeText(mActivity , entity.getName(), Toast.LENGTH_SHORT).show();
                //TvProgramDetailTabFragment fragment = (TvProgramDetailTabFragment)
                //getParentFragmentManager().getFragments();
                //fragment.resetData(entity);
                lookTvProgram(entity);
            }
        });
        super.onViewCreated(view,
                            savedInstanceState);
    }

    public void setDatas(List<TvProgramEntity> datas) {
        adapter.resetDatas(datas);
        adapter.notifyDataSetChanged();
    }

    private void lookTvProgram(TvProgramEntity entity) {
        ((TvProgramFragment)getParentFragment()).switchTab(entity);
    }


}
