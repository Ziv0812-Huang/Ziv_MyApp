package com.myapp.adapter;

import android.content.Context;
import android.view.View;

import com.myapp.model.CountryInfo;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

public class CountryListAdapter
        extends BaseRecyclerViewAdapter<CountryInfo, CountryListAdapter.ViewHolder> {

    private Context context;

    private int mItemViewLayoutId;

    public CountryListAdapter(Context context,
                              int itemViewLayoutId) {
        super(context);
        //this.list = list;
        this.context = context;
        this.mItemViewLayoutId = itemViewLayoutId;
    }

    @Override
    protected int getViewLayoutId() {
        return mItemViewLayoutId;
    }

    @Override
    protected ViewHolder getViewHolder(View view) {
        ViewDataBinding binding = DataBindingUtil.bind(view);
        return new ViewHolder(binding);
    }

    @Override
    protected void bindViewHolder(Context context,
                                  ViewHolder holder,
                                  CountryInfo item) {
        holder.bind(item);
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder{
        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CountryInfo item) {
            binding.setVariable(BR.countryItem, item);
            binding.executePendingBindings();
        }
    }
}
