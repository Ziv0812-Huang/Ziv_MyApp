package com.myapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.myapp.MyApp;
import com.myapp.R;
import com.myapp.activity.BaseActivity;
import com.myapp.adapter.BaseRecyclerViewAdapter;
import com.myapp.adapter.SearchAdapter;
import com.myapp.adapter.StockBasicInfoAdapter;
import com.myapp.databinding.StockBasicInfoFragmentBinding;
import com.myapp.manager.response.CommonResponse;
import com.myapp.manager.response.StockBasicInfoResponse;
import com.myapp.model.SearchItem;
import com.myapp.model.StockBasicInfo;
import com.myapp.utils.ProgressDialogUtils;
import com.myapp.utils.SharedPreferencesManager;
import com.myapp.viewmodel.StockTWBasicInfoViewModel;
import com.myapp.viewmodel.factory.ModelFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.AndroidSupportInjection;

public class StockBasicInfoFragment extends BaseFragment{

    private BaseActivity mActivity;

    private StockBasicInfoFragmentBinding binding;

    private MutableLiveData<StockBasicInfoResponse> stockBasicInfoResponse = new MutableLiveData<>();
    private List<StockBasicInfo> stockBasicInfos = new ArrayList<>();

    private StockBasicInfoAdapter adapter;

    public static BaseFragment newInstance() {
        StockBasicInfoFragment fragment = new StockBasicInfoFragment();
        return fragment;
    }

    @Override
    int getItemLayoutId() {
        return R.layout.adapter_store_stock_cardview;
    }

    @Override
    int getFragmentLayoutId() {
        return R.layout.fragment_stock_basic_info;
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
        //preferencesManager = MyApp.getInstance().getSharedPreferencesManager();
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
        getStockInfoJson();

        adapter = new StockBasicInfoAdapter(mActivity,
                                            getItemLayoutId());


        Observer<StockBasicInfoResponse> storeStockResponseObserver = response -> {

            List<StockBasicInfo> datas = getStoreStock(response.getList());
            adapter.addDatas(datas);

            adapter.setDeleteClickListener(new BaseRecyclerViewAdapter.BaseOnItemClickListener(){
                @Override
                public void onItemClick(View v,
                                        int position) {
                    StockBasicInfo item = adapter.getItem(position);
                    List<StockBasicInfo> datas = adapter.getDatas();
                    datas.remove(item);
                    saveStoreStock(datas);
                    adapter.notifyDataSetChanged();
                }
            });

            binding.storeList.setLayoutManager(new LinearLayoutManager(mActivity));
            binding.storeList.setAdapter(adapter);

            ArrayAdapter<String> listAdapter = new SearchAdapter(mActivity,
                                                                 buildData(response.getList()));
            binding.searchInput.setAdapter(listAdapter);
            binding.searchInput.setThreshold(1);
            binding.searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        getStockData(v.getText().toString(), response.getList());
                        v.setText("");
                        return true;
                    }
                    return false;
                }
            });
            ProgressDialogUtils.dismiss();
        };

        stockBasicInfoResponse.observe(mActivity,
                                       storeStockResponseObserver);

        super.onViewCreated(view,
                            savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ProgressDialogUtils.showProgressDialog(mActivity);
    }

    private void getStockInfoJson() {

        ModelFactory modelFactory = new ModelFactory();

        StockTWBasicInfoViewModel viewModel = new ViewModelProvider(this,
                                                                    modelFactory).get(StockTWBasicInfoViewModel.class);

        stockBasicInfoResponse = viewModel.stockTWBasicInfo(mActivity);
    }

    private SearchItem[] buildData(List<StockBasicInfo> list) {
        int length = list.size();
        SearchItem[] array = new SearchItem[length];
        int i = 0;
        for (StockBasicInfo info : list) {
            array[i] = new SearchItem(info.getStockNo() , info.getStockName());
            i += 1;
        }
        return array;
    }

    private void getStockData(String searchTxt, List<StockBasicInfo> list) {
        int endIndex = searchTxt.indexOf("/");
        int limitMax = 5;
        if (endIndex<0) {
            return;
        }
        String code = searchTxt.substring(0, endIndex).trim();

        List<StockBasicInfo> datas = adapter.getDatas();

        if (datas.size() >= limitMax) {
            Toast.makeText(mActivity,
                           "已超過上限5筆!!!",
                           Toast.LENGTH_SHORT)
                 .show();
            return;
        }

        for (StockBasicInfo info : list) {
            if (info.getStockNo().equals(code)) {
                if (datas.indexOf(info) > -1) {
                    Toast.makeText(mActivity,
                                   "已存在自選股!!!",
                                   Toast.LENGTH_SHORT)
                         .show();
                    return;
                }
                datas.add(info);
                break;
            }
        }

        Toast.makeText(mActivity,
                       searchTxt,
                       Toast.LENGTH_SHORT)
             .show();

        adapter.notifyDataSetChanged();
        saveStoreStock(datas);
    }

    private void saveStoreStock(List<StockBasicInfo> list) {
        Set<String> stockSet = new HashSet<>();
        for (StockBasicInfo info : list) {
            stockSet.add(info.getStockNo());
        }

        SharedPreferencesManager.StockBasic.saveStoreStockSymbol(stockSet);
    }

    private List<StockBasicInfo> getStoreStock(List<StockBasicInfo> list) {
        List<StockBasicInfo> datas = new ArrayList<>();
        Set<String> stockSet = SharedPreferencesManager.StockBasic.getStoreStockSymbol();
        for (String stockNo: stockSet) {
            for (StockBasicInfo info : list) {
                if (stockNo.equals(info.getStockNo())) {
                    datas.add(info);
                    continue;
                }
            }
        }

        return datas;
    }
    
}
