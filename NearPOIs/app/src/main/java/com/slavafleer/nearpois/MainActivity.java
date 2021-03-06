package com.slavafleer.nearpois;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.slavafleer.nearpois.db.FavoritesLogic;
import com.slavafleer.nearpois.helper.BroadCastReceiverHelper;
import com.slavafleer.nearpois.recyclerView.PoiHolder;
import com.slavafleer.nearpois.recyclerView.QuickSearchHolder;

public class MainActivity extends AppCompatActivity implements
        PoiHolder.OnClickListener, QuickSearchHolder.OnClickListener,
        OnMapReadyCallback, PoisFragment.LocationCallbacks {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static PoisFragment poisFragment;
    private static Poi lastSelectedPoi;

    private String deviceType;

    private GoogleMap googleMap;

    private static boolean isFirstTimeRun = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceType = (String) findViewById(R.id.linearLayoutMainRoot).getTag();

        // Don't recreate fragments on rotations
        if (poisFragment == null) {
            poisFragment = new PoisFragment();
        }

        // Get Pois fragment
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.poisContainer, poisFragment)
                .commit();

        // Get Map fragment if it is tablet
        if (deviceType.equals(Constants.KEY_TABLET)) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            SupportMapFragment fragmentMap =
                    (SupportMapFragment) fragmentManager.findFragmentById(R.id.mapContainer);

            fragmentMap.getMapAsync(this);
        }
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

    // Do it on poi item data part clicked.
    @Override
    public void onDataClick(Poi poi) {

        lastSelectedPoi = poi;

        Log.i(TAG, poi.getName());

        showOnMap(poi);
    }

    // Show POI on the map or in map fragment on tablet or as map activity on phone
    private void showOnMap(Poi poi) {

        if (deviceType.equals(Constants.KEY_TABLET)) {

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
            int cameraSpeedInMilliseconds = 3000;

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, zoom);

            googleMap.animateCamera(cameraUpdate, cameraSpeedInMilliseconds, null);

        } else {
            // On phone device
            Intent intent = new Intent(this, MapActivity.class);

            intent.putExtra(Constants.KEY_MAP_NAME, poi.getName());
            intent.putExtra(Constants.KEY_MAP_LATITUDE, poi.getLatitude());
            intent.putExtra(Constants.KEY_MAP_LONGITUDE, poi.getLongitude());

            startActivity(intent);
        }
    }

    // Runs when poi item data part is long clicked.
    @Override
    public void onDataLongClick(Poi poi, LinearLayout linearLayoutPoiData, final int position) {

        Log.i(TAG, poi.getName() + " long touched.");

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.KEY_MAIN_ACTIVITY, MODE_PRIVATE);
        boolean isListOfFavorites = sharedPreferences.getBoolean(Constants.KEY_IS_FAVORITES, false);

        FavoritesLogic favoritesLogic = new FavoritesLogic(this);
        favoritesLogic.open();

        // Open popup menu on Favorites list item long click
        if (isListOfFavorites) {

            PopupMenu popupMenu = new PopupMenu(this, linearLayoutPoiData);
            popupMenu.getMenuInflater().inflate(R.menu.menu_item_favorites, popupMenu.getMenu());

            final Poi tmpPoi = poi;
            final FavoritesLogic tmpFavoritesLogic = favoritesLogic;

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.menu_item_poi_show_on_map:
                            onDataClick(tmpPoi);
                            break;

                        case R.id.menu_item_favorites_remove_from_favorites:

                            // Delete from Favorites
                            tmpFavoritesLogic.deletePoi(tmpPoi);
                            poisFragment.updateRecyclerView(position);
                            Toast.makeText(MainActivity.this, R.string.poi_deleted_from_favorites, Toast.LENGTH_SHORT).show();
                            break;

                        case R.id.menu_item_poi_share:
                            // Share the poi name and address
                            share(tmpPoi);
                            break;
                    }

                    return false;
                }
            });

            popupMenu.show();

            // Open popup menu on Results list item long click
        } else {

            PopupMenu popupMenu = new PopupMenu(this, linearLayoutPoiData);
            popupMenu.getMenuInflater().inflate(R.menu.menu_item_poi, popupMenu.getMenu());

            final Poi tmpPoi = poi;
            final FavoritesLogic tmpFavoritesLogic = favoritesLogic;

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.menu_item_poi_show_on_map:
                            onDataClick(tmpPoi);
                            break;

                        case R.id.menu_item_poi_add_to_favorites:

                            // Save selected poi to Favorites
                            tmpPoi.setIsOpen("");
                            tmpFavoritesLogic.addPoi(tmpPoi);

                            Toast.makeText(MainActivity.this, R.string.poi_added_to_favorites, Toast.LENGTH_SHORT).show();
                            break;

                        case R.id.menu_item_poi_share:
                            // Share the poi name and address
                            share(tmpPoi);
                            break;
                    }

                    return false;
                }
            });

            popupMenu.show();
        }
    }

    // Share the poi name and address
    private void share(Poi tmpPoi) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,
                tmpPoi.getName() + "\n\n" +
                        tmpPoi.getVicinity());
        intent.setType("text/plain");
        startActivity(intent);
    }

    // Runs when quick search is clicked and give type of search
    @Override
    public void onQuickSearchClick(String type) {

        poisFragment.setType(type);
        Log.i(TAG, type);
    }

    // Runs when map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        if (lastSelectedPoi != null) {
            showOnMap(lastSelectedPoi);
        }
    }

    // Create Action Bar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Action Bar Menu Actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_item_favorites:
                Toast.makeText(this, R.string.favorites, Toast.LENGTH_SHORT).show();

                if (poisFragment != null) {
                    poisFragment.showFavorites();
                }
                return true;

            case R.id.menu_item_settings:
                Intent intent = new Intent(this, PreferencesActivity.class);
                startActivity(intent);

                return true;

            case R.id.menu_item_exit:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Run as device get its location
    @Override
    public void onLocationGet(Location lastLocation) {

        if (isFirstTimeRun && deviceType.equals(Constants.KEY_TABLET)) {
            isFirstTimeRun = false;

            lastSelectedPoi = new Poi(getString(R.string.you), lastLocation.getLatitude(), lastLocation.getLongitude());
            showOnMap(lastSelectedPoi);
        }
    }
}
