package com.slavafleer.recyclerlistandgrid;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

// Holder for item on RecyclerView
public class PointOfInterestHolder extends RecyclerView.ViewHolder {

    private TextView textViewId;
    private TextView textViewName;
    private TextView textViewAddress;
    private TextView textViewDistance;

    public PointOfInterestHolder(View itemView) {
        super(itemView);

        textViewId = (TextView) itemView.findViewById(R.id.textViewId);
        textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        textViewAddress = (TextView) itemView.findViewById(R.id.textViewAddress);
        textViewDistance = (TextView) itemView.findViewById(R.id.textViewDistance);
    }

    // Binds data to views on each item in list
    public void bindPoinOfInterest(PointOfInterest pointOfInterest) {

        textViewId.setText(pointOfInterest.getId() + "");
        textViewName.setText(pointOfInterest.getName());
        textViewAddress.setText(pointOfInterest.getAddress());
        textViewDistance.setText(pointOfInterest.getDistance() + "");
    }
}
