package com.slavafleer.nearpois;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.slavafleer.nearpois.helper.BroadCastReceiverHelper;
import com.slavafleer.nearpois.recyclerView.PoiHolder;
import com.slavafleer.nearpois.recyclerView.QuickSearchHolder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        PoiHolder.OnClickListener, QuickSearchHolder.OnClickListener, OnMapReadyCallback {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static PoisFragment poisFragment;
    private static Poi lastSelectedPoi;

    private String deviceType;

    private GoogleMap googleMap;

//    private ResultsLogic resultsLogic; // db business logic

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        resultsLogic = new ResultsLogic(this);

        // TODO: for testing only, need to delete.
        //initTestResults();


        deviceType = (String) findViewById(R.id.linearLayoutMainRoot).getTag();

        // Don't recreate fragments on rotations
        if(poisFragment == null) {
            poisFragment = new PoisFragment();
        }

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.poisContainer, poisFragment)
                .commit();

        if (deviceType.equals("tablet")) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            SupportMapFragment fragmentMap =
                    (SupportMapFragment) fragmentManager.findFragmentById(R.id.mapContainer);

            fragmentMap.getMapAsync(this);
        }

        // Hide soft keyboard on start.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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

    // TODO: for testing only, need to delete.
    private void initTestResults() {

        ArrayList<Poi> pois = new ArrayList<>();
        pois.add(new Poi("Place 1"));
        pois.add(new Poi("Place 2"));
        pois.add(new Poi("Place 3"));
        pois.add(new Poi("Place 4"));
        pois.add(new Poi("Place 5"));
        pois.add(new Poi("Place 6"));
        pois.add(new Poi("Place 7"));

//        resultsLogic.open();
//        for(Poi poi : pois) {
//            resultsLogic.addPoi(poi);
//        }
//        resultsLogic.close();
    }

    // Do it on poi item data part clicked.
    @Override
    public void onDataClick(Poi poi) {

        lastSelectedPoi = poi;

        Log.i(TAG, poi.getName());

        showOnMap(poi);
    }

    // Show POI on the map or in map fragment on tablet or as map activity on phone
    private void showOnMap(Poi poi) {

        if(deviceType.equals("tablet")) {

            if(googleMap == null) {
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

        } else {
            // On phone device
            Intent intent = new Intent(this, MapActivity.class);

            intent.putExtra("name", poi.getName());
            intent.putExtra("latitude", poi.getLatitude());
            intent.putExtra("longitude", poi.getLongitude());

            startActivity(intent);
        }
    }

    // Runs when poi item data part is long clicked.
    @Override
    public void onDataLongClick(String name) {

        Log.i(TAG, name + " long touched.");
    }

    // Runs when poi item photo part is clicked.
    @Override
    public void onPhotoClick(String name) {

        Log.i(TAG, "Photo of " + name);
    }

    // Runs when quick search is clicked and give type of search
    @Override
    public void onQuickSearchClick(String type) {

        poisFragment.setType(type);
        Toast.makeText(this, "Searched by type: " + type, Toast.LENGTH_SHORT).show();
        Log.i(TAG, type);
    }

    // Runs when map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        if(lastSelectedPoi != null) {
            showOnMap(lastSelectedPoi);
        }
    }
}
