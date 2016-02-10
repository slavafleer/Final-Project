package com.slavafleer.nearpois.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.slavafleer.nearpois.Poi;
import com.slavafleer.nearpois.R;

/**
 * Poi Holder for Poi Recycler View
 */
public class PoiHolder extends RecyclerView.ViewHolder {

    private TextView textViewName;
    private TextView textViewAddress;
    private TextView textViewVicinity;
    private TextView textViewDistance;
    private ImageView imageViewPhoto;

    // Find views in each item.
    public PoiHolder(View itemView) {
        super(itemView);

        textViewName = (TextView) itemView.findViewById(R.id.textViewItemPoiName);
        textViewAddress = (TextView) itemView.findViewById(R.id.textViewItemPoiAddress);
        textViewVicinity = (TextView) itemView.findViewById(R.id.textViewItemPoiVicinity);
        textViewDistance = (TextView) itemView.findViewById(R.id.textViewItemPoiDistance);
        imageViewPhoto = (ImageView) itemView.findViewById(R.id.imageViewItemPoiPhoto);
    }

    // Bind Data Object to the Views.
    public void bindPoi(Poi poi) {

        String name = poi.getName();
        String address = poi.getAddress();
        String vicinity = poi.getVicinity();
        double distance = poi.getDistance();

        if(name != null) {
            textViewName.setText(name);
        }

        if(address != null) {
            textViewAddress.setText(address);
        }

        if(vicinity != null) {
            textViewVicinity.setText(vicinity);
        }

        // TODO: to add dependence for settings (km or miles) and show the relevant one.
        if(distance > 0) {
            textViewDistance.setText(String.format("%.0fkm", distance));
        }

        // TODO: load image, need to decide from where
        // TODO: probably to load from Data Base.
    }
}
