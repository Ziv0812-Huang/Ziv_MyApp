package com.myapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.myapp.R;
import com.myapp.room.model.entity.TvProgramEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

public class TvProgramAdapter
        extends BaseRecyclerViewAdapter<TvProgramEntity, TvProgramAdapter.ViewHolder> {

    private Context context;

    private int mItemViewLayoutId;

    public TvProgramAdapter(Context context,
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
                                  TvProgramEntity item) {
        ImageView imageView = holder.itemView.findViewById(R.id.iv_photo);

        Picasso.get().load(item.getThumb()).into(imageView);

        holder.bind(item);
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder{
        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TvProgramEntity item) {
            binding.setVariable(BR.tvProgramEntity, item);

            binding.executePendingBindings();
        }
    }
}
