package com.myapp.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.myapp.model.SearchItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class SearchAdapter
        extends ArrayAdapter<String> {

    List<String> searchList;
    SearchItem[] searchItems;

    
    public SearchAdapter(@NonNull Context context,
                         @NonNull SearchItem[] objects) {
        super(context, android.R.layout.simple_dropdown_item_1line);
        searchItems = objects;
        searchList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return searchList.size();
    }

    @Override
    public String getItem(int index) {
        return searchList.get(index);
    }

    @Override
    public Filter getFilter() {
        Filter searchFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    searchList.clear();
                    for (int i=0; i<searchItems.length; i++) {
                        SearchItem item = searchItems[i];
                        if (item.code.startsWith(constraint.toString())) {
                            searchList.add(item.code + " / " + item.name );
                        }
                    }

                    filterResults.values = searchList;
                    filterResults.count = searchList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence contraint,
                                          FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return searchFilter;
    }
}
