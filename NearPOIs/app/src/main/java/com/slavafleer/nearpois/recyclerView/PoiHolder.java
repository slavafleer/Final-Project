package com.slavafleer.nearpois.recyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slavafleer.nearpois.Poi;
import com.slavafleer.nearpois.R;
import com.slavafleer.nearpois.asynkTask.ImageDownloaderAsyncTask;
import com.squareup.picasso.Picasso;

/**
 * Poi Holder for Poi Recycler View
 */
public class PoiHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, ImageDownloaderAsyncTask.Callbacks {

    private static final String TAG = "PoiHolder";

    private Context context;

    private ClickListener clickListener;

    private LinearLayout linearLayoutPoiData;
    private TextView textViewName;
    private TextView textViewAddress;
    private TextView textViewVicinity;
    private TextView textViewDistance;
    private ImageView imageViewPhoto;

    // Find views in each item.
    public PoiHolder(Context context, View itemView) {
        super(itemView);

        this.context = context;

        linearLayoutPoiData = (LinearLayout) itemView.findViewById(R.id.linearLayoutPoiData);
        textViewName = (TextView) linearLayoutPoiData.findViewById(R.id.textViewItemPoiName);
        textViewAddress = (TextView) linearLayoutPoiData.findViewById(R.id.textViewItemPoiAddress);
        textViewVicinity = (TextView) linearLayoutPoiData.findViewById(R.id.textViewItemPoiVicinity);
        textViewDistance = (TextView) linearLayoutPoiData.findViewById(R.id.textViewItemPoiDistance);
        imageViewPhoto = (ImageView) itemView.findViewById(R.id.imageViewItemPoiPhoto);

        linearLayoutPoiData.setOnClickListener(this);
        linearLayoutPoiData.setOnLongClickListener(this);

        imageViewPhoto.setOnClickListener(this);

        clickListener = (ClickListener) context;
    }

    // Bind Data Object to the Views.
    public void bindPoi(Poi poi) {

        String name = poi.getName();
        String address = poi.getAddress();
        String vicinity = poi.getVicinity();
        double distance = poi.getDistance();

        if (name != null) {
            textViewName.setText(name);
        }

        if (address != null) {
            textViewAddress.setText(address);
        }

        if (vicinity != null) {
            textViewVicinity.setText(vicinity);
        }

        // TODO: to add dependence for settings (km or miles) and show the relevant one.
        if (distance > 0) {
            textViewDistance.setText(String.format("%.0fkm", distance));
        }

        // TODO: load image, need to decide from where
        // TODO: probably to load from Data Base.
        String photoReference = poi.getPhotoReference();

        String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photoReference + "&key=AIzaSyBqZywUvsonHXo6gVpiI0p-ABQ9oRuYdJw";
        Picasso picasso = new Picasso.Builder(context)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
//                        imageViewPhoto.setImageResource(android.R.drawable.ic_menu_crop);
                    }
                })
                .build();
        picasso.load(url)
                .into(imageViewPhoto);

    }

    // ImageDownloaderAsyncTask Callbacks
    @Override
    public void onAboutToStartDownloadImage() {

    }

    @Override
    public void onImageDownloadSuccess(Bitmap bitmap) {
        imageViewPhoto.setImageBitmap(bitmap);
    }

    @Override
    public void onImageDownloadError(int httpStatusCode, String errorMessage) {
        imageViewPhoto.setImageBitmap(null);
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
