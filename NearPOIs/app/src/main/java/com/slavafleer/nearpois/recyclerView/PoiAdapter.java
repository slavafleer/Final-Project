package com.slavafleer.nearpois.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slavafleer.nearpois.Poi;
import com.slavafleer.nearpois.R;

import java.util.ArrayList;

/**
 * Poi Adapter for Poi Recycler View
 */
public class PoiAdapter extends RecyclerView.Adapter<PoiHolder> {

    private Context context;
    private ArrayList<Poi> pois;

    public PoiAdapter(Context context, ArrayList<Poi> pois) {
        this.context = context;
        this.pois = pois;
    }

    // Will be invoked only for the first shown items!
    // Creates the first x layout items.
    @Override
    public PoiHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;
        switch (viewType) {
            case 1:
                view = layoutInflater.inflate(R.layout.item_poi, parent, false); // Not null, cause it make problem with weight.
                break;

            default:
                view = layoutInflater.inflate(R.layout.item_poi_gray, parent, false); // Not null, cause it make problem with weight.
                break;
        }
        return new PoiHolder(view);
    }

    // Will be invoked for each item presented.
    // Here we will set the data to the layout:
    @Override
    public void onBindViewHolder(PoiHolder holder, int position) {

        Poi poi = pois.get(position);

        holder.bindPoi(poi);
    }

    @Override
    public int getItemCount() {
        return pois.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }
}
