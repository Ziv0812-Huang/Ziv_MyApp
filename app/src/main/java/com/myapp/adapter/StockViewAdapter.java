package com.myapp.adapter;

import android.content.Context;
import android.view.View;

import com.myapp.R;
import com.myapp.model.StockView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

public class StockViewAdapter
        extends BaseRecyclerViewAdapter<StockView, StockViewAdapter.ViewHolder> {

    private Context context;

    private int mItemViewLayoutId;

    private int bgColorRes = R.color.gray_757575;

    public StockViewAdapter(Context context,
                            int itemViewLayoutId) {
        super(context);
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
                                  StockView item) {
        holder.bind(item);
    }

    public void setBgColorRes(int bgColorRes) {
        this.bgColorRes = bgColorRes;
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder{
        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(StockView item) {
            if (item.getChange().startsWith("+")) {
                bgColorRes = R.color.red_e60e1a;
            } else if (item.getChange().startsWith("-")) {
                bgColorRes = R.color.green_0e6462;
            } else {
                bgColorRes = R.color.gray_757575;
            }
            binding.setVariable(BR.stockViewItem, item);
            binding.setVariable(BR.bgColorRes, bgColorRes);
            binding.executePendingBindings();


        }
    }
}
