package com.slavafleer.nearpois;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.slavafleer.nearpois.helper.BroadCastReceiverHelper;

// Show POI on the map in activity on phone
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = MapActivity.class.getSimpleName();

    private GoogleMap googleMap;

    private static Poi poi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        poi = new Poi(intent.getStringExtra(Constants.KEY_MAP_NAME),
                intent.getDoubleExtra(Constants.KEY_MAP_LATITUDE, 0),
                intent.getDoubleExtra(Constants.KEY_MAP_LONGITUDE, 0));


        FragmentManager fragmentManager = getSupportFragmentManager();

        SupportMapFragment fragmentMap =
                (SupportMapFragment) fragmentManager.findFragmentById(R.id.mapContainer);

        fragmentMap.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Turning on ChargerReceiver
        BroadCastReceiverHelper.toggleChargerReceiver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Turning off ChargerReceiver
        BroadCastReceiverHelper.toggleChargerReceiver(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        Log.d(TAG, "Map is ready.");

        showOnMap(poi);
    }

    // Show POI on the map
    private void showOnMap(Poi poi) {

        if (googleMap == null) {
            return;
        }

        LatLng location = new LatLng(poi.getLatitude(), poi.getLongitude());

        googleMap.clear();

        // Create and add a new marker:
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title(poi.getName());
        Marker marker = googleMap.addMarker(markerOptions);
        marker.showInfoWindow();

        int zoom = 15;

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, zoom);

        googleMap.animateCamera(cameraUpdate);
    }
}
