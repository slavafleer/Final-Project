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
public class PoiAdapter extends RecyclerView.Adapter<PoiHolder> implements
        PoiHolder.OnClickListener {

    private Context context;
    private ArrayList<Poi> pois;

    private OnClickListener onClickListener;

    public PoiAdapter(Context context, ArrayList<Poi> pois, PoiHolder.OnClickListener onClickListener) {
        this.context = context;
        this.pois = pois;
        this.onClickListener = (OnClickListener)onClickListener;
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
                view = layoutInflater.inflate(R.layout.item_poi_light, parent, false); // Not null, cause it make problem with weight.
                break;

            default:
                view = layoutInflater.inflate(R.layout.item_poi_dark, parent, false); // Not null, cause it make problem with weight.
                break;
        }
        return new PoiHolder(context, view);
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

    // PoiHolder interface
    @Override
    public void onDataClick(Poi poi) {
        onClickListener.onDataClick(poi);
    }

    @Override
    public void onDataLongClick(Poi poi) {
        onClickListener.onDataLongClick(poi);
    }

    @Override
    public void onPhotoClick(Poi poi) {
        onClickListener.onPhotoClick(poi);
    }

    public interface OnClickListener {

        void onDataClick(Poi poi);

        void onDataLongClick(Poi poi);

        void onPhotoClick(Poi poi);
    }
}
