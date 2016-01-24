package com.slavafleer.recyclerlistandgrid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PointOfInterestAdapter extends RecyclerView.Adapter<PointOfInterestHolder> {

    private Context context;
    private ArrayList<PointOfInterest> pointOfInterests;

    public PointOfInterestAdapter(Context context, ArrayList<PointOfInterest> pointOfInterests) {
        this.context = context;
        this.pointOfInterests = pointOfInterests;
    }

    // Will be invoked only for the first shown items!
    // Creates the first x layout items.
    @Override
    public PointOfInterestHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.item_point_of_interest, null);

        return new PointOfInterestHolder(view);
    }

    // Will be invoked for each item presented.
    // Here we will set the data to the layout:
    @Override
    public void onBindViewHolder(PointOfInterestHolder holder, int position) {

        PointOfInterest pointOfInterest = pointOfInterests.get(position);

        holder.bindPoinOfInterest(pointOfInterest);
    }

    @Override
    public int getItemCount() {
        return pointOfInterests.size();
    }
}
