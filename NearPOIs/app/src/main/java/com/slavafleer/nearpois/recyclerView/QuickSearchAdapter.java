package com.slavafleer.nearpois.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slavafleer.nearpois.QuickSearch;
import com.slavafleer.nearpois.R;

import java.util.ArrayList;

/**
 * Adapter for Quick Search Recycler.
 */
public class QuickSearchAdapter extends RecyclerView.Adapter<QuickSearchHolder> {

    private Context context;
    private ArrayList<QuickSearch> quickSearches;

    public QuickSearchAdapter(Context context, ArrayList<QuickSearch> quickSearches) {
        this.context = context;
        this.quickSearches = quickSearches;
    }

    // Will be invoked only for the first shown items!
    // Creates the first x layout items.
    @Override
    public QuickSearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.item_quick_search, parent, false);

        return new QuickSearchHolder(view);
    }

    // Will be invoked for each item presented.
    // Here we will set the data to the layout:
    @Override
    public void onBindViewHolder(QuickSearchHolder holder, int position) {

        QuickSearch quickSearch = quickSearches.get(position % 19);

        holder.bindQuickSearch(quickSearch);
    }

    @Override
    public int getItemCount() {
//        return quickSearches.size();
        return quickSearches.size() * 100;
    }
}
