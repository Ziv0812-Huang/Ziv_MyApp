package com.myapp.adapter;

import android.content.Context;
import android.view.View;

import com.myapp.model.StockBasicInfo;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

public class StockBasicInfoAdapter
        extends BaseRecyclerViewAdapter<StockBasicInfo, StockBasicInfoAdapter.ViewHolder> {

    private Context context;

    private int mItemViewLayoutId;

    private View.OnClickListener deleteClickListener;

    public StockBasicInfoAdapter(Context context,
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
                                  StockBasicInfo item) {
        holder.bind(item);
    }

    public void setDeleteClickListener(View.OnClickListener deleteClickListener) {
        this.deleteClickListener = deleteClickListener;
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder{
        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(StockBasicInfo item) {
            binding.setVariable(BR.stockItem, item);
            binding.setVariable(BR.deleteListener,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        deleteClickListener.onClick(itemView);
                                    }
                                });
            binding.executePendingBindings();


        }
    }
}
