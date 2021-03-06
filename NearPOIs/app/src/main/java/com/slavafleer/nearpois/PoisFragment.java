package com.slavafleer.nearpois;


import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.slavafleer.nearpois.db.FavoritesLogic;
import com.slavafleer.nearpois.db.ResultsLogic;
import com.slavafleer.nearpois.recyclerView.PoiAdapter;
import com.slavafleer.nearpois.recyclerView.QuickSearchAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 * This fragment includes search input,
 * running default buttons for quick searching
 * and the list of results.
 */
public class PoisFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = PoisFragment.class.getSimpleName();

    private LocationCallbacks locationCallbacks;

    private RecyclerView recyclerViewPois;
    private RecyclerView recyclerViewQuickSearches;

    private EditText editTextSearchText;
    private ImageView imageViewFind;
    private ImageView imageViewFindAll;
    private TextView textViewEmptyRecyclerView;

    private PoiAdapter poiAdapter;

    private Activity activity;

    private ResultsLogic resultsLogic; // db business logic

    private ArrayList<Poi> pois = new ArrayList<>();

    private ArrayList<Poi> lastResult; // for saving last result

    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private double clientLatitude;
    private double clientLongitude;

    private static boolean isFirstTimeRun = true;
    private static boolean actionsDisabled;
    private static boolean isIsFirstTimeNoLocation = true;

    private int readyPoisCounter; // Show completed data of Pois

    private RequestQueue requestQueue;

    private String type;
    private String url;

    private boolean isListOfFavorites;

    public PoisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pois, container, false);

        activity = getActivity();

        locationCallbacks = (LocationCallbacks) activity;

        requestQueue = Volley.newRequestQueue(activity);

        resultsLogic = new ResultsLogic(activity);

        buildGoogleApiClient();

        editTextSearchText = (EditText) view.findViewById(R.id.editTextSearchText);

        // Find Button On Click
        imageViewFind = (ImageView) view.findViewById(R.id.imageViewFind);
        imageViewFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onViewFindClick();
            }
        });

        // Run search on FindAll click
        imageViewFindAll = (ImageView) view.findViewById(R.id.imageViewFindAll);
        imageViewFindAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (actionsDisabled) {
                    return;
                }

                findAllPois();
            }
        });

        recyclerViewPois = (RecyclerView) view.findViewById(R.id.recyclerViewPois);
        recyclerViewQuickSearches = (RecyclerView) view.findViewById(R.id.recyclerViewQuickSearches);

        // Initialising Pois Recycler View.
        poiAdapter = new PoiAdapter(activity, pois);
        recyclerViewPois.setLayoutManager(new LinearLayoutManager(activity));
        recyclerViewPois.setAdapter(poiAdapter);

        // Initialising Quick Search Array;
        String[] searchTypes = getResources().getStringArray(R.array.searchTypes);
        String[] imageNames = getResources().getStringArray(R.array.imageNames);

        ArrayList<QuickSearch> quickSearches = new ArrayList<>();
        for (int i = 0; i < searchTypes.length; i++) {
            quickSearches.add(new QuickSearch(activity, searchTypes[i], imageNames[i]));
        }

        // Initialising Quick Search Recycler View.
        QuickSearchAdapter quickSearchAdapter = new QuickSearchAdapter(activity, quickSearches);
        recyclerViewQuickSearches.setLayoutManager(
                new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewQuickSearches.setAdapter(quickSearchAdapter);

        // Put Recycler in middle of "endless" icons.
        recyclerViewQuickSearches.scrollToPosition(
                Constants.NUMBER_OF_QUICK_SEARCH_ICONS * QuickSearchAdapter.LOOPS_AMOUNT / 2);

        textViewEmptyRecyclerView = (TextView) view.findViewById(R.id.emptyRecyclerView);

        // Run search when clicked on Done in keyboard
        editTextSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    onViewFindClick();
                }
                return false;
            }
        });

        return view;
    }

    // Run search on FindAll or keyboard Done click
    private void onViewFindClick() {
        if (actionsDisabled) {
            return;
        }

        if (lastLocation == null) {
            Toast.makeText(activity, R.string.location_still_not_found, Toast.LENGTH_SHORT).show();
            return;
        }

        if (editTextSearchText.getText().toString().trim().equals("")) {
            Toast.makeText(activity, R.string.search_hint, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            String encodedSearchText = java.net.URLEncoder.encode(editTextSearchText.getText().toString().trim(), "UTF-8");
            url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                    clientLatitude + "," + clientLongitude + "&name=" + encodedSearchText +
                    "&rankby=distance&key=" + Constants.ACCESS_KEY_GOOGLE_PLACE_API;
            findPois();
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    // Find all POIs
    private void findAllPois() {
        if (lastLocation == null) {
            Toast.makeText(activity, R.string.location_still_not_found, Toast.LENGTH_LONG).show();
            return;
        }

        url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                clientLatitude + "," + clientLongitude +
                "&radius=10000&key=" + Constants.ACCESS_KEY_GOOGLE_PLACE_API;
        findPois();
    }

    // Create poi request and send it to API.
    private void findPois() {

        if (actionsDisabled) {
            return;
        }

        actionsDisabled = true;
        Log.d("test", "Actions disabled!!!");

        textViewEmptyRecyclerView.setVisibility(View.INVISIBLE);

        readyPoisCounter = 0; // Show completed data of Pois


        Log.i(TAG, url.toString());

        // Show dialog to user
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setTitle(activity.getString(R.string.connection));
        dialog.setMessage(activity.getString(R.string.please_wait));
        dialog.setCancelable(false);
        dialog.show();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {

            // On receive json, update pois array.
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString(Constants.KEY_STATUS);

                    if (status.equals(Constants.VALUE_OK)) {
                        pois.clear();
                        JSONArray results = response.getJSONArray(Constants.KEY_RESULTS);
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject oneResult = results.getJSONObject(i);
                            String name = oneResult.getString(Constants.KEY_NAME);
                            String vicinity = oneResult.getString(Constants.KEY_VICINITY);
                            String place_id = oneResult.getString(Constants.KEY_PLACE_ID);
                            JSONObject geometry = oneResult.getJSONObject(Constants.KEY_GEOMETRY);
                            JSONObject location = geometry.getJSONObject(Constants.KEY_LOCATION);
                            String latitude = location.getString(Constants.KEY_LATITUDE);
                            String longitude = location.getString(Constants.KEY_LONGITUDE);
                            String photo_reference = null;
                            if (oneResult.has(Constants.KEY_PHOTOS)) {
                                JSONArray photos = oneResult.getJSONArray(Constants.KEY_PHOTOS);
                                JSONObject photo = photos.getJSONObject(0);
                                photo_reference = photo.getString(Constants.KEY_PHOTO_REFERENCE);
                            }
                            String iconUrl = oneResult.getString(Constants.KEY_ICON_URL);
                            String isOpen = null;
                            if (oneResult.has(Constants.KEY_OPENING_HOURS)) {
                                JSONObject openingHours = oneResult.getJSONObject(Constants.KEY_OPENING_HOURS);
                                isOpen = openingHours.getString(Constants.KEY_OPEN_NOW)
                                        .equals("true") ? activity.getString(R.string.open) : activity.getString(R.string.closed);
                            }
                            double rating = Constants.NO_RATING;
                            if (oneResult.has(Constants.KEY_RATING)) {
                                rating = oneResult.getDouble(Constants.KEY_RATING);
                            }

                            pois.add(new Poi(name, vicinity, place_id, Double.parseDouble(latitude),
                                    Double.parseDouble(longitude), photo_reference, iconUrl, isOpen,
                                    rating));

                            isListOfFavorites = false;

                            SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.KEY_MAIN_ACTIVITY, Context.MODE_PRIVATE);
                            sharedPreferences.edit().putBoolean(Constants.KEY_IS_FAVORITES, isListOfFavorites).commit();

                            getDistanceAndWalkingDuration(i, isListOfFavorites);
                            getDrivingDuration(i, isListOfFavorites);
                        }

                    } else if (status.equals(Constants.VALUE_ZERO_RESULTS)) {

                        Toast.makeText(activity, R.string.no_results_found, Toast.LENGTH_SHORT).show();

                        actionsDisabled = false;
                        Log.d("test", "Actions enabled.");
                    } else {
                        Log.e(TAG, status);

                        actionsDisabled = false;
                        Log.d("test", "Actions enabled.");
                    }

                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());

                    actionsDisabled = false;
                    Log.d("test", "Actions enabled.");
                }

                try {
                    dialog.dismiss();
                } catch (IllegalArgumentException e) {
                    Log.e(TAG, e.getMessage());
                }

                isFirstTimeRun = false;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage());

                Toast.makeText(activity, R.string.connection_error_check_settings, Toast.LENGTH_LONG).show();

                actionsDisabled = false;
                Log.d("test", "Actions enabled.");

                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(intent);

                if (isFirstTimeRun) {
                    isFirstTimeRun = false;

                    // Load from DB last results if were any connection error
                    resultsLogic.open();
                    pois = resultsLogic.getAllResults();
                    resultsLogic.close();


                    poiAdapter = new PoiAdapter(activity, pois);
                    recyclerViewPois.setLayoutManager(new LinearLayoutManager(activity));
                    recyclerViewPois.setAdapter(poiAdapter);

                    actionsDisabled = false;
                    Log.d("test", "Actions enabled.");

                }

                try {
                    dialog.dismiss();
                } catch (IllegalArgumentException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });

        requestQueue.add(jsonRequest);

    }

    @Override
    public void onStart() {
        super.onStart();

        googleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (lastResult != null && !lastResult.isEmpty()) {
            // Save to DB last results
            resultsLogic.open();
            resultsLogic.replaceAllResults(lastResult);
            resultsLogic.close();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    // Build Google Api Client
    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    // Send queries to Google Distance Matrix API
    private void getDistanceAndWalkingDuration(final int position, final boolean isFavorites) {

        if (lastLocation == null) {
            Toast.makeText(activity, R.string.location_still_not_found, Toast.LENGTH_SHORT).show();
            return;
        }

        String units = PreferenceManager.getDefaultSharedPreferences(activity.getBaseContext())
                .getString(Constants.KEY_UNITS, "metric");
        String mode = "walking";
        String urlDistance = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" +
                lastLocation.getLatitude() + "," + lastLocation.getLongitude() + "&destinations=" +
                pois.get(position).getLatitude() + "," + pois.get(position).getLongitude() +
                "&mode=" + mode + "&units=" + units +
                "&key=" + Constants.ACCESS_KEY_GOOGLE_PLACE_API;
        Log.i(TAG, urlDistance);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.GET, urlDistance, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, response.toString());
                try {
                    if (response.getString(Constants.KEY_STATUS).equals(Constants.VALUE_OK)) {
                        JSONArray rows = response.getJSONArray(Constants.KEY_ROWS);
                        JSONObject row = rows.getJSONObject(0);
                        JSONArray elements = row.getJSONArray(Constants.KEY_ELEMENTS);
                        JSONObject element = elements.getJSONObject(0);
                        JSONObject distance = element.getJSONObject(Constants.KEY_DISTANCE);
                        String distanceText = distance.getString(Constants.KEY_TEXT);
                        int distanceValue = distance.getInt(Constants.KEY_VALUE);
                        JSONObject duration = element.getJSONObject(Constants.KEY_DURATION);
                        String durationText = duration.getString(Constants.KEY_TEXT);
                        long durationValue = duration.getLong(Constants.KEY_VALUE);

                        pois.get(position).setDistanceText(distanceText);
                        pois.get(position).setDistanceValue(distanceValue);
                        pois.get(position).setWalkingDurationText(durationText);
                        pois.get(position).setWalkingDurationValue(durationValue);

                        readyPoisCounter++;
                        initPoisRecyclerView(isFavorites);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    readyPoisCounter++;
                    initPoisRecyclerView(isFavorites);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage());

                readyPoisCounter++;
            }
        });

        requestQueue.add(jsonRequest);
    }

    // For saving last result
    private void copyLastResult() {
        lastResult = new ArrayList<>();
        for (Poi poi : pois) {
            lastResult.add(poi);
        }
    }

    // Initialising Pois Recycler View.
    private void initPoisRecyclerView(boolean isFavorites) {
        // waiting for finishing of all asyncTasks
        if (readyPoisCounter >= pois.size() * 2) {

            // Initialising Pois Recycler View.
            poiAdapter = new PoiAdapter(activity, pois);
            recyclerViewPois.setLayoutManager(new LinearLayoutManager(activity));
            recyclerViewPois.setAdapter(poiAdapter);

            if (!isFavorites) {
                copyLastResult();
            }

            actionsDisabled = false;
            Log.d("test", "Actions enabled after first search.");
        }
    }

    // Send queries to Google Distance Matrix API
    private void getDrivingDuration(final int position, final boolean isFavorites) {

        if (lastLocation == null) {
            Toast.makeText(activity, R.string.location_still_not_found, Toast.LENGTH_SHORT).show();
            return;
        }

        String units = PreferenceManager.getDefaultSharedPreferences(activity.getBaseContext())
                .getString(Constants.KEY_UNITS, "metric");
        String mode = "driving";
        String urlDistance = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" +
                lastLocation.getLatitude() + "," + lastLocation.getLongitude() + "&destinations=" +
                pois.get(position).getLatitude() + "," + pois.get(position).getLongitude() +
                "&mode=" + mode + "&units=" + units +
                "&key=" + Constants.ACCESS_KEY_GOOGLE_PLACE_API;
        Log.i(TAG, urlDistance);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.GET, urlDistance, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, response.toString());
                try {
                    if (response.getString(Constants.KEY_STATUS).equals(Constants.VALUE_OK)) {
                        JSONArray rows = response.getJSONArray(Constants.KEY_ROWS);
                        JSONObject row = rows.getJSONObject(0);
                        JSONArray elements = row.getJSONArray(Constants.KEY_ELEMENTS);
                        JSONObject element = elements.getJSONObject(0);
                        JSONObject duration = element.getJSONObject(Constants.KEY_DURATION);
                        String durationText = duration.getString(Constants.KEY_TEXT);
                        long durationValue = duration.getInt(Constants.KEY_VALUE);

                        pois.get(position).setDrivingDurationText(durationText);
                        pois.get(position).setDrivingDurationValue(durationValue);

                        readyPoisCounter++;
                        initPoisRecyclerView(isFavorites);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                    readyPoisCounter++;
                    initPoisRecyclerView(isFavorites);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage());

                readyPoisCounter++;
            }
        });

        requestQueue.add(jsonRequest);
    }

    // For Google Location API
    // Runs when a GoogleApiClient object successfully connects.
    @Override
    public void onConnected(Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (lastLocation != null) {
            clientLatitude = lastLocation.getLatitude();
            clientLongitude = lastLocation.getLongitude();
            Log.i(TAG, "Client location: " + clientLatitude + ", " + clientLongitude);
            locationCallbacks.onLocationGet(lastLocation);

            if (isFirstTimeRun) {

                findAllPois();

                isListOfFavorites = false;

                SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.KEY_MAIN_ACTIVITY, Context.MODE_PRIVATE);
                sharedPreferences.edit().putBoolean(Constants.KEY_IS_FAVORITES, isListOfFavorites).commit();
            }
        } else {

            if (isIsFirstTimeNoLocation) {

                isIsFirstTimeNoLocation = false;
                isFirstTimeRun = false;

                Log.i(TAG, "Client location is not found.");
                Toast.makeText(activity, R.string.enable_location_settings, Toast.LENGTH_SHORT).show();

                actionsDisabled = false;
                Log.d("test", "Actions enabled.");

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

        // The connection to Google Play services was lost for some reason.
        // We call connect() to attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        // Refer to the javadoc for ConnectionResult to see what error codes
        // might be returned in onConnectionFailed.
        Log.i(TAG, "Connection failed: ErrorCode: " + connectionResult.getErrorCode());
        Toast.makeText(activity, activity.getString(R.string.connection_error_plus_error_code) + connectionResult.getErrorCode(), Toast.LENGTH_SHORT).show();
    }

    // Runs as quick search was clicked
    public void setType(String type) {

        this.type = type;
        Log.i(TAG, type);

        url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                clientLatitude + "," + clientLongitude + "&type=" + type +
                "&rankby=distance&key=" + Constants.ACCESS_KEY_GOOGLE_PLACE_API;
        findPois();
    }

    public void showFavorites() {

        if (actionsDisabled) {
            return;
        }

        actionsDisabled = true;
        Log.d("test", "Actions disabled!!!");

        FavoritesLogic favoritesLogic = new FavoritesLogic(activity);

        readyPoisCounter = 0; // Show completed data of Pois

        // Load from DB last results
        favoritesLogic.open();
        pois = favoritesLogic.getAllPois();
        if (favoritesLogic != null) {
            favoritesLogic.close();
        }

        isListOfFavorites = true;

        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.KEY_MAIN_ACTIVITY, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(Constants.KEY_IS_FAVORITES, isListOfFavorites).commit();

        if (pois.isEmpty()) {

            textViewEmptyRecyclerView.setText(R.string.no_favorites);
            textViewEmptyRecyclerView.setVisibility(View.VISIBLE);

            poiAdapter = new PoiAdapter(activity, pois);
            recyclerViewPois.setLayoutManager(new LinearLayoutManager(activity));
            recyclerViewPois.setAdapter(poiAdapter);

            actionsDisabled = false;
            Log.d("test", "Actions enabled.");

            return;
        } else {
            textViewEmptyRecyclerView.setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i < pois.size(); i++) {
            getDistanceAndWalkingDuration(i, isListOfFavorites);
            getDrivingDuration(i, isListOfFavorites);
        }

    }

    // Update recycler view
    public void updateRecyclerView(int position) {
        showFavorites();
    }

    public interface LocationCallbacks {
        void onLocationGet(Location lastLocation);
    }
}
