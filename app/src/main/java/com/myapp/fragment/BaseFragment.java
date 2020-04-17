package com.myapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.myapp.utils.UiUtils;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseFragment extends Fragment {

    abstract int getItemLayoutId();

    abstract int getFragmentLayoutId();

    public <E extends View> E getView(View view, int id) {
        return UiUtils.getView(view, id);
    }

    public void showWeb(String url) {
        Activity activity = getActivity();
        if (null == activity) return;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    protected int[] getChildFragmentIds() {
        return null;
    }

    @Override
    public void onDestroyView() {
        // To prevent inflate duplicate fragment in XML, especially in Android4.4.
        UiUtils.removeFragmentsById(this, getChildFragmentIds());

        super.onDestroyView();
    }

    protected GridLayoutManager getCustomGridLayoutManager(int maxColumns,
                                                           int size) {
        return getCustomGridLayoutManager(maxColumns, size, -1, false);

    }

    protected GridLayoutManager getCustomGridLayoutManager(int maxColumns,
                                                           int size,
                                                           @RecyclerView.Orientation int orientation,
                                                           boolean reverseLayout) {
        return getCustomGridLayoutManager(maxColumns, size, orientation, reverseLayout,false);
    }

    protected GridLayoutManager getCustomGridLayoutManager(int maxColumns,
                                                           int size,
                                                           @RecyclerView.Orientation int orientation,
                                                           boolean reverseLayout,
                                                           boolean geometry) {
        int unit = 2;
        int msize = size;
        int totalSpan = maxColumns * unit;
        GridLayoutManager manager;
        if (orientation == -1) {
            manager = new GridLayoutManager(getActivity(),
                                            totalSpan);
        }
        else {
            manager = new GridLayoutManager(getActivity(),
                                            totalSpan,
                                            orientation,
                                            reverseLayout);
        }

        GridLayoutManager.SpanSizeLookup onSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int d = msize % maxColumns;
                int mark_point = msize - d;
                int span = unit;
                if (geometry) {
                    span = (position >= mark_point) ? (totalSpan / d) : unit;
                }
                return span;
            }
        };
        manager.setSpanSizeLookup(onSpanSizeLookup);
        return manager;
    }
}
