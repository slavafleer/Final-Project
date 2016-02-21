package com.slavafleer.nearpois.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slavafleer.nearpois.Constants;
import com.slavafleer.nearpois.Poi;
import com.slavafleer.nearpois.R;
import com.slavafleer.nearpois.helper.RoundedTransformation;
import com.slavafleer.nearpois.helper.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Poi Holder for Poi Recycler View
 */
public class PoiHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "PoiHolder";

    private Context context;

    private ClickListener clickListener;

    private LinearLayout linearLayoutPoiData;
    private TextView textViewName;
    private TextView textViewVicinity;
    private TextView textViewDistance;
    private ImageView imageViewPhoto;
    private ImageView imageViewDefaultPhoto;
    private TextView textViewIsOpen;
    private TextView textViewRating;
    private TextView textViewWalkingDuration;
    private TextView textViewDrivingDuration;

    // Find views in each item.
    public PoiHolder(Context context, View itemView) {
        super(itemView);

        this.context = context;

        linearLayoutPoiData = (LinearLayout) itemView.findViewById(R.id.linearLayoutPoiData);
        textViewName = (TextView) linearLayoutPoiData.findViewById(R.id.textViewItemPoiName);
        textViewVicinity = (TextView) linearLayoutPoiData.findViewById(R.id.textViewItemPoiVicinity);
        textViewDistance = (TextView) linearLayoutPoiData.findViewById(R.id.textViewItemPoiDistance);
        textViewIsOpen = (TextView) linearLayoutPoiData.findViewById(R.id.textViewItemPoiIsOpen);
        textViewRating = (TextView) linearLayoutPoiData.findViewById(R.id.textViewItemPoiRating);
        imageViewPhoto = (ImageView) itemView.findViewById(R.id.imageViewItemPoiPhoto);
        imageViewDefaultPhoto = (ImageView) itemView.findViewById(R.id.imageViewDefaultItemPoiPhoto);
        textViewWalkingDuration = (TextView) linearLayoutPoiData.findViewById(R.id.textViewItemPoiWalkingDuration);
        textViewDrivingDuration = (TextView) linearLayoutPoiData.findViewById(R.id.textViewItemPoiDrivingDuration);

        linearLayoutPoiData.setOnClickListener(this);
        linearLayoutPoiData.setOnLongClickListener(this);

        imageViewPhoto.setOnClickListener(this);

        clickListener = (ClickListener) context;
    }

    // Bind Data Object to the Views.
    public void bindPoi(Poi poi) {

        String name = poi.getName();
        String vicinity = poi.getVicinity();
        String distanceText = poi.getDistanceText();
        String isOPen = poi.getIsOpen();
        double rating = poi.getRating();
        String walkingDurationText = poi.getWalkingDurationText();
        String drivingDurationText = poi.getDrivingDurationText();

        if (name != null) {
            textViewName.setText(name);
        } else {
            textViewName.setText(R.string.no_information);
        }

        if (vicinity != null) {
            textViewVicinity.setText(vicinity);
        } else {
            textViewVicinity.setText(R.string.no_information);
        }

        // TODO: to add dependence for settings (km or miles) and show the relevant one.
        if (distanceText != null) {
            textViewDistance.setText(distanceText);
        } else {
            textViewDistance.setText("");
        }

        if (walkingDurationText != null) {
            textViewWalkingDuration.setText(walkingDurationText);
        } else {
            textViewWalkingDuration.setText("");
        }

        if (drivingDurationText != null) {
            textViewDrivingDuration.setText(drivingDurationText);
        } else {
            textViewDrivingDuration.setText("");
        }

        if (isOPen != null) {
            textViewIsOpen.setText(isOPen);
        } else {
            textViewIsOpen.setText("");
        }

        if (rating != Constants.NO_RATING) {
            textViewRating.setText(Utils.formatRating(rating) + "");
        } else {
            textViewRating.setText("");
        }

        // TODO: load image, need to decide from where
        // TODO: probably to load from Data Base.

        // Show image in item.
        String photoReference = poi.getPhotoReference();

//        imageViewPhoto.setImageDrawable(null);
        String urlPhoto = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
                + photoReference + "&key=" + Constants.ACCESS_KEY_GOOGLE_PLACE_API;
        Picasso.with(context)
                .load(urlPhoto)
//                .error(android.R.drawable.ic_menu_crop)
//                .fit()
                .transform(new RoundedTransformation(16, 0)) // used 3rd side class
                .into(imageViewPhoto, new Callback() {
                    @Override
                    public void onSuccess() {
                        imageViewDefaultPhoto.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        imageViewDefaultPhoto.setVisibility(View.VISIBLE);
                    }
                });
    }

    // Do it on poi item (data or photo) clicked.
    @Override
    public void onClick(View v) {

        if (v instanceof LinearLayout) { // data part
//            Log.i(TAG, textViewName.getText().toString());
            clickListener.onDataClick(textViewName.getText().toString());
        } else { // photo part
//            Log.i(TAG, "Photo of " + textViewName.getText().toString());
            clickListener.onPhotoClick(textViewName.getText().toString());
        }
    }

    // Do it on poi data long click.
    @Override
    public boolean onLongClick(View v) {

//        Log.i(TAG, textViewName.getText().toString() + " long touched.");
        clickListener.onDataLongClick(textViewName.getText().toString());

        return true; //  don't do onClick too.
    }

    public interface ClickListener {

        void onDataClick(String name);

        void onDataLongClick(String name);

        void onPhotoClick(String name);
    }
}
