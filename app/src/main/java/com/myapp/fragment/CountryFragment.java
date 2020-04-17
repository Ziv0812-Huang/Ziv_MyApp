package com.myapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myapp.R;
import com.myapp.activity.BaseActivity;
import com.myapp.adapter.BaseRecyclerViewAdapter;
import com.myapp.adapter.CountryListAdapter;
import com.myapp.databinding.CountryFragmentBinding;
import com.myapp.model.Country;
import com.myapp.model.CountryInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CountryFragment extends BaseFragment {

    private BaseActivity mActivity;
    private CountryListAdapter adapter;
    private CountryFragmentBinding binding;
    private ArrayList<Country> list;

    public static BaseFragment newInstance() {
        CountryFragment fragment = new CountryFragment();
        return fragment;
    }

    @Override
    int getItemLayoutId() {
        return R.layout.adapter_country_list;
    }

    @Override
    int getFragmentLayoutId() {
        return R.layout.fragment_country;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mActivity = (BaseActivity) getActivity();
        binding = DataBindingUtil.inflate(inflater,
                                          getFragmentLayoutId(),
                                          container,
                                          false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

        list = mActivity.getCountryData();
        adapter = new CountryListAdapter(mActivity, getItemLayoutId());
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.BaseOnItemClickListener(){
            @Override
            public void onItemClick(View v,
                                    int position) {
                Country data = adapter.getItem(position);
                Toast.makeText(mActivity , data.getCountry(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter.addDatas(getDatas(list));

        binding.rvList.setLayoutManager(new LinearLayoutManager(mActivity));
        binding.rvList.setAdapter(adapter);


    }


    private List<CountryInfo> getDatas(List<Country> list) {
        List<CountryInfo> countryInfoList = new ArrayList<>();
        for (Country data : list) {
            CountryInfo countryInfo = new CountryInfo(data);
            switch (data.getCountry()) {
                case "Taiwan":
                    countryInfo.setImageResId(R.drawable.taiwan);
                    break;
                case "Singapore":
                    countryInfo.setImageResId(R.drawable.singapore);
                    break;
                case "USA":
                    countryInfo.setImageResId(R.drawable.usa);
                    break;
                case "France":
                    countryInfo.setImageResId(R.drawable.france);
                    break;
                case "Philippines":
                    countryInfo.setImageResId(R.drawable.philippines);
                    break;
                case "Australia":
                    countryInfo.setImageResId(R.drawable.australia);
                    break;
                case "Vietnam":
                    countryInfo.setImageResId(R.drawable.vietnam);
                    break;
                case "Thailand":
                    countryInfo.setImageResId(R.drawable.thailand);
                    break;
                case "Canada":
                    countryInfo.setImageResId(R.drawable.canada);
                    break;
                case "Russia":
                    countryInfo.setImageResId(R.drawable.russia);
                    break;
                case "England":
                    countryInfo.setImageResId(R.drawable.england);
                    break;
                case "Brazil":
                    countryInfo.setImageResId(R.drawable.brazil);
                    break;
                case "Korea":
                    countryInfo.setImageResId(R.drawable.korea);
                    break;
                case "Germany":
                    countryInfo.setImageResId(R.drawable.germany);
                    break;
                case "Spain":
                    countryInfo.setImageResId(R.drawable.spain);
                    break;
            }
            countryInfoList.add(countryInfo);
        }
        return countryInfoList;
    }
}
