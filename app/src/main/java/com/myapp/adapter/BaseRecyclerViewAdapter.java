package com.myapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerViewAdapter<T, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

    protected BaseOnItemClickListener mOnItemClickListener;
    private ArrayList<T> mDatas;
    private final WeakReference<Context> mContext;
    protected final LayoutInflater mInflater;

    private int itemWidth;
    private int itemHeight;
    private View mEmptyView;


    protected abstract int getViewLayoutId();
    protected abstract V getViewHolder(View view);
    protected abstract void bindViewHolder(Context context, V holder, T item);


    public BaseRecyclerViewAdapter(Context context) {
        mDatas = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
        mContext = new WeakReference<>(context);
    }

    public void resetDatas(List<T> datas) {
        if (null != datas) {
            this.mDatas.clear();
            addDatas(datas);
        }
    }

    public void addDatas(List<T> datas) {
        if (null != datas) {
            this.mDatas.addAll(datas);
        }

        updateUi();
    }

    public void setItemSize(int width, int height) {
        itemWidth = width;
        itemHeight = height;
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    private void updateUi() {
        if (null != mEmptyView) {
            if (getItemCount() == 0) {
                mEmptyView.setVisibility(View.VISIBLE);

            } else {
                mEmptyView.setVisibility(View.GONE);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = mInflater.inflate(getViewLayoutId(), parent, false);
        if (null != mOnItemClickListener) view.setOnClickListener(mOnItemClickListener);

        if (itemWidth > 0 && itemHeight > 0) {
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(itemWidth, itemHeight);
            view.setLayoutParams(params);
        }

        return getViewHolder(view);
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        Context context = mContext.get();
        if (context == null) return;

        holder.itemView.setTag(position);

        T item = getItem(position);
        bindViewHolder(context, holder, item);

        holder.itemView.setEnabled(isEnable(item));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public T getItem(int position) {
        if (position < 0 || mDatas.size() <= position) {
            return null;
        }

        return mDatas.get(position);
    }

    public List<T> getDatas() {
        return mDatas;
    }

    protected boolean isEnable(T item) {
        return true;
    }

    public void setOnItemClickListener(BaseOnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public static class BaseOnItemClickListener
            implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Object positionObj = v.getTag();
            int position = (int) positionObj;

            onItemClick(v,
                        position);
        }

        public void onItemClick(View v,
                                int position) {

        }
    }

}
