package com.myapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.R;
import com.myapp.activity.BaseActivity;
import com.myapp.adapter.BaseRecyclerViewAdapter;
import com.myapp.adapter.StockViewAdapter;
import com.myapp.databinding.StockViewFragmentBinding;
import com.myapp.manager.response.StockBasicInfoResponse;
import com.myapp.manager.response.StockViewResponse;
import com.myapp.model.StockView;
import com.myapp.utils.ProgressDialogUtils;
import com.myapp.utils.SharedPreferencesManager;
import com.myapp.viewmodel.StockTWViewModel;
import com.myapp.viewmodel.factory.ModelFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.AndroidSupportInjection;

public class StockViewFragment
        extends BaseFragment{

    private BaseActivity mActivity;

    private StockViewFragmentBinding binding;

    private MutableLiveData<StockViewResponse> stockViewResponse = new MutableLiveData<>();
    private List<StockView> stockViews = new ArrayList<>();

    private StockViewAdapter adapter;

    public static BaseFragment newInstance() {
        StockViewFragment fragment = new StockViewFragment();
        return fragment;
    }

    @Override
    int getItemLayoutId() {
        return R.layout.adapter_stock_view_cardview;
    }

    @Override
    int getFragmentLayoutId() {
        return R.layout.fragment_stock_view;
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
        binding = DataBindingUtil.inflate(inflater,
                                          getFragmentLayoutId(),
                                          container,
                                          false);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        getStockViewJson();

        adapter = new StockViewAdapter(mActivity,
                                       getItemLayoutId());


        Observer<StockViewResponse> stockViewResponseObserver = response -> {

            List<StockView> datas = getStoreStock(response.getList());
            adapter.addDatas(datas);

            binding.storeList.setLayoutManager(new LinearLayoutManager(mActivity));
            binding.storeList.setAdapter(adapter);

            ProgressDialogUtils.dismiss();
        };

        stockViewResponse.observe(mActivity,
                                  stockViewResponseObserver);

        super.onViewCreated(view,
                            savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ProgressDialogUtils.showProgressDialog(mActivity);
    }

    private void getStockViewJson() {

        ModelFactory modelFactory = new ModelFactory();

        StockTWViewModel viewModel = new ViewModelProvider(this,
                                                           modelFactory).get(StockTWViewModel.class);

        stockViewResponse = viewModel.stockTW(mActivity);
    }

    private List<StockView> getStoreStock(List<StockView> list) {
        List<StockView> datas = new ArrayList<>();
        Set<String> stockSet = SharedPreferencesManager.StockBasic.getStoreStockSymbol();
        for (String stockNo: stockSet) {
            for (StockView item : list) {
                if (stockNo.equals(item.getStockNo())) {
                    datas.add(item);
                    continue;
                }
            }
        }

        return datas;
    }
    
}
